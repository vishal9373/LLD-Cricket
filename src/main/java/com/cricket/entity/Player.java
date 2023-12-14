package com.cricket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Player extends BaseModel{

    public String name;

    public int jerseyNumber;

    public PlayerType playerType;

    public PlayerStatus playerStatus;

    public PlayerPosition playerPosition;

    @ManyToOne
    @JsonIgnore
    public Team team;

    @ManyToOne
    @JsonIgnore
    public Game game;

}
