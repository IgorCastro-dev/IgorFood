package com.igorfood.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igorfood.domain.model.Cozinha;
import com.igorfood.domain.model.Restaurante;
import com.igorfood.domain.repository.RestauranteRepository;
import com.igorfood.dtos.RestauranteDTO;
import com.igorfood.dtos.input.RestauranteInput;
import com.igorfood.exception.*;
import jakarta.transaction.Transactional;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RestauranteService {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private RestauranteRepository resturanteRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final String MSG_ESTADO_EM_USO
            = "Cidade de código %d não pode ser removida, pois está em uso";

    public List<RestauranteDTO> listar(){
        return resturanteRepository.findAll().stream()
                .map(restaurante->modelMapper.map(restaurante,RestauranteDTO.class))
                .collect(Collectors.toList());
    }

    public RestauranteDTO buscar(Long id){
        var restaurante = getRestaurante(id);
        return modelMapper.map(restaurante,RestauranteDTO.class);
    }

    @Transactional
    public RestauranteDTO save(RestauranteInput restauranteInput){
        try{
            Restaurante restaurante = modelMapper.map(restauranteInput,Restaurante.class);
            return saveRestauranteAndReturnDTO(restaurante);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }

    @Transactional
    public RestauranteDTO update(Long id,RestauranteInput restauranteInput){
            Restaurante restauranteAtual = getRestaurante(id);

            //para tratar org.hibernate.HibernateException: identifier of an
            //instance of com.igorfood.domain.model.Cozinha was altered from 2 to 3
            restauranteAtual.setCozinha(new Cozinha());

            modelMapper.map(restauranteInput,restauranteAtual);
            try{
                return saveRestauranteAndReturnDTO(restauranteAtual);
            }catch (EntidadeNaoEncontradaException e){
                throw new NegocioException(e.getMessage());
            }
    }


    @Transactional
    public void remover(Long id){
        try{
            buscar(id);
            resturanteRepository.deleteById(id);
            resturanteRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(
                    String.format(MSG_ESTADO_EM_USO, id));
        }
    }

    @Transactional
    public RestauranteDTO updateField(Long restauranteId, Map<String, Object> campos) {
        Restaurante restauranteAtual = getRestaurante(restauranteId);
        merge(restauranteAtual,campos);
        return saveRestauranteAndReturnDTO(restauranteAtual);
    }

    private Restaurante getRestaurante(Long id) {
        var restaurante = resturanteRepository.findById(id)
                .orElseThrow(()->
                        new RestauranteNaoEncontradoException(id));
        return restaurante;
    }

    private RestauranteDTO saveRestauranteAndReturnDTO(Restaurante restaurante) {
        Cozinha cozinha = cozinhaService.getCozinha(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return modelMapper.map(resturanteRepository.save((restaurante)), RestauranteDTO.class);
    }


    private void merge(Restaurante restauranteDestino, Map<String, Object> campos) {
        try{
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
            Restaurante restauranteOrigem = mapper.convertValue(campos, Restaurante.class);
            campos.forEach((nome, valor)-> {
                Field field = ReflectionUtils.findField(Restaurante.class, nome);
                field.setAccessible(true);
                Object novoValor = ReflectionUtils.getField(field,restauranteOrigem);
                ReflectionUtils.setField(field,restauranteDestino,novoValor);
            });
        }catch (IllegalArgumentException e){
                Throwable rootCause = ExceptionUtils.getRootCause(e);
                throw new HttpMessageNotReadableException(e.getMessage(),rootCause);
        }

    }
}
