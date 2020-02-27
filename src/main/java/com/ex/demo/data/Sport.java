package com.ex.demo.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@ToString
public class Sport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private Integer cost;

    private Date startDate;
    private Date endDate;

//    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    private Place place;

    private LocationId locationId;

    public Sport() {
    }

}
