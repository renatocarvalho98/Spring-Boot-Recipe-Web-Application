package CA2.FoodCA2.Repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

import CA2.FoodCA2.Model.Recipe;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByTitleContainingIgnoreCase(String keyword);
    List<Recipe> findByUserId(String userId);
}
