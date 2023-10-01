package com.igorfood.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
@Data
@AllArgsConstructor
public class VendaDiaria {
    private LocalDate data;
    private Long totalVendas;
    private BigDecimal totalFaturado;
}
