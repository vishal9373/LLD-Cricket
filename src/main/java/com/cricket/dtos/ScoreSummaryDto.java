package com.cricket.dtos;


import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@ToString
@Getter
public class ScoreSummaryDto {
    private Long totalRuns;
    private Long totalWickets;
}
