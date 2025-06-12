package com.gamereview.api.services;

import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.GameResponseDto;
import com.gamereview.api.models.Game;
import com.gamereview.api.repositories.GameRepository;
import com.gamereview.api.services.implementations.GameServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTests {

    @Mock
    private GameRepository gameRepository;

    @InjectMocks
    private GameServiceImpl gameServiceImpl;

    @Test
    public void GameService_CreateGame_ReturnsGameDto() {
        Game game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        GameDto gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();

        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        GameDto result = gameServiceImpl.createGame(gameDto);

        Assertions.assertNotNull(result);
    }

    @Test
    public void GameService_GetAllGames_ReturnsGameResponse()
    {
        Page<Game> games = Mockito.mock(Page.class);

        when(gameRepository.findAll(Mockito.any(Pageable.class))).thenReturn(games);

        GameResponseDto result = gameServiceImpl.getAllGames(1,1);

        Assertions.assertNotNull(result);
    }

    @Test
    public void GameService_GetGameById_ReturnsGameDto() {
        Game game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        GameDto gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();

        when(gameRepository.findById(1)).thenReturn(Optional.ofNullable(game));

        GameDto result = gameServiceImpl.getGameById(1);

        Assertions.assertNotNull(result);
    }

    @Test
    public void GameService_UpdateGame_ReturnsGameDto() {
        Game game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        GameDto gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();

        when(gameRepository.findById(1)).thenReturn(Optional.ofNullable(game));
        when(gameRepository.save(Mockito.any(Game.class))).thenReturn(game);

        GameDto result = gameServiceImpl.updateGame(gameDto,1);

        Assertions.assertNotNull(result);
    }

    @Test
    public void GameService_DeleteGame_ReturnsVoid() {
        Game game = Game.builder().title("Game Title 1").description("Game Description 1").build();
        GameDto gameDto = GameDto.builder().title(game.getTitle()).description(game.getDescription()).build();

        when(gameRepository.findById(1)).thenReturn(Optional.ofNullable(game));

        Assertions.assertAll(() -> gameServiceImpl.deleteGameById(1));
    }
}
