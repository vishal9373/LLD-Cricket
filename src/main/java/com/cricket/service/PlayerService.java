package com.cricket.service;

import org.springframework.http.ResponseEntity;

public interface PlayerService {

     public ResponseEntity<?> playerDetails(Integer playerId);
}
