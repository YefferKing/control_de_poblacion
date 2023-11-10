package com.gestion.empleados;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	@Bean
	protected UserDetailsService userDetailsService() {
		UserDetails usuario1 = User
				.withUsername("yeffer03")
				.password("$2a$10$24tuFpb/Rl3d92Fo06C0POicLqaOL92VHFzJpTS0b.pZr2rAsNYri")
				.roles("USER")	
				.build();

		UserDetails usuario2 = User
				.withUsername("Aux1")
				.password("$2a$10$BhQm0lmuuNB.7yYAaMiFYO32nKV5FArzhjEaUXvxrNpIE/9mKhoGO")
				.roles("USER")
				.build();

		UserDetails usuario3 = User
				.withUsername("Aux2")
				.password("$2a$10$dJDD5aO0PU3LwXQMGbnZV.AvxT.x7ANJAjQ2021yOR.ffPPM.1xKW")
				.roles("USER")
				.build();

		UserDetails usuario4 = User
				.withUsername("Aux3")
				.password("$2a$10$uMD0UjEd4.r1EW/hL6jFCu7lMxvwEKXTr2ETOAwwYE6QOS37lqDpe")
				.roles("USER")
				.build();

		UserDetails usuario5 = User
				.withUsername("Aux4")
				.password("$2a$10$58/U1tsT1evaFzLHTBjW5eyo8/EZFJbNmd3fezOqTLHnpb/cyNIYG")
				.roles("USER")
				.build();
		
		UserDetails usuario6 = User
				.withUsername("R.ASEGURAMIENTO")
				.password("$2a$10$UlNo8qo36t9e8DLH8AlCneubTbBFmKQwjtBAAzUSPl7Eg4zd1F3QW")
				.roles("ADMIN")	
				.build();
		
		return new InMemoryUserDetailsManager(usuario1,usuario2,usuario3,usuario4,usuario5,usuario6);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		    .antMatchers("/").permitAll()
		    .antMatchers("/form/*","/eliminar/*").hasRole("ADMIN")
		    .anyRequest().authenticated()
		    .and()
		    .formLogin()
		        .loginPage("/login")
		        .permitAll()
		    .and()
		    .logout().permitAll();
	}
}
