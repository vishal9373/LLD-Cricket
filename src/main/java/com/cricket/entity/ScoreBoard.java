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
@ToString
public class ScoreBoard extends BaseModel{

    public int noOfOvers;

    public int noOfBowls;

    public int noOfWickets;

    public int runs;

    @ManyToOne
    @JsonIgnore
    public Team team;

}
