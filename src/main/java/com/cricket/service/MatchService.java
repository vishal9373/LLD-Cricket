package com.cricket.service;

import org.springframework.http.ResponseEntity;

public interface MatchService {

    public ResponseEntity<?> matchSummary(Integer gameId);

}
