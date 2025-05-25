package CA2.FoodCA2.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import CA2.FoodCA2.DTO.RecipeDTO;





@Service
public class RecipeService {

	
	@Value("${spoonacular.api.key}")
	private String apiKey;
	
	
	public List<RecipeDTO> searchRecipes(String query){
		String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&number=5&apiKey=" + apiKey;


		RestTemplate restTemplate = new RestTemplate();
	    Map response = restTemplate.getForObject(url, Map.class);

		List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");


		List<RecipeDTO> recipes = new ArrayList<>();
	    for (Map<String, Object> r : results) {
	        RecipeDTO dto = new RecipeDTO();
			dto.setTitle((String) r.get("title"));
			dto.setImage((String) r.get("image"));
			dto.setId((Integer) r.get("id"));

			recipes.add(dto);

	}
	    return recipes;
		}

	public Map<String, Object> getNutritionInfo(int id) {
		String url = "https://api.spoonacular.com/recipes/" + id + "/nutritionWidget.json?apiKey=" + apiKey;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Map.class);
	}

	public Map<String, Object> getRecipeDetails(int id) {
		String url = "https://api.spoonacular.com/recipes/" + id + "/information?includeNutrition=false&apiKey=" + apiKey;
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.getForObject(url, Map.class);
	}


	public List<String> getRecipeIngredients(int id) {
		String url = "https://api.spoonacular.com/recipes/" + id + "/ingredientWidget.json?apiKey=" + apiKey;
		RestTemplate restTemplate = new RestTemplate();
		Map<String, Object> response = restTemplate.getForObject(url, Map.class);

		List<Map<String, Object>> ingredients = (List<Map<String, Object>>) response.get("ingredients");

		List<String> ingredientList = new ArrayList<>();
		for (Map<String, Object> ingredient : ingredients) {
			ingredientList.add((String) ingredient.get("name"));
		}

		return ingredientList;
	}




}
