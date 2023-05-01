package console.game.units;

import java.util.List;

public record GameStateDTO(List<SnakeDTO> snakes,
                           List<WallDTO> walls,
                           List<FoodDTO> foodDTOS) {
}
