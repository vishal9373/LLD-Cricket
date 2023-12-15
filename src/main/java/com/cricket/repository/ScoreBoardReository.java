package com.cricket.repository;

import com.cricket.dtos.ScoreSummaryDto;
import com.cricket.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ScoreBoardReository extends JpaRepository<ScoreBoard,Integer> {

    @Query("SELECT new com.cricket.dtos.ScoreSummaryDto(SUM(s.runs), SUM(s.noOfWickets)) " +
            "FROM ScoreBoard s " +
            "WHERE s.team.id = :teamId")
    ScoreSummaryDto getSumOfRunsAndWicketsByTeamId(@Param("teamId") int teamId);

    @Query("SELECT s FROM ScoreBoard s WHERE s.team.id = :teamId ORDER BY s.id DESC")
    ScoreBoard findLastScoreBoardByTeamId(@Param("teamId") int teamId);
}
