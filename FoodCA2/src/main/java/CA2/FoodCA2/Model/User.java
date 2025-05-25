package CA2.FoodCA2.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Document("users") //collection no MongoDB
public class User {
	
	@Id	
	private String id;
	private String username;
	private String email;
	private String password;
	private String role = "USER";
	
	
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}




	private List<String> savedRecipeIds = new ArrayList<>();

	public List<String> getSavedRecipeIds() {
		return savedRecipeIds;
	}

	public void setSavedRecipeIds(List<String> savedRecipeIds) {
		this.savedRecipeIds = savedRecipeIds;
	}
}
