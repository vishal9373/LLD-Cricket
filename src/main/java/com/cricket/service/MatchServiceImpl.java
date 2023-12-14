package com.cricket.service;

import com.cricket.entity.ScoreBoard;
import com.cricket.entity.Team;
import com.cricket.repository.ScoreBoardReository;
import com.cricket.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScoreBoardReository scoreBoardReository;

    @Override
    public ResponseEntity<?> matchSummary(Integer gameId) {

        String result = teamRepository.findWinningTeam(gameId);


        return ResponseEntity.ok(result + " has Won the Match");
    }
}
