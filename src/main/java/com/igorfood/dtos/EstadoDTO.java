package com.igorfood.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EstadoDTO {
    private Long id;
    private String nome;
}
