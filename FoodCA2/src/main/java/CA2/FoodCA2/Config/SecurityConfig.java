package CA2.FoodCA2.Config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import CA2.FoodCA2.Service.CustomUserDetailsService;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	  @Autowired
	  private CustomUserDetailsService customUserDetailsService;
	
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		
		http
		.csrf().disable()
		.authorizeHttpRequests(auth -> auth
		.requestMatchers("/", "/index", "/register", "/login", "/css/**", "/login.css","/main.css",
				"/register.css", "/index.css").permitAll()
						.requestMatchers("/profile", "/deleteAccount", "/save", "/details/**", "/saved").authenticated()
		.anyRequest().authenticated()
				)
		
		.formLogin(form -> form
			.loginPage("/login")
				.loginProcessingUrl("/login")
			.defaultSuccessUrl("/search", true)
            .permitAll()
         )
		
		.logout(logout -> logout
				.logoutSuccessUrl("/login?logout")
				.permitAll()
				
				);


		return http.build();
		
	}
	
	
	
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	 @Bean
	    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
	        return config.getAuthenticationManager();
	    }

	
}
