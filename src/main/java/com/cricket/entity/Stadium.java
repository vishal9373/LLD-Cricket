package com.cricket.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Stadium extends BaseModel{

    public String name;

    @OneToMany(mappedBy = "stadium")
    public List<Game> game;

    @ManyToOne
    public City city;

    @OneToMany(mappedBy = "stadium")
    public List<Seat> seat;

}
