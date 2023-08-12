package com.igorfood.dtos.input;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class GrupoInput {

    @NotBlank
    private String nome;

}
