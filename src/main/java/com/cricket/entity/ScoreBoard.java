package com.cricket.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
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

    @OneToOne
    @JsonIgnore
    public Team team;

}
