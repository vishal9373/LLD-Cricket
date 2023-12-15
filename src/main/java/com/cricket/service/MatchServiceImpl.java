package com.cricket.service;

import com.cricket.entity.Team;
import com.cricket.enums.MatchStatus;
import com.cricket.repository.ScoreBoardReository;
import com.cricket.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class MatchServiceImpl implements MatchService{

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScoreBoardReository scoreBoardReository;

    @Override
    public String matchSummary(Integer gameId) {

        List<Team> result = teamRepository.findByGameId(gameId);

        int runs = result.get(0).totalRuns - result.get(1).totalRuns;
        int wickets = result.get(0).totalWickets - result.get(1).totalWickets;
        String teamName = runs < 0  ? result.get(1).name : result.get(0).name;

        if(runs == 0){
            return "Match is Tie!!!";
        }
        else if(runs < 0){
            return teamName + " has Won the Match " + " by " + wickets + " Wickets";
        }

        if(wickets <= 0){
            return  teamName + " has Won the Match " + " by " + runs + " runs";
        }

        return  teamName + " has Won the Match " + " by " + runs + " runs & " + wickets + " Wickets";
    }
}
