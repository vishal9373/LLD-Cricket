package com.cricket.controller;


import com.cricket.Constants.Constants;
import com.cricket.dtos.Response;
import com.cricket.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    @GetMapping("/{gameId}")
    public ResponseEntity<?> matchResult(@PathVariable Integer gameId) {
        String res = matchService.matchSummary(gameId);
        return ResponseEntity.ok(Response.builder().data(res).message(Constants.MATCH_RESULT).build());
    }
}
