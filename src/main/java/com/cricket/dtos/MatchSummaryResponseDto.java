package com.cricket.dtos;

import lombok.Data;

import java.util.List;

@Data
public class MatchSummaryResponseDto {

    private int gameId;
    private List<ScoreCardResponseDto> scoreCardResponseDtoList;
}
