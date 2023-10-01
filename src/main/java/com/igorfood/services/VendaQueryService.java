package com.igorfood.services;

import com.igorfood.dtos.VendaDiaria;
import com.igorfood.filter.VendaDiariaFilter;

import java.util.List;


public interface VendaQueryService {
    List<VendaDiaria> consultarVendaDiaria(VendaDiariaFilter filtro, String timeOffset);
}
