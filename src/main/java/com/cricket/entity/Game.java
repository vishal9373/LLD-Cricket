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

    public GameStatus gameStatus;

    @ManyToMany
    public List<Team> teamList;

    @ManyToOne
    public Stadium stadium;

    @OneToMany(mappedBy = "game")
    public List<Player> playerList;

}
