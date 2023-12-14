package com.cricket.repository;

import com.cricket.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ScoreBoardReository extends JpaRepository<ScoreBoard,Integer> {

    ScoreBoard findByTeamId(Integer id);
}
