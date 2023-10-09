package com.igorfood.domain.services;

import com.igorfood.domain.model.Usuario;
import com.igorfood.domain.repository.UsuarioRepository;
import com.igorfood.dtos.UsuarioDTO;
import com.igorfood.dtos.input.SenhaInput;
import com.igorfood.dtos.input.UsuarioInput;
import com.igorfood.dtos.input.UsuarioSemSenhaInput;
import com.igorfood.exception.EntidadeEmUsoException;
import com.igorfood.exception.NegocioException;
import com.igorfood.exception.UsuarioNaoEncontradoException;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EntityManager entityManager;

    private static final String MSG_USUARIO_EM_USO
            = "Usuario de código %d não pode ser removida, pois está em uso";

    private static final String SENHA_INCORRETA = "A senha informada não coincide com a atual";

    public Set<UsuarioDTO> listar(){
        return usuarioRepository.findAll().stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
                .collect(Collectors.toSet());
    }

    public UsuarioDTO buscar(Long usuarioId){
        Usuario usuario = getUsuario(usuarioId);
        return modelMapper.map(usuario, UsuarioDTO.class);
    }


    @Transactional
    public UsuarioDTO salvar(UsuarioInput usuarioInput){
        Usuario usuario = modelMapper.map(usuarioInput,Usuario.class);
        Usuario usuarioSalvo = saveUsuario(usuario);
        return modelMapper.map(usuarioSalvo, UsuarioDTO.class);
    }

    @Transactional
    public UsuarioDTO atualizarUsuario(Long usuarioId, UsuarioSemSenhaInput usuarioSemSenhaInput){
        Usuario usuarioAtual = getUsuario(usuarioId);
        modelMapper.map(usuarioSemSenhaInput,usuarioAtual);
        return modelMapper.map(saveUsuario(usuarioAtual), UsuarioDTO.class);
    }

    @Transactional
    public void excluir(Long usuarioId){
        try{
            getUsuario(usuarioId);
            usuarioRepository.deleteById(usuarioId);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_USUARIO_EM_USO, usuarioId));
        }

    }

    @Transactional
    public void atualizarSenha(Long usuarioId, SenhaInput senhaInput){
        Usuario usuario = getUsuario(usuarioId);
        if (usuario.senhaNaoCoincideCom(senhaInput.getSenhaAtual())){
            throw new NegocioException(SENHA_INCORRETA);
        }
        usuario.setSenha(senhaInput.getNovaSenha());
    }

    public Usuario getUsuario(Long usuarioId){
        return usuarioRepository.findById(usuarioId).orElseThrow(
                () -> new UsuarioNaoEncontradoException(usuarioId)
        );
    }

    private Usuario saveUsuario(Usuario usuario) {
        entityManager.detach(usuario);
        Optional usuarioVerificaEmail = usuarioRepository.findByEmail(usuario.getEmail());
        if (usuarioVerificaEmail.isPresent() && !usuarioVerificaEmail.get().equals(usuario)){
            throw new NegocioException(
                    String.format("Ja existe um usuário com o email %s", usuario.getEmail()));
        }
        return usuarioRepository.save(usuario);
    }
}
