package CA2.FoodCA2.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import CA2.FoodCA2.Model.User;


public interface UserRepository extends MongoRepository<User,String> {
		Optional<User> findByUsername(String username);
		void deleteByUsername(String username);

}

	


