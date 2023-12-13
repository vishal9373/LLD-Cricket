package com.cricket.service;

import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.Response;

public interface GameService {

    public Response addGame(GameRequestDto gameRequestDto);
}
