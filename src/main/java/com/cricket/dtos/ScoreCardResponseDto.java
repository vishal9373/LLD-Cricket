package com.cricket.dtos;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class ScoreCardResponseDto {

    private int noOfRuns;
    private int noOfWickets;
    private String teamName;
}
