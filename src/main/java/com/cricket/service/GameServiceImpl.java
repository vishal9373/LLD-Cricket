package com.cricket.service;

import com.cricket.Constants.Constants;
import com.cricket.Exceptions.GameEndedException;
import com.cricket.Exceptions.GameValidationException;
import com.cricket.dtos.*;
import com.cricket.entity.*;
import com.cricket.enums.*;
import com.cricket.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Integer addGame(GameRequestDto gameRequestDto) {

            validateTeam(gameRequestDto);
            Game game = Game.builder()
                    .gameStatus(GameStatus.IN_PROGRESS)
                    .build();

           game =  gameRepository.save(game);
           saveTeams(gameRequestDto,game);
            return  game.id;
    }

    private void saveTeams(GameRequestDto gameRequestDto,Game game) {

        List<TeamRequestDto> teamRequestDtoList = gameRequestDto.getTeamRequestDtos();
        List<Team> teamList = new ArrayList<>();
        for(TeamRequestDto teamRequest : teamRequestDtoList){

            List<PlayerRequestDto> playerRequestDtoList = teamRequest.getPlayerRequestDtoList();

            Team  team = Team.builder()
                    .isTossWon(teamRequest.isTossWon())
                    .name(teamRequest.getName())
                    .teamType(teamRequest.isTossWon() ? TeamType.BATTING : TeamType.BOWLING)
                    .totalRuns(0)
                    .totalWickets(0)
                    .game(game)
                    .build();

            teamList.add(team);
            teamRepository.save(team);

            savePlayers(game, playerRequestDtoList, team);
        }
    }

    private void savePlayers(Game game, List<PlayerRequestDto> playerRequestDtoList, Team team) {
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
                    .noOfSixes(0)
                    .noOfWickets(0)
                    .totalRuns(0)
                    .player(player)
                    .build();

            playerCardRepository.save(playerCard);
        }
    }


    private void validateTeam(GameRequestDto gameRequestDto) {

        List<TeamRequestDto> teamRequestDtoList = gameRequestDto.getTeamRequestDtos();

        if(teamRequestDtoList.size() != 2){
            throw new GameValidationException(Constants.TEAM_PLAYING);
        }

        for(TeamRequestDto teamRequest : teamRequestDtoList){

            List<PlayerRequestDto> playerRequestDtoList = teamRequest.getPlayerRequestDtoList();

            if(playerRequestDtoList.size() != 5){
                throw new GameValidationException(Constants.PLAYER_EXCEPTION);
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
                throw new GameValidationException(Constants.SUBSTITUTE);
            }

            if(iWicketKeeperCount != 1){
                throw new GameValidationException(Constants.WICKET_KEEPER);
            }
        }

    }

    public void playInnings(Team battingTeam, Team bowlingTeam, int noOfOvers,int inning){

        ConcurrentHashMap<Player, PlayerPosition> battingPlayers = new ConcurrentHashMap<>();
        ConcurrentHashMap<Player,PlayerPosition> bowlingPlayers = new ConcurrentHashMap<>();


        List<Player> battingplayerList = battingTeam.getPlayerList();
        List<Player> bowlingplayerList = bowlingTeam.getPlayerList();

        setPostionOfBatsMan(battingplayerList, battingPlayers);
        setPositionOfBowler(bowlingplayerList, bowlingPlayers);

        play(noOfOvers,battingPlayers ,bowlingPlayers,battingTeam,bowlingTeam,inning);

        if(inning == 2) {
            checkForWin(bowlingTeam, battingTeam);
        }
    }

    private static void setPositionOfBowler(List<Player> bowlingplayerList, ConcurrentHashMap<Player, PlayerPosition> bowlingplayers) {
        int count=0;
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
    }

    private static void setPostionOfBatsMan(List<Player> battingplayerList, ConcurrentHashMap<Player, PlayerPosition> battingplayers) {
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
    }

    public void checkForWin(Team bowlingTeam,Team battingTeam){

            ScoreSummaryDto  scoreBoardBowlingTeam = scoreBoardReository.getSumOfRunsAndWicketsByTeamId(bowlingTeam.id);
            ScoreSummaryDto  scoreBoardBattingTeam = scoreBoardReository.getSumOfRunsAndWicketsByTeamId(battingTeam.id);


            bowlingTeam.setTotalWickets(scoreBoardBowlingTeam.getTotalWickets().intValue());
            bowlingTeam.setTotalRuns(scoreBoardBowlingTeam.getTotalRuns().intValue());

            if (scoreBoardBowlingTeam.getTotalRuns() > scoreBoardBattingTeam.getTotalRuns()) {
                bowlingTeam.setMatchStatus(MatchStatus.WON);
                battingTeam.setMatchStatus(MatchStatus.LOSS);
            } else if (scoreBoardBowlingTeam.getTotalRuns() == scoreBoardBattingTeam.getTotalRuns()) {
                bowlingTeam.setMatchStatus(MatchStatus.TIE);
                battingTeam.setMatchStatus(MatchStatus.TIE);
            }
            else{
                bowlingTeam.setMatchStatus(MatchStatus.LOSS);
                battingTeam.setMatchStatus(MatchStatus.TIE);
            }


            battingTeam.setTotalWickets(scoreBoardBattingTeam.getTotalWickets().intValue());
            battingTeam.setTotalRuns(scoreBoardBattingTeam.getTotalRuns().intValue());

    }

    public void play(int noOfOvers,ConcurrentHashMap<Player,PlayerPosition> battingPlayers,
                     ConcurrentHashMap<Player,PlayerPosition> bowlingPlayers ,Team battingTeam,Team bowlingTeam,
                        int inning){
        Random random = new Random();
        Player playerOnStrike = new Player();
        Player playerOnNonStrike = new Player();

        for(int over=0;over<noOfOvers;over++){
            for(int ball=0;ball<6;ball++){
                int run = random.nextInt(8);
                for(Map.Entry<Player,PlayerPosition> map : battingPlayers.entrySet()){
                    if(map.getValue().equals(PlayerPosition.ON_STRICK)){
                        playerOnStrike = map.getKey();
                    }

                    if(map.getValue().equals(PlayerPosition.ON_NON_STRICK)){
                        playerOnNonStrike = map.getKey();
                    }

                }

                setRuns(battingPlayers, bowlingPlayers, run, playerOnStrike, playerOnNonStrike);
                setScoreBoard(battingTeam, over, ball, run);

                if(inning == 2){
                    ScoreSummaryDto bowling = scoreBoardReository.getSumOfRunsAndWicketsByTeamId(bowlingTeam.id);
                    ScoreSummaryDto  batting = scoreBoardReository.getSumOfRunsAndWicketsByTeamId(battingTeam.id);

                    if(batting.getTotalRuns() > bowling.getTotalRuns()){
                        return;
                    }
                }

                if(battingPlayers.size() < 2){
                    return;
                }
            }
            changeBowler(bowlingPlayers);
        }
    }

    private void setScoreBoard(Team battingTeam, int over, int ball, int run) {
        ScoreBoard scoreBoard = scoreBoardReository.findLastScoreBoardByTeamId(battingTeam.id);
        int totalRuns = scoreBoard != null ? scoreBoard.totalRuns : 0, totalWickets = scoreBoard != null ? scoreBoard.totalWickets : 0;
        ScoreBoard setScoreBoard = new ScoreBoard();
        setScoreBoard.setNoOfOvers(over+1);
        setScoreBoard.setNoOfBowls(ball+1);
        setScoreBoard.setTeam(battingTeam);
        if(run == 7){
            setScoreBoard.setNoOfWickets(1);
            setScoreBoard.setTotalWickets(totalWickets+1);
            totalWickets+=1;

        }else{
            setScoreBoard.setRuns(run);
            setScoreBoard.setTotalRuns(totalRuns+run);
            totalRuns+=run;

        }

        setScoreBoard.setTotalRuns(totalRuns);
        setScoreBoard.setTotalWickets(totalWickets);

        scoreBoardReository.save(setScoreBoard);
    }

    private void setRuns(ConcurrentHashMap<Player, PlayerPosition> battingplayers, ConcurrentHashMap<Player, PlayerPosition> bowlingplayers, int run, Player playerOnStrike, Player playerOnNonStrike) {
        if(run != 7){
            PlayerCard playerCard = playerCardRepository.findByPlayerId(playerOnStrike.id);
            if(run == 4){
                playerCard.setNoOfFours(playerCard.getNoOfFours()+1);
            }
            if(run == 6){
                playerCard.setNoOfSixes(playerCard.getNoOfSixes()+1);
            }

            if(run == 1 || run == 3){
                battingplayers.put(playerOnNonStrike,PlayerPosition.ON_STRICK);
                battingplayers.put(playerOnStrike,PlayerPosition.ON_NON_STRICK);
            }

            playerCard.setTotalRuns(playerCard.getTotalRuns()+ run);
            playerCardRepository.save(playerCard);

        }
        else{
            manageWicket(battingplayers, bowlingplayers, playerOnStrike);
        }
    }

    private void manageWicket(ConcurrentHashMap<Player, PlayerPosition> battingplayers, ConcurrentHashMap<Player, PlayerPosition> bowlingplayers, Player playerOnStrick) {
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

    private void changeBowler(ConcurrentHashMap<Player, PlayerPosition> bowlingplayers) {
        Player currentBowler = new Player();
        for(Map.Entry<Player,PlayerPosition> map : bowlingplayers.entrySet()){

            if(map.getValue().equals(PlayerPosition.BOWLER)){
                currentBowler = map.getKey();
            }
        }
        for(Map.Entry<Player,PlayerPosition> map : bowlingplayers.entrySet()){
            if(map.getValue().equals(PlayerPosition.YET_TO_PLAY)){
                bowlingplayers.put(map.getKey(),PlayerPosition.BOWLER);
                break;
            }

        }
        bowlingplayers.put(currentBowler,PlayerPosition.YET_TO_PLAY);
    }

    @Override
    public MatchSummaryResponseDto startGame(GameDetailsDto gameDetailsDto){

        Optional<Game> game = playGame(gameDetailsDto);

        List<Team> result = teamRepository.findByGameId(game.get().id);


        List<ScoreCardResponseDto> scoreCardResponseDtoList = new ArrayList<>();
        for(Team team : result){

            ScoreCardResponseDto scoreCardResponseDto = new ScoreCardResponseDto();
            scoreCardResponseDto.setNoOfRuns(team.getTotalRuns());
            scoreCardResponseDto.setNoOfWickets(team.getTotalWickets());
            scoreCardResponseDto.setTeamName(team.getName());

            scoreCardResponseDtoList.add(scoreCardResponseDto);
        }

        MatchSummaryResponseDto matchSummaryResponseDto = new MatchSummaryResponseDto();
        matchSummaryResponseDto.setGameId(game.get().id);
        matchSummaryResponseDto.setScoreCardResponseDtoList(scoreCardResponseDtoList);

        return  matchSummaryResponseDto;

    }

    private Optional<Game> playGame(GameDetailsDto gameDetailsDto) {
        Optional<Game> game = gameRepository.findById(gameDetailsDto.getGameId());
        if(game.get().getGameStatus().equals(GameStatus.ENDED)){
            throw new GameEndedException(Constants.GAME_PLAYED);
        }

        Team teamBatting;
        Team teamBowling;

        for(int inning=0;inning<2;inning++){

             teamBatting = teamRepository.findByTeamTypeAndGameId(TeamType.BATTING, gameDetailsDto.getGameId());
             teamBowling = teamRepository.findByTeamTypeAndGameId(TeamType.BOWLING, gameDetailsDto.getGameId());

            playInnings(teamBatting,teamBowling, gameDetailsDto.getNoOfOvers(),inning+1);

             teamBatting.setTeamType(TeamType.BOWLING);
             teamBowling.setTeamType(TeamType.BATTING);

             teamRepository.save(teamBatting);
             teamRepository.save(teamBowling);
        }

        game.get().setGameStatus(GameStatus.ENDED);
        gameRepository.save(game.get());
        return game;
    }
}
