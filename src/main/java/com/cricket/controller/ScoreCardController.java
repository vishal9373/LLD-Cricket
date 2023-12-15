package com.cricket.controller;

import com.cricket.Constants.Constants;
import com.cricket.dtos.Response;
import com.cricket.dtos.ScoreCardResponseDto;
import com.cricket.service.ScoreCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scorecard")
public class ScoreCardController {

    @Autowired
    private ScoreCardService scoreCardService;


    @GetMapping("/{gameId}")
    public ResponseEntity<?> addGames(@PathVariable Integer gameId){
        List<ScoreCardResponseDto> scoreCardResponseDtoList =  scoreCardService.getScoreCard(gameId);
        return ResponseEntity.ok(Response.builder().data(scoreCardResponseDtoList).message(Constants.SCORE_DETAILS).build());

    }
}
