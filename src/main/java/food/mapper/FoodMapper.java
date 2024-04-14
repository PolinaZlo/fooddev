package food.mapper;

import food.dto.FoodDto;
import food.model.Food;

public class FoodMapper {

    public FoodDto getDto(Food food) {
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodTitle(food.getTitle());
        foodDto.setFoodPrice(food.getPrice());
        return foodDto;
    }

    public Food getFood(FoodDto foodDto) {
        Food food = new Food();
        food.setTitle(foodDto.getFoodTitle());
        food.setPrice(foodDto.getFoodPrice());
        return  food;
    }

}
