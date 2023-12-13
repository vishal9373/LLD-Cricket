package com.cricket.controller;


import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.Response;
import com.cricket.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GameController {

    @Autowired
    private GameService gameService;

//    @GetMapping("/demo")
//    public void test(){
//        gameService.addGame();
//    }

    @PostMapping("/addGame")
    public Response addGames(@RequestBody GameRequestDto gameRequestDto){

        return gameService.addGame(gameRequestDto);

    }
}
