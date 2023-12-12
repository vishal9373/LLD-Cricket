package com.cricket.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Builder
public class Team extends BaseModel{

    private String name;

    private int totolRuns;
    private int totalWickets;

    private TeamType teamType;

    @OneToOne
    private ScoreBoard scoreBoard;

    @OneToMany(mappedBy = "team")
    private List<Player> playerList;

    private boolean isTossWon;
}
