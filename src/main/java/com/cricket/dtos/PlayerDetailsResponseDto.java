package com.cricket.dtos;


import lombok.Data;

@Data
public class PlayerDetailsResponseDto {

    private String name;
    private Integer runs;
    private Integer wickets;
}
