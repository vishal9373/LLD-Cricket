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

        Response response = new Response();
        response.setMessage(Constants.MATCH_RESULT);
        response.setData(res);

        return ResponseEntity.ok(response);
    }
}
