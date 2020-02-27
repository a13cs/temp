package com.ex.demo.model;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlaceDTO {

    private LocationDTO location;
    private Set<SportDTO> sports;

}
