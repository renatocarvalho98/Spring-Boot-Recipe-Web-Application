package CA2.FoodCA2.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import CA2.FoodCA2.DTO.UserDTO;
import CA2.FoodCA2.Model.User;
import CA2.FoodCA2.Repository.UserRepository;
import jakarta.validation.Valid;



@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;

	 @Autowired
	 private PasswordEncoder passwordEncoder;

	 public User register(User user) {
	     user.setPassword(passwordEncoder.encode(user.getPassword()));
	     return userRepository.save(user);
	    }
	
	  
	    
	    

}
