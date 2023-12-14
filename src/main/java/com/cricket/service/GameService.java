package com.cricket.service;

import com.cricket.dtos.GameDetailsDto;
import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.Response;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;

public interface GameService {

    public ResponseEntity<?> addGame(GameRequestDto gameRequestDto);

    public ResponseEntity<?> startGame(GameDetailsDto gameDetailsDto) throws JsonProcessingException;
}
