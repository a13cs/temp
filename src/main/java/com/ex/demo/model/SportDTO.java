package com.ex.demo.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SportDTO {

    private String name;
    private Integer cost;
    private PeriodDTO period;

}
