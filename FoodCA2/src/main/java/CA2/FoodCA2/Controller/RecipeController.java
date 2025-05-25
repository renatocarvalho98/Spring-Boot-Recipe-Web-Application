package CA2.FoodCA2.Controller;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import CA2.FoodCA2.Model.User;
import CA2.FoodCA2.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import CA2.FoodCA2.DTO.RecipeDTO;
import CA2.FoodCA2.Model.Recipe;
import CA2.FoodCA2.Repository.RecipeRepository;
import CA2.FoodCA2.Service.RecipeService;


@Controller
public class RecipeController {

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private RecipeRepository recipeRepository;

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/search")
	public String search(@RequestParam(required = false) String query, Model model) {
		List<RecipeDTO> recipes = (query != null && !query.isEmpty())
				? recipeService.searchRecipes(query)
				: List.of();

		model.addAttribute("recipes", recipes);
		return "search";
	}

	@PostMapping("/save")
	public String saveRecipe(@RequestParam String title,
							 @RequestParam String image,
							 @RequestParam int calories,
							 @RequestParam double protein,
							 @RequestParam double fat,
							 @RequestParam double carbohydrates,
							 @RequestParam String instructions,
							 @RequestParam String ingredients,
								Principal principal
													) {
		User user = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("User not found"));

		Recipe recipe = new Recipe();
		recipe.setTitle(title);
		recipe.setImage(image);
		recipe.setCalories(calories);
		recipe.setProtein(protein);
		recipe.setFat(fat);
		recipe.setCarbohydrates(carbohydrates);
		recipe.setInstructions(instructions);
		recipe.setIngredients(List.of(ingredients.split(",")));
		recipe.setUserId(user.getId());

		recipeRepository.save(recipe);
		return "redirect:/search";
	}

	@GetMapping("/details/{id}")
	public String getDetails(@PathVariable int id, Model model) {
		Map<String, Object> nutrition = recipeService.getNutritionInfo(id);
		Map<String, Object> info = recipeService.getRecipeDetails(id);
		List<String> ingredients = recipeService.getRecipeIngredients(id);

		RecipeDTO recipe = new RecipeDTO();
		recipe.setId(id);
		recipe.setTitle((String) info.get("title"));
		recipe.setImage((String) info.get("image"));
		recipe.setInstructions((String) info.get("instructions"));
		recipe.setCalories(Integer.parseInt(nutrition.get("calories").toString()));
		recipe.setProtein(Double.parseDouble(nutrition.get("protein").toString().replace("g", "")));
		recipe.setFat(Double.parseDouble(nutrition.get("fat").toString().replace("g", "")));
		recipe.setCarbohydrates(Double.parseDouble(nutrition.get("carbs").toString().replace("g", "")));
		recipe.setIngredients(ingredients);

		model.addAttribute("recipe", recipe);
		return "details";
	}


	@GetMapping("/saved")
	public String viewSavedRecipes(Model model, Principal principal) {
		Optional<User> optionalUser = userRepository.findByUsername(principal.getName());

		if (optionalUser.isPresent()) {
			String userId = optionalUser.get().getId(); // extrai o User do Optional
			List<Recipe> savedRecipes = recipeRepository.findByUserId(userId);
			model.addAttribute("recipes", savedRecipes);
			return "saved";

		}else {
			return "redirect:/search";
		}
    }

	@PostMapping("/deleteRecipe")
	public String deleteRecipe(@RequestParam String id, Principal principal) {
		Optional<User> userOpt = userRepository.findByUsername(principal.getName());
		if (userOpt.isPresent()) {
			Recipe recipe = recipeRepository.findById(id).orElse(null);
			if (recipe != null && recipe.getUserId().equals(userOpt.get().getId())) {
				recipeRepository.delete(recipe);
			}
		}
		return "redirect:/saved";
	}





}

