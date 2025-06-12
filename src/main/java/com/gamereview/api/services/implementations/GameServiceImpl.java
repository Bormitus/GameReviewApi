package com.gamereview.api.services.implementations;

import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.GameResponseDto;
import com.gamereview.api.exceptions.GameNotFoundException;
import com.gamereview.api.models.Game;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.services.interfaces.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public GameDto createGame(GameDto gameDto) {
        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setDescription(gameDto.getDescription());

        Game newGame = gameRepository.save(game);

        GameDto gameResponse = new GameDto();
        gameResponse.setId(newGame.getId());
        gameResponse.setTitle(newGame.getTitle());
        gameResponse.setDescription(newGame.getDescription());

        return gameResponse;
    }

    @Override
    public GameResponseDto getAllGames(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Game> games = gameRepository.findAll(pageable);
        List<Game> listOfGames = games.getContent();
        List<GameDto> content = listOfGames.stream().map(game -> mapToDto(game)).collect(Collectors.toList());

        GameResponseDto gameResponseDto = new GameResponseDto();
        gameResponseDto.setContent(content);
        gameResponseDto.setPageNumber(games.getNumber());
        gameResponseDto.setPageSize(games.getSize());
        gameResponseDto.setTotalPages(games.getTotalPages());
        gameResponseDto.setTotalElements(games.getTotalElements());
        gameResponseDto.setLast(games.isLast());

        return gameResponseDto;
    }

    @Override
    public GameDto getGameById(int id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game could not be found"));
        return mapToDto(game);
    }

    @Override
    public GameDto updateGame(GameDto gameDto, int id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game could not be found"));
        game.setTitle(gameDto.getTitle());
        game.setDescription(gameDto.getDescription());
        Game updatedGame = gameRepository.save(game);
        return mapToDto(updatedGame);
    }

    @Override
    public void deleteGameById(int id) {
        Game game = gameRepository.findById(id).orElseThrow(() -> new GameNotFoundException("Game could not be found"));
        gameRepository.delete(game);
    }

    private GameDto mapToDto(Game game)
    {
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setTitle(game.getTitle());
        gameDto.setDescription(game.getDescription());

        return gameDto;
    }

    private Game mapToEntity(GameDto gameDto)
    {
        Game game = new Game();
        game.setTitle(gameDto.getTitle());
        game.setDescription(gameDto.getDescription());
        return game;

    }
}
