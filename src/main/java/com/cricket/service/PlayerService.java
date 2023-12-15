package com.cricket.service;

import com.cricket.dtos.PlayerDetailsResponseDto;

import java.util.List;

public interface PlayerService {

     List<PlayerDetailsResponseDto> playerDetails(Integer playerId);
}
