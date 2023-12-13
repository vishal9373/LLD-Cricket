package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;

@Data
@Entity
@Builder
public class Player extends BaseModel{

    public String name;

    public int jerseyNumber;

    public PlayerType playerType;

    public PlayerStatus playerStatus;

    @ManyToOne
    public Team team;

    @ManyToOne
    public Game game;

}
