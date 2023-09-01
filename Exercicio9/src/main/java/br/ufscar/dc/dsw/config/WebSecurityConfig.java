package br.ufscar.dc.dsw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.ufscar.dc.dsw.security.UsuarioDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UsuarioDetailsServiceImpl();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable()
		.authorizeRequests()
		.antMatchers("/error", "/login/**", "/js/**", "/css/**", "/image/**", "/webjars/**").permitAll()
		// REST Controllers
		.antMatchers("/pratos", "/restaurantes").permitAll() // REST Controllers (GET - get All, POST - Create)
		.antMatchers("/pratos/{\\d+}", "/restaurantes/{\\d+}").permitAll() // REST Controllers (GET by id, PUT - Update by id, DELETE by ID)
		.antMatchers("/pratos/nomes/{\\w+}").permitAll() // REST Controllers (GET by titulo)
		.antMatchers("/reservas/usuarios/{\\d+}").permitAll() // REST Controllers (GET by UsuarioID)
		.antMatchers("/reservas/**").hasRole("USER")
		.antMatchers("/restaurantes/**", "/pratos/**", "/usuarios/**").hasRole("ADMIN")
		.anyRequest().authenticated()
		.and()
			.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
			.logout()
			.logoutSuccessUrl("/")
			.permitAll();
	}
}