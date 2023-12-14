package com.cricket.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class Game extends BaseModel{

    public GameStatus gameStatus;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    public List<Team> teamList;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    public List<Player> playerList;

}
