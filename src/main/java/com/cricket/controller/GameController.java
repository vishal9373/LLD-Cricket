package com.cricket.controller;


import com.cricket.Constants.Constants;
import com.cricket.dtos.GameDetailsDto;
import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.MatchSummaryResponseDto;
import com.cricket.dtos.Response;
import com.cricket.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/")
    public ResponseEntity<?> addGames(@RequestBody GameRequestDto gameRequestDto){
        Integer id =  gameService.addGame(gameRequestDto);
        return ResponseEntity.ok(Response.builder().data(id).message(Constants.GAME_CREATED).build());
    }

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody GameDetailsDto gameDetailsDto){
        MatchSummaryResponseDto matchSummaryResponseDto =  gameService.startGame(gameDetailsDto);
        return ResponseEntity.ok(Response.builder().data(matchSummaryResponseDto).message(Constants.GAME_COMPLETED).build());

    }
}
