package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Builder
public class ScoreBoard extends BaseModel{

    private int noOfOvers;

    private int noOfBowls;

    private int noOfWickets;

    private int runs;

}
