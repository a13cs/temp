package com.ex.demo.data;

import lombok.*;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Embeddable
public class LocationId implements Serializable {

    private String city;
    private String region;
    private String country;

}
