package com.cricket.dtos;

import lombok.Data;

import java.util.List;

@Data
public class TeamRequestDto {

    private String name;
    private boolean tossWon;
    private List<PlayerRequestDto> playerRequestDtoList;
}
