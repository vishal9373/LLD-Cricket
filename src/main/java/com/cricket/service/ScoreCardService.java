package com.cricket.service;

import com.cricket.dtos.ScoreCardResponseDto;

import java.util.List;

public interface ScoreCardService {

    List<ScoreCardResponseDto> getScoreCard(Integer gameId);
}
