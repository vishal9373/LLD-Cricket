package com.cricket.dtos;


import lombok.Data;

@Data
public class PlayerRequestDto {

    private String name;
    private int jerseyNumber;
    private String playerStatus;
    private String playerType;
}
