package com.cricket.service;

import com.cricket.dtos.GameDetailsDto;
import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.MatchSummaryResponseDto;

public interface GameService {

    Integer addGame(GameRequestDto gameRequestDto);

    MatchSummaryResponseDto startGame(GameDetailsDto gameDetailsDto);
}
