package com.cricket.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Stadium extends BaseModel{

    private String name;

    @OneToMany(mappedBy = "stadium")
    private List<Game> game;

    @ManyToOne
    private City city;

    @OneToMany(mappedBy = "stadium")
    private List<Seat> seat;

}
