package br.com.antunes.gustavo.usefultools.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class CustomSecurityConfiguration {

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
		http.securityMatcher(PathRequest.toH2Console());
		http.authorizeHttpRequests().requestMatchers("/swagger-ui/**").permitAll();
		http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll();
		http.authorizeHttpRequests().requestMatchers(AntPathRequestMatcher.antMatcher("/tools/**")).permitAll();
		http.csrf((csrf) -> csrf.disable());
		http.headers((headers) -> headers.frameOptions().sameOrigin());
		return http.build();
	}

}
