package food.error;

public class FoodNotFoundException extends RuntimeException {
    public FoodNotFoundException(String message) {
        super(message);
    }
}
