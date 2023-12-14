package com.cricket.service;

import com.cricket.dtos.GameRequestDto;
import org.springframework.http.ResponseEntity;

public interface MatchService {

    public ResponseEntity<?> matchSummary(Integer gameId);

}
