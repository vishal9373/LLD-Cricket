package com.cricket.service;

import com.cricket.Exceptions.GameValidationException;
import com.cricket.dtos.GameRequestDto;
import com.cricket.dtos.PlayerRequestDto;
import com.cricket.dtos.Response;
import com.cricket.dtos.TeamRequestDto;
import com.cricket.entity.*;
import com.cricket.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService{

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ScoreBoardReository scoreBoardReository;

    @Autowired
    private PlayerCardRepository playerCardRepository;

    @Override
    @Transactional
    public Response addGame(GameRequestDto gameRequestDto) {
       // System.out.println("Inside service : " + gameRequestDto);
        try {
            validateTeam(gameRequestDto);
            Game game = Game.builder()
                    .gameStatus(GameStatus.IN_PROGRESS)
                    .build();

            gameRepository.save(game);


            List<TeamRequestDto> teamRequestDtoList = gameRequestDto.getTeamRequestDtos();
            List<Team> teamList = new ArrayList<>();
            for(TeamRequestDto teamRequest : teamRequestDtoList){

                List<PlayerRequestDto> playerRequestDtoList = teamRequest.getPlayerRequestDtoList();
                ScoreBoard scoreBoard  = ScoreBoard.builder()
                        .runs(0)
                        .noOfBowls(0)
                        .noOfOvers(0)
                        .noOfWickets(0)
                        .build();

                scoreBoardReository.save(scoreBoard);

                Team  team = Team.builder()
                        .isTossWon(teamRequest.isTossWon())
                        .name(teamRequest.getName())
                        .teamType(teamRequest.isTossWon() ? TeamType.BATTING : TeamType.BOWLING)
                        .totolRuns(0)
                        .totalWickets(0)
                        .scoreBoard(scoreBoard)
                        .build();

               // teamList.add(team);
                teamRepository.save(team);

                List<Player> playerList = new ArrayList<>();
                for(PlayerRequestDto playerRequestDto : playerRequestDtoList){
                    Player player = Player.builder()
                            .name(playerRequestDto.getName())
                            .playerStatus(PlayerStatus.valueOf(playerRequestDto.getPlayerStatus()))
                            .playerType(PlayerType.valueOf(playerRequestDto.getPlayerType()))
                            .jerseyNumber(playerRequestDto.getJerseyNumber())
                            .game(game)
                            .team(team)
                            .build();

                   // playerList.add(player);
                    playerRepository.save(player);
                    PlayerCard playerCard = PlayerCard.builder()
                            .noOfFours(0)
                            .noOfSixs(0)
                            .noOfWickets(0)
                            .totalRuns(0)
                            .player(player)
                            .build();

                    playerCardRepository.save(playerCard);

                }



            }



            return Response.getSuccessResponse("Added Successfully");
        }catch(GameValidationException e){
            e.printStackTrace();
           return  Response.getFailureResponse(e.getMessage());
        }
        catch(Exception e){
            e.printStackTrace();
            return Response.getFailureResponse(e.getMessage());
        }
    }

    private void validateTeam(GameRequestDto gameRequestDto) {

        List<TeamRequestDto> teamRequestDtoList = gameRequestDto.getTeamRequestDtos();

        if(teamRequestDtoList.size() != 2){
            throw new GameValidationException("There are no two Teams playing");
        }

        for(TeamRequestDto teamRequest : teamRequestDtoList){

            List<PlayerRequestDto> playerRequestDtoList = teamRequest.getPlayerRequestDtoList();

            if(playerRequestDtoList.size() != 5){
                throw new GameValidationException("There should be Five Players in a team");
            }

            int iSubstituteCount = 0,iWicketKeeperCount=0;
            for(PlayerRequestDto playerRequestDto : playerRequestDtoList){
                if(PlayerStatus.SUBSTITUTE == PlayerStatus.valueOf(playerRequestDto.getPlayerStatus())){
                    iSubstituteCount +=1;
                }
                if(PlayerType.WICKET_KEEPER == PlayerType.valueOf(playerRequestDto.getPlayerType())){
                    iWicketKeeperCount +=1;
                }
            }
            if(iSubstituteCount != 1){
                throw new GameValidationException("There should be one substitute player");
            }

            if(iWicketKeeperCount != 1){
                throw new GameValidationException("There should be one wicket Keeper");
            }
        }

    }
}
