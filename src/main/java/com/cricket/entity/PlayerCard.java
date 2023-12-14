package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class PlayerCard extends BaseModel{

    public int noOfFours;

    public int noOfSixs;

    public int totalRuns;

    public int noOfWickets;

    @OneToOne
    public Player player;
}
