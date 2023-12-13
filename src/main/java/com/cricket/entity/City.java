package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class City extends BaseModel {

    public String name;
    @OneToMany(mappedBy = "city")
    public List<Stadium> stadium;
}
