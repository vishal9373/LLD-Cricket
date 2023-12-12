package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class PlayerCard extends BaseModel{

    private int noOfFours;

    private int noOfSixs;

    private int totalRuns;

    private int noOfWickets;

    @OneToOne
    private Player player;
}
