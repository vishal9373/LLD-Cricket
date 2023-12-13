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

    public String name;

    public int totolRuns;
    public int totalWickets;

    public TeamType teamType;

    @OneToOne
    public ScoreBoard scoreBoard;

    @OneToMany(mappedBy = "team")
    public List<Player> playerList;

    public Boolean isTossWon;
}
