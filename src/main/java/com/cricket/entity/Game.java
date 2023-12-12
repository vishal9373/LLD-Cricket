package com.cricket.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Builder
public class Game extends BaseModel{

    private GameStatus gameStatus;

    @ManyToMany
    private List<Team> teamList;

    @ManyToOne
    private Stadium stadium;

    @OneToMany(mappedBy = "game")
    private List<Player> playerList;

}
