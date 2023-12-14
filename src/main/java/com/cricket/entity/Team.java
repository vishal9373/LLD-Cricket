package com.cricket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
//@ToString
@Getter
public class Team extends BaseModel{

    public String name;

    public int totolRuns;
    public int totalWickets;

    public TeamType teamType;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    public List<Player> playerList;

    public Boolean isTossWon;

    @ManyToOne
    @JsonIgnore
    public Game game;
}
