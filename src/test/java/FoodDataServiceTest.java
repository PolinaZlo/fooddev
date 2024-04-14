import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.ObjectMapper;
import food.controller.FoodController;
import food.dto.FoodDto;
import food.model.Food;
import food.repository.FoodRepository;
import food.service.FoodService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class FoodDataServiceTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private FoodService foodService;
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodController foodController;

    @Test
    void testFindById() throws Exception {
        Long id = 1L;
        Food expectedFood = new Food(id, "Пицца",1000);
        when(foodService.findById(id)).thenReturn(expectedFood);
        mockMvc.perform(MockMvcRequestBuilders.get("/findById")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(id));
        verify(foodService, times(1)).findById(id);
    }
    @Test
    void testFindByIdWhenNotFound() throws Exception {
        Long id = 77L;
        when(foodService.findById(id)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/findById")
                        .param("id", id.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
        verify(foodService, times(1)).findById(id);
    }
    @Test
    void testFindByName() throws Exception {
        String name = "Пицца";
        Food expectedFood = new Food(1L, "Пицца",1000);
        when(foodService.findByName(name)).thenReturn((List<Food>) expectedFood);
        mockMvc.perform(MockMvcRequestBuilders.get("/findByName")
                        .param("title", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(name));
        verify(foodService, times(1)).findByName(name);
    }
    @Test
    void testFindByNameWhenNotFound() throws Exception {
        String name = "ТестоваяЕда";
        when(foodService.findByName(name)).thenReturn(Optional.empty());
        mockMvc.perform(MockMvcRequestBuilders.get("/findByName")
                        .param("title", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].title").value(name));
        verify(foodService, times(1)).findByName(name);
    }

    @Test
    void testGetAll() throws Exception {
        Food expectedFood = new Food();
        when(foodService.findAll()).thenReturn((List<Food>) expectedFood);
        mockMvc.perform(MockMvcRequestBuilders.get("/getAll")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(foodService, times(1)).findAll();
    }
    @Test
    public void testSave() throws Exception {
        FoodDto foodDto = new FoodDto();
        foodDto.setFoodTitle("Test Food");
        foodDto.setFoodPrice(1111);
        Food food = new Food();
        food.setId(1L);
        when(foodRepository.saveAndFlush(any(Food.class))).thenReturn(food);
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/save")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        verify(foodRepository, times(1)).saveAndFlush(any(Food.class));
    }
}