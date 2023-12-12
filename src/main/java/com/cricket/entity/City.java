package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class City extends BaseModel {

    private String name;
    @OneToMany(mappedBy = "city")
    private List<Stadium> stadium;
}
