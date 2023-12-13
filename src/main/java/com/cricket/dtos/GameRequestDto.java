package com.cricket.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GameRequestDto {

    private List<TeamRequestDto> teamRequestDtos;
}
