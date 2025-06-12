package com.gamereview.api.services.interfaces;

import com.gamereview.api.dtos.GameDto;
import com.gamereview.api.dtos.GameResponseDto;

public interface GameService {
    GameDto createGame(GameDto gameDto);
    GameResponseDto getAllGames(int pageNumber, int pageSize);
    GameDto getGameById(int id);
    GameDto updateGame(GameDto gameDto, int id);
    void deleteGameById(int id);
}
