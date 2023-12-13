package com.cricket.repository;

import com.cricket.entity.ScoreBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreBoardReository extends JpaRepository<ScoreBoard,Integer> {
}
