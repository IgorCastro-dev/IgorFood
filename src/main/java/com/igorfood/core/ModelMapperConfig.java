package com.igorfood.core;

import com.igorfood.domain.model.Endereco;
import com.igorfood.domain.model.ItemPedido;
import com.igorfood.domain.model.Restaurante;
import com.igorfood.dtos.EnderecoDTO;
import com.igorfood.dtos.input.ItemPedidoInput;
import com.igorfood.dtos.input.RestauranteInput;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        var enterecoToEnderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
        enterecoToEnderecoDTOTypeMap.<String>addMapping(
          endereco -> endereco.getCidade().getEstado().getNome(),
          (dest,value) -> dest.getCidade().setEstado(value));

        modelMapper.createTypeMap(ItemPedidoInput.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
        return modelMapper;
    }


}
