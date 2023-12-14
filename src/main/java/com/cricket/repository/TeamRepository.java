package com.cricket.repository;

import com.cricket.entity.ScoreBoard;
import com.cricket.entity.Team;
import com.cricket.entity.TeamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;


@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {

    Team findByTeamTypeAndGameId(TeamType teamType,Integer gameId);

    List<Team> findByGameId(Integer gameId);

    @Query("SELECT s FROM ScoreBoard s WHERE s.team.game.id = :gameId")
    List<ScoreBoard> findScoreCard(Integer gameId);
}
