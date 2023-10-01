package com.igorfood.filter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.OffsetDateTime;

@Data
public class VendaDiariaFilter {

    @ApiModelProperty(example = "1")
    private Long restauranteId;

    @ApiModelProperty(example = "2020-10-01T11:34:00.270-03:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoInicio;

    @ApiModelProperty(example = "2020-10-01T11:34:00.270-03:00")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime dataCriacaoFim;
}
