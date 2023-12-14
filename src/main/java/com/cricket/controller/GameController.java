package com.cricket.controller;


import com.cricket.dtos.GameDetailsDto;
import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.Response;
import com.cricket.service.GameService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
        return gameService.addGame(gameRequestDto);
    }

    @PostMapping("/start")
    public ResponseEntity<?> startGame(@RequestBody GameDetailsDto gameDetailsDto) throws JsonProcessingException {
        return gameService.startGame(gameDetailsDto);
    }
}
