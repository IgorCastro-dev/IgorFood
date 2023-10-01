package com.igorfood.controller;

import com.igorfood.dtos.VendaDiaria;
import com.igorfood.filter.VendaDiariaFilter;
import com.igorfood.services.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatistica")
public class EstatisticaController {

    @Autowired
    private VendaQueryService vendaQueryService;

    @GetMapping("/venda-diaria")
    public List<VendaDiaria> consultarVendaDiaria(VendaDiariaFilter filter,
    @RequestParam(required = false,defaultValue = "+00:00") String TimeOffset){
        return vendaQueryService.consultarVendaDiaria(filter,TimeOffset);
    }
}
