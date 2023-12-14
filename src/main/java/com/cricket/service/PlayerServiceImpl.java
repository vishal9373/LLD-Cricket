package com.cricket.service;

import com.cricket.dtos.PlayerDetailsResponseDto;
import com.cricket.entity.Game;
import com.cricket.entity.Player;
import com.cricket.entity.PlayerCard;
import com.cricket.repository.GameRepository;
import com.cricket.repository.PlayerCardRepository;
import com.cricket.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private PlayerCardRepository playerCardRepository;

    @Override
    public ResponseEntity<?> playerDetails(Integer gameId) {
        Optional<Game> game = gameRepository.findById(gameId);

        List<Player> playerList = playerRepository.findByGameId(game.get().id);

        List<PlayerDetailsResponseDto> playerDetailsResponseDtoLis = new ArrayList<>();

        for(Player player : playerList){

            PlayerCard playerCard = playerCardRepository.findByPlayerId(player.id);
            PlayerDetailsResponseDto playerDetailsResponseDto = new PlayerDetailsResponseDto();
            playerDetailsResponseDto.setName(player.getName());
            playerDetailsResponseDto.setWickets(playerCard.getNoOfWickets());
            playerDetailsResponseDto.setRuns(playerCard.getTotalRuns());

            playerDetailsResponseDtoLis.add(playerDetailsResponseDto);
        }
        return ResponseEntity.ok(playerDetailsResponseDtoLis);
    }
}
