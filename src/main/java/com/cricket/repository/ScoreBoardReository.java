package com.cricket.repository;

import com.cricket.dtos.ScoreCardResponseDto;
import com.cricket.dtos.ScoreSummaryDto;
import com.cricket.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ScoreBoardReository extends JpaRepository<ScoreBoard,Integer> {

    @Query("SELECT new com.cricket.dtos.ScoreSummaryDto(SUM(s.runs), SUM(s.noOfWickets)) " +
            "FROM ScoreBoard s " +
            "WHERE s.team.id = :teamId")
    ScoreSummaryDto getSumOfRunsAndWicketsByTeamId(@Param("teamId") int teamId);

    @Query("SELECT s FROM ScoreBoard s WHERE s.team.id = :teamId AND s.id = (SELECT MAX(sb.id) FROM ScoreBoard sb WHERE sb.team.id = :teamId)")
    ScoreBoard findLastScoreBoardByTeamId(@Param("teamId") int teamId);

    @Query("SELECT new com.cricket.dtos.ScoreCardResponseDto(s.totalRuns,s.totalWickets,s.team.name)" +
            " FROM ScoreBoard s WHERE s.team.id = :teamId AND s.noOfBowls = :scoreBoardId")
    List<ScoreCardResponseDto> findScoreBoardByTeamIdAndScoreBoardId(@Param("teamId") int teamId, @Param("scoreBoardId") int scoreBoardId);
}
