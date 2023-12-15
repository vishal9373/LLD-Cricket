package com.cricket.repository;

import com.cricket.entity.Team;
import com.cricket.enums.MatchStatus;
import com.cricket.enums.TeamType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TeamRepository extends JpaRepository<Team,Integer> {

    Team findByTeamTypeAndGameId(TeamType teamType,Integer gameId);

    List<Team> findByGameId(Integer gameId);

    Team findByGameIdAndMatchStatus(int gameId, MatchStatus matchStatus);

}
