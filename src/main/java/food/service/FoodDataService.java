package food.service;

import food.error.FoodNotFoundException;
import food.model.Food;
import food.repository.FoodRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FoodDataService implements FoodService {

    private final FoodRepository foodRepository;

    public Food save(Food food) {
        return foodRepository.saveAndFlush(food);
    }
    public void update(Food food) {
        Food foodToBeUpdated = foodRepository.findById(food.getId()).get();
        foodToBeUpdated.setTitle(food.getTitle());
        foodToBeUpdated.setPrice(food.getPrice());
        foodRepository.saveAndFlush(foodToBeUpdated);
    }

    public Food findById(Long id) {
        Optional<Food> foodOptional = foodRepository.findById(id);
        if (foodOptional.isPresent()) {
            return foodOptional.get();
        } else {
            throw new FoodNotFoundException("Food not found with id: " + id);
        }
    }
    public List<Food> findByName(String title) {
        List<Food> sortedFoodList = foodRepository.findAll().stream()
                .filter(food -> food.getTitle().equals(title))
                .collect(Collectors.toList());
        return sortedFoodList;
    }

    public List<Food> findAll() {
        return foodRepository.findAll();
    }

    public void delete(Food food) {
        foodRepository.delete(food);
    }

}
