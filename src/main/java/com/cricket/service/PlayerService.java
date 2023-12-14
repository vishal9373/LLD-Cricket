package com.cricket.service;

import com.cricket.dtos.GameRequestDto;
import org.springframework.http.ResponseEntity;

public interface PlayerService {

     public ResponseEntity<?> playerDetails(Integer playerId);
}
