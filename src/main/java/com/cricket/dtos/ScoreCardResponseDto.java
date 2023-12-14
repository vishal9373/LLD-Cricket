package com.cricket.dtos;

import lombok.Data;

@Data
public class ScoreCardResponseDto {

    private int noOfOvers;
    private int noOfRuns;
    private int noOfWickets;
    private String teamName;
}
