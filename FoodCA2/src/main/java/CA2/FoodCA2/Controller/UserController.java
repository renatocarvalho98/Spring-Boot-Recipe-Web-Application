package CA2.FoodCA2.Controller;

import CA2.FoodCA2.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import CA2.FoodCA2.DTO.UserDTO;
import CA2.FoodCA2.Model.User;
import CA2.FoodCA2.Service.UserService;
import jakarta.validation.Valid;
import lombok.Data;

import java.security.Principal;
import java.util.Optional;

@Data
@Controller
public class UserController {
	

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;



	@GetMapping("/register")
	public String showRegisterForm(Model model) {
		model.addAttribute("user", new UserDTO());
		return "register";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") UserDTO userDTO, BindingResult result) {
	    if (result.hasErrors()) {
	        return "register";
	    }

	    User user = new User();
	    user.setUsername(userDTO.getUsername());
	    user.setEmail(userDTO.getEmail());
	    user.setPassword(userDTO.getPassword());

	    userService.register(user);
	    return "redirect:/login";
	}
	
	@GetMapping("/login")
	public String showLoginForm() {
	     return "login";
	 }


	 @GetMapping("/profile")
	public String showProfile(Model model, Principal principal) {
		Optional<User> optionalUser = userRepository.findByUsername(principal.getName());

		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			model.addAttribute("user", user);
		}
		return "profile";
	 }


	 @PostMapping("/profile")
	public String updateProfile(@ModelAttribute User userForm, Principal principal ) {
		 User user = userRepository.findByUsername(principal.getName())
				 .orElseThrow(() -> new RuntimeException("User not found"));

		 user.setEmail(userForm.getEmail());
		 if (!userForm.getPassword().isEmpty()) {
			 user.setPassword(passwordEncoder.encode(userForm.getPassword()));
		 }
		 userRepository.save(user);
		 return "redirect:/search";

	 }

	@PostMapping("/deleteAccount")
	public String deleteAccount(Principal principal, HttpServletRequest request, HttpServletResponse response) {
		Optional<User> userOpt = userRepository.findByUsername(principal.getName());

		if (userOpt.isPresent()) {
			User user = userOpt.get();
			userRepository.delete(user);

			// Invalida sessão e logout
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null){
				new SecurityContextLogoutHandler().logout(request, response, auth);
			}

			return "redirect:/login?accountDeleted";
		}

		return "redirect:/index";
	}




	@GetMapping({"/", "/index"})
	public String index() {
		return "index"; // este é o nome do HTML: index.html
	}





}
