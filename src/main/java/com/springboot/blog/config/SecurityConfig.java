package com.springboot.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springboot.blog.security.CustomUserDetailsService;
import com.springboot.blog.security.JwtAuthenticationEntryPoint;
import com.springboot.blog.security.JwtAuthenticationFilter;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

@Configuration
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)

@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	
	
	


	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Autowired
	private JwtAuthenticationEntryPoint authenticationEntryPoint;

	@Autowired
	private JwtAuthenticationFilter authenticationFilter;

	@Bean
	PasswordEncoder passwordEncoder() {

		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

		http.csrf(csrf -> csrf.disable()).authorizeHttpRequests((authorize) ->
		// authorize.anyRequest().authenticated()
		authorize.requestMatchers(HttpMethod.GET, "/api/v1/**").permitAll()
				// .requestMatchers(HttpMethod.GET, "/api/categories/**").permitAll()
				.requestMatchers("/api/v1/auth/**").permitAll()
				
				.requestMatchers("/v3/api-docs/**").permitAll()
				.requestMatchers("/swagger-ui/**").permitAll()
				.requestMatchers("/swagger-resources/**").permitAll()
				.requestMatchers("/swagger-ui.html/**").permitAll()
				.requestMatchers("/webjars/**").permitAll()

				.anyRequest().authenticated()

		).exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	
	

//	@Bean
//	protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//
//		http.csrf().disable().authorizeRequests().requestMatchers(HttpMethod.GET, "/api/**").permitAll()
//				.requestMatchers("/api/auth/**").permitAll().anyRequest().authenticated().and().httpBasic();
//
//		return http.build();
//
//	}

//  @Bean
//  public AuthenticationManager authenticationManagerBean() throws Exception {
//      return authenticationManagerBean();
//  }

//	@Bean
//	protected UserDetailsService userDetailsService() {
//		
//		UserDetails hemanta = User.builder().username("hemanta").password(passwordEncoder().encode( "hks")).roles("USER").build();
//		
//		UserDetails admin = User.builder().username("admin").password( passwordEncoder().encode("admin")).roles("ADMIN").build();
//		
//		return new InMemoryUserDetailsManager(hemanta ,admin);
//		
//	}

}
