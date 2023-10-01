package com.igorfood.infrastruture.service;

import com.igorfood.domain.model.Pedido;
import com.igorfood.domain.model.StatusPedido;
import com.igorfood.dtos.VendaDiaria;
import com.igorfood.filter.VendaDiariaFilter;
import com.igorfood.services.VendaQueryService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class VendaQueryServiceImpl implements VendaQueryService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<VendaDiaria> consultarVendaDiaria(VendaDiariaFilter filtro, String timeOffset) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<VendaDiaria> query = builder.createQuery(VendaDiaria.class);
        Root<?> root = query.from(Pedido.class);

        var predicates = new ArrayList<Predicate>();
        var functionConvertTz = builder.function("convert_tz", Date.class,root.get("dataCriacao"),builder.literal("+00:00"),builder.literal(timeOffset));
        var functionDateDataCriacao = builder.function("date", Date.class,functionConvertTz);

        var selection = builder.construct(VendaDiaria.class,
                            functionDateDataCriacao,
                            builder.count(root.get("id")),
                            builder.sum(root.get("valorTotal")));
        if (filtro.getRestauranteId() != null){
            predicates.add(builder.equal(root.get("restaurante").get("id"),filtro.getRestauranteId()));
        }
        if (filtro.getDataCriacaoInicio() != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoInicio()));
        }

        if (filtro.getDataCriacaoFim() != null) {
            predicates.add(builder.lessThanOrEqualTo(root.get("dataCriacao"),
                    filtro.getDataCriacaoFim()));
        }

        predicates.add(root.get("status").in(
                StatusPedido.CONFIRMADO, StatusPedido.ENTREGUE));
        query.select(selection);
        query.where(predicates.toArray(new Predicate[0]));
        query.groupBy(functionDateDataCriacao);
        return entityManager.createQuery(query).getResultList();
    }
}
