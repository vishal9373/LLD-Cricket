package com.cricket.entity;

import com.cricket.enums.MatchStatus;
import com.cricket.enums.TeamType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Team extends BaseModel{

    public String name;

    public int totalRuns;
    public int totalWickets;

    public TeamType teamType;

    public MatchStatus matchStatus;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    public List<Player> playerList;

    public Boolean isTossWon;

    @ManyToOne
    @JsonIgnore
    public Game game;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    public List<ScoreBoard> scoreBoard;
}
