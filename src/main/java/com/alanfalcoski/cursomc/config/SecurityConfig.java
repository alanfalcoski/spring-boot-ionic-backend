package com.alanfalcoski.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private Environment env;

	private static final String[] PUBLIC_MATCHERS = {
			"/h2-console/**"
	};
	
	private static final String[] PUBLIC_MATCHERS_GET = {			
			"/produtos/**",
			"/categorias/**"
	};
	
	/* Todas as URLS's do vetor PUBLIC_MATCHERS serão permitidas, outras requisições serão autenticadas */
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		//Permitir acesso ao H2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();			
		}
		
		
		//não efetuar verificação de ataque por sessão
		http.cors().and().csrf().disable();
		//permitir o que estiver na array
		http.authorizeRequests()			
			//Na lista PUBLIC_MATCHERS_GET não será possível alterar.
			.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll()
			.antMatchers(PUBLIC_MATCHERS).permitAll()
			//autenticar outras requisições
			.anyRequest().authenticated();
		//não criar sessão do usuário
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	
	  @Bean
	  CorsConfigurationSource corsConfigurationSource() {
	    final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
	    return source;
	  }
}
