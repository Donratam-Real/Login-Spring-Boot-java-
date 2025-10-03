package com.example.demo.config;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(auth -> auth
				.requestMatchers("/login", "/register", "/h2-console/**").permitAll()
				.anyRequest().authenticated()
			)
			.csrf(csrf -> csrf
				.ignoringRequestMatchers("/h2-console/**")
			)
			.headers(headers -> headers
				.frameOptions(frame -> frame.sameOrigin())
			)
			.formLogin(form -> form
				.loginPage("/login")
				.defaultSuccessUrl("/home", true)
				.permitAll()
			)
			.httpBasic(withDefaults());

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

    // UserDetailsService is provided via com.example.demo.service.UserDetailsServiceImpl
}


