package com.cricket.service;


import com.cricket.dtos.ScoreCardResponseDto;
import com.cricket.entity.ScoreBoard;
import com.cricket.entity.Team;
import com.cricket.repository.ScoreBoardReository;
import com.cricket.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ScoreCardServiceImpl implements ScoreCardService{

    @Autowired
    private ScoreBoardReository scoreBoardReository;

    @Autowired
    private TeamRepository teamRepository;

    @Override
    public List<ScoreCardResponseDto> getScoreCard(Integer gameId) {

        List<Team> teamList = teamRepository.findByGameId(gameId);
        List<ScoreCardResponseDto> scoreCardResponseDto = new ArrayList<>();

        for(Team team : teamList) {

            List<ScoreCardResponseDto> scoreSummaryDto = scoreBoardReository.findScoreBoardByTeamIdAndScoreBoardId(team.id, 6);
            for(ScoreCardResponseDto scoreCardResponseDto1 : scoreSummaryDto){
                scoreCardResponseDto.add(scoreCardResponseDto1);
            }
                ScoreBoard scoreBoard = scoreBoardReository.findLastScoreBoardByTeamId(team.id);
                ScoreCardResponseDto scoreCardResponseDto1 = ScoreCardResponseDto.builder()
                        .noOfRuns(scoreBoard.totalRuns)
                        .noOfWickets(scoreBoard.totalWickets)
                        .teamName(scoreBoard.team.name)
                        .build();
                scoreCardResponseDto.add(scoreCardResponseDto1);
        }

        return scoreCardResponseDto;

    }
}
