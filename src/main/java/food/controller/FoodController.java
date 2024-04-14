package food.controller;

import food.dto.FoodDto;
import food.mapper.FoodMapper;
import food.model.Food;
import food.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/food")
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "Save food", description = "Save a new food item")
    @PostMapping("/save")
    public ResponseEntity<Food> save(@RequestBody FoodDto foodDto) {
        Food food = new FoodMapper().getFood(foodDto);
        log.info("Сохранение еды: " + foodDto.getFoodTitle());
        return ResponseEntity.ok(foodService.save(food));
    }

    @Operation(summary = "Find food by ID", description = "Find a food item by its ID")
    @GetMapping("/findById")
    public ResponseEntity<Food> findById(@Parameter(in = ParameterIn.QUERY, description = "ID of food to be searched", required = true)
                                         @RequestParam Long id) {
        return ResponseEntity.ok(foodService.findById(id));
    }

    @Operation(summary = "Find food by Name", description = "Find a food item by its Name")
    @GetMapping("/findByName")
    public ResponseEntity<List<Food>> findByName(@Parameter(in = ParameterIn.QUERY, description = "Name of food to be searched", required = true)
                                         @RequestParam String title) {
        return ResponseEntity.ok(foodService.findByName(title));
    }

    @Operation(summary = "Get all food items", description = "Get a list of all food items")
    @GetMapping("/getAll")
    public ResponseEntity<List<Food>> getAll() {
        return ResponseEntity.ok(foodService.findAll().stream().toList());
    }

    @Operation(summary = "Update food", description = "Update an existing food item")
    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody Food food) {
        foodService.update(food);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Delete food", description = "Delete a food item")
    @DeleteMapping("/delete")
    public ResponseEntity<Void> delete(@RequestBody Food food) {
        foodService.delete(food);
        return ResponseEntity.ok().build();
    }
}
