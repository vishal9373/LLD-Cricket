package com.cricket.controller;


import com.cricket.Constants.Constants;
import com.cricket.dtos.PlayerDetailsResponseDto;
import com.cricket.dtos.Response;
import com.cricket.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @GetMapping("/{playerId}")
    public ResponseEntity<?> playerDetails(@PathVariable Integer playerId) {

        List<PlayerDetailsResponseDto> playerDetailsResponseDtoList = playerService.playerDetails(playerId);

        Response response = new Response();
        response.setMessage(Constants.PLAYER_DETAILS);
        response.setData(playerDetailsResponseDtoList);

        return ResponseEntity.ok(response);
    }
}
