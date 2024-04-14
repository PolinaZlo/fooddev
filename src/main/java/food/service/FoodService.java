package food.service;

import food.model.Food;

import java.util.Collection;
import java.util.List;

public interface FoodService {
    Food save(Food food);
    Collection<Food> findAll();
    Food findById(Long id);
    List<Food> findByName(String title);
    void update(Food food);

    void delete(Food food);
}
