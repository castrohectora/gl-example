package com.castrohector.gl.dto;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class PhoneDTO {

    private Long number;
    private Integer citycode;
    private String contrycode;

}
