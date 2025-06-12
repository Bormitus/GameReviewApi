package com.gamereview.api.repositories;

import com.gamereview.api.models.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class GameRepositoryTests {
    @Autowired
    private GameRepository gameRepository;

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(gameRepository);
    }

    @Test
    public void GameRepository_SaveAll_ReturnsSavedGame() {

        Game game = Game.builder().title("Game Title").description("Game Description").build();

        Game savedGame = gameRepository.save(game);

        Assertions.assertNotNull(savedGame);
        Assertions.assertTrue(savedGame.getId() > 0);
    }

    @Test
    public void GameRepository_GetAll_ReturnsMoreThanOneGame() {
        Game game1 = Game.builder().title("Game Title 1").description("Game Description 1").build();
        Game game2 = Game.builder().title("Game Title 2").description("Game Description 2").build();

        Game savedGame = gameRepository.save(game1);
        Game savedGame2 = gameRepository.save(game2);

        List<Game> allGames = gameRepository.findAll();

        Assertions.assertNotNull(allGames);
        Assertions.assertTrue(allGames.size() == 2);
    }

    @Test
    public void GameRepository_FindById_ReturnsGame() {
        Game game1 = Game.builder().title("Game Title 1").description("Game Description 1").build();

        Game savedGame = gameRepository.save(game1);

        Game game = gameRepository.findById(savedGame.getId()).get();

        Assertions.assertNotNull(game);
    }

    @Test
    public void GameRepository_FindByTitle_ReturnsGame() {
        Game game1 = Game.builder().title("Game Title 1").description("Game Description 1").build();

        Game savedGame = gameRepository.save(game1);

        Game game = gameRepository.findByTitle(savedGame.getTitle()).get();

        Assertions.assertNotNull(game);
    }

    @Test
    public void GameRepository_Update_ReturnsUpdatedGame() {
        Game game1 = Game.builder().title("Game Title 1").description("Game Description 1").build();

        Game savedGame = gameRepository.save(game1);

        Game game = gameRepository.findById(savedGame.getId()).get();

        game.setTitle("Game Title 2");
        game.setDescription("Game Description 2");

        Game updatedGame = gameRepository.save(game);

        Assertions.assertNotNull(updatedGame);
        Assertions.assertNotNull(updatedGame.getTitle());
        Assertions.assertNotNull(updatedGame.getDescription());
    }

    @Test
    public void GameRepository_Delete_ReturnsNull() {
        Game game1 = Game.builder().title("Game Title 1").description("Game Description 1").build();

        gameRepository.save(game1);
        gameRepository.deleteById(game1.getId());

        Optional<Game> optionalGame = gameRepository.findById(game1.getId());

        Assertions.assertFalse(optionalGame.isPresent());
    }
}
