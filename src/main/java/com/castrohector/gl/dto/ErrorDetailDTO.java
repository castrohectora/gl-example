package com.castrohector.gl.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class ErrorDetailDTO {

    private Long timestamp;
    private Integer codigo;
    private String detail;

}
