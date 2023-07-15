package com.igorfood.services;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igorfood.domain.model.Cozinha;
import com.igorfood.domain.model.Restaurante;
import com.igorfood.domain.repository.RestauranteRepository;
import com.igorfood.exception.CozinhaNaoEncontradaException;
import com.igorfood.exception.EntidadeNaoEncontradaException;
import com.igorfood.exception.NegocioException;
import com.igorfood.exception.RestauranteNaoEncontradoException;
import org.flywaydb.core.internal.util.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.NotReadablePropertyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class RestauranteService {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private RestauranteRepository resturanteRepository;

    public List<Restaurante> listar(){
        return resturanteRepository.findAll();
    }

    public Restaurante buscar(Long id){
        var restaurante = resturanteRepository.findById(id)
                .orElseThrow(()->
                        new RestauranteNaoEncontradoException(id));
        return restaurante;
    }
    public Restaurante save(Restaurante restaurante){
        try{
            Cozinha cozinha = cozinhaService.buscar(restaurante.getCozinha().getId());
            restaurante.setCozinha(cozinha);
            return resturanteRepository.save(restaurante);
        }catch (CozinhaNaoEncontradaException e){
            throw new NegocioException(e.getMessage());
        }

    }
    public Restaurante update(Long id,Restaurante restaurante){
            Restaurante restaurante1 = buscar(id);
            BeanUtils.copyProperties(restaurante,restaurante1,"id");
            try{
                return save(restaurante1);
            }catch (EntidadeNaoEncontradaException e){
                throw new NegocioException(e.getMessage());
            }


    }

    public void remover(Long id){
        buscar(id);
        resturanteRepository.deleteById(id);
    }

    public Object updateField(Long restauranteId, Map<String, Object> campos) {
        Restaurante restauranteAtual = buscar(restauranteId);
        merge(restauranteAtual,campos);
        return update(restauranteId,restauranteAtual);
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
