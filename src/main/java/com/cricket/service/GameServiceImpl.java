package com.cricket.service;

import com.cricket.Exceptions.GameEndedException;
import com.cricket.Exceptions.GameValidationException;
import com.cricket.dtos.*;
import com.cricket.entity.*;
import com.cricket.repository.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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
    public ResponseEntity<?> addGame(GameRequestDto gameRequestDto) {

            validateTeam(gameRequestDto);
            Game game = Game.builder()
                    .gameStatus(GameStatus.IN_PROGRESS)
                    .build();

           game =  gameRepository.save(game);


            List<TeamRequestDto> teamRequestDtoList = gameRequestDto.getTeamRequestDtos();
            List<Team> teamList = new ArrayList<>();
            for(TeamRequestDto teamRequest : teamRequestDtoList){

                List<PlayerRequestDto> playerRequestDtoList = teamRequest.getPlayerRequestDtoList();


                System.out.println(teamRequest.getName() + " Toss: " + teamRequest.isTossWon());

                Team  team = Team.builder()
                        .isTossWon(teamRequest.isTossWon())
                        .name(teamRequest.getName())
                        .teamType(teamRequest.isTossWon() ? TeamType.BATTING : TeamType.BOWLING)
                        .totolRuns(0)
                        .totalWickets(0)
                        .game(game)
                        .build();

                teamList.add(team);
                teamRepository.save(team);

                ScoreBoard scoreBoard  = ScoreBoard.builder()
                        .runs(0)
                        .noOfBowls(0)
                        .noOfOvers(0)
                        .noOfWickets(0)
                        .team(team)
                        .build();

                scoreBoardReository.save(scoreBoard);

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


            return new ResponseEntity<String>("Data added Successfully", HttpStatus.CREATED);
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

    public void playInnings(Team battingTeam, Team bowlingTeam, int noOfOvers,int inning){

        ConcurrentHashMap<Player,PlayerPosition> battingplayers = new ConcurrentHashMap<>();
        ConcurrentHashMap<Player,PlayerPosition> bowlingplayers = new ConcurrentHashMap<>();


        List<Player> battingplayerList = battingTeam.getPlayerList();
        List<Player> bowlingplayerList = bowlingTeam.getPlayerList();

        int count = 0;
        for(Player player : battingplayerList) {
            if (!player.getPlayerStatus().equals(PlayerStatus.SUBSTITUTE) && !player.getPlayerStatus().equals(PlayerStatus.INJURED)) {
                if(count == 0) {
                    battingplayers.put(player, PlayerPosition.ON_STRICK);
                }
                else if(count  == 1){
                    battingplayers.put(player, PlayerPosition.ON_NON_STRICK);
                }
                else{
                    battingplayers.put(player, PlayerPosition.YET_TO_PLAY);
                }
                count+=1;
            }
        }

        count=0;
        for(Player player : bowlingplayerList){
            if (!player.getPlayerStatus().equals(PlayerStatus.SUBSTITUTE) && !player.getPlayerStatus().equals(PlayerStatus.INJURED)
                    && !player.getPlayerType().equals(PlayerType.WICKET_KEEPER) ) {
                if(count == 0) {
                    bowlingplayers.put(player, PlayerPosition.BOWLER);
                }
                else{
                    bowlingplayers.put(player, PlayerPosition.YET_TO_PLAY);
                }
                count+=1;
            }
        }

        play(noOfOvers,battingplayers ,bowlingplayers,battingTeam);


    }

    public void play(int noOfOvers,ConcurrentHashMap<Player,PlayerPosition> battingplayers,
                     ConcurrentHashMap<Player,PlayerPosition> bowlingplayers ,Team battingTeam){
        Random random = new Random();
        Player playerOnStrick = new Player();
        Player playerOnNonStrick = new Player();

        for(int olc=0;olc<noOfOvers;olc++){
            for(int ilc=0;ilc<6;ilc++){
                int run = random.nextInt(8);
                for(Map.Entry<Player,PlayerPosition> map : battingplayers.entrySet()){
                    if(map.getValue().equals(PlayerPosition.ON_STRICK)){
                        playerOnStrick = map.getKey();
                    }

                    if(map.getValue().equals(PlayerPosition.ON_NON_STRICK)){
                        playerOnNonStrick = map.getKey();
                    }

                }

                if(run != 7){
                    PlayerCard playerCard = playerCardRepository.findByPlayerId(playerOnStrick.id);
                    if(run == 4){
                        playerCard.setNoOfFours(playerCard.getNoOfFours()+1);
                    }
                    if(run == 6){
                        playerCard.setNoOfSixs(playerCard.getNoOfSixs()+1);
                    }

                    if(run == 1 || run == 3){
                        battingplayers.put(playerOnNonStrick,PlayerPosition.ON_STRICK);
                        battingplayers.put(playerOnStrick,PlayerPosition.ON_NON_STRICK);
                    }

                    playerCard.setTotalRuns(playerCard.getTotalRuns()+run);
                    playerCardRepository.save(playerCard);

                }
                else{
                    battingplayers.remove(playerOnStrick);

                    for(Map.Entry<Player,PlayerPosition> map : battingplayers.entrySet()){
                        if(map.getValue().equals(PlayerPosition.YET_TO_PLAY)){
                            Player tempPlayer = map.getKey();
                            battingplayers.put(tempPlayer,PlayerPosition.ON_STRICK);
                            break;
                        }
                    }
                    for(Map.Entry<Player,PlayerPosition> map : bowlingplayers.entrySet()){
                        if(map.getValue().equals(PlayerPosition.BOWLER)){
                            Player tempPlayer = map.getKey();
                            PlayerCard playerCard = playerCardRepository.findByPlayerId(tempPlayer.id);
                            playerCard.setNoOfWickets(playerCard.getNoOfWickets()+1);
                            playerCardRepository.save(playerCard);
                        }
                    }
                }

                ScoreBoard scoreBoard = scoreBoardReository.findByTeamId(battingTeam.id);
                System.out.println(scoreBoard);
                scoreBoard.setNoOfOvers(olc);
                scoreBoard.setNoOfBowls(ilc);
                if(run == 7){
                    scoreBoard.setNoOfWickets(scoreBoard.getNoOfWickets()+1);
                }else{
                    scoreBoard.setRuns(scoreBoard.getRuns()+run);
                }

                scoreBoardReository.save(scoreBoard);

                if(battingplayers.size() < 2){
                    return;
                }
            }


            for(Map.Entry<Player,PlayerPosition> map : bowlingplayers.entrySet()){

                if(map.getValue().equals(PlayerPosition.BOWLER)){
                    bowlingplayers.remove(map.getKey());
                }
            }
            for(Map.Entry<Player,PlayerPosition> map : bowlingplayers.entrySet()){
                if(map.getValue().equals(PlayerPosition.YET_TO_PLAY)){
                    bowlingplayers.put(map.getKey(),PlayerPosition.BOWLER);
                }
            }
        }
    }
    @Override
    public ResponseEntity<?> startGame(GameDetailsDto gameDetailsDto) throws JsonProcessingException {

        Optional<Game> game = gameRepository.findById(gameDetailsDto.getGameId());
        if(game.get().getGameStatus().equals(GameStatus.ENDED)){
            throw new GameEndedException("Game has already beeen played ! you can't play same game again");
        }

        List<Team> teamList = game.get().getTeamList();

        Random random = new Random();

        HashMap<PlayerPosition,Player> hashMap = new HashMap<>();

        Team teamBatting = new Team();
        Team teamBowling = new Team();


        int inning = 1;
        for(Team team : teamList){

             teamBatting = teamRepository.findByTeamTypeAndGameId(TeamType.BATTING,gameDetailsDto.getGameId());
             teamBowling = teamRepository.findByTeamTypeAndGameId(TeamType.BOWLING,gameDetailsDto.getGameId());

            playInnings(teamBatting,teamBatting, gameDetailsDto.getNoOfOvers(),inning);
            inning+=1;

             teamBatting.setTeamType(TeamType.BOWLING);
             teamBowling.setTeamType(TeamType.BATTING);

             teamRepository.save(teamBatting);
             teamRepository.save(teamBowling);
        }

        game.get().setGameStatus(GameStatus.ENDED);
        gameRepository.save(game.get());

        return new ResponseEntity<String>("Game has been Completed Successfully", HttpStatus.CREATED);
    }
}
