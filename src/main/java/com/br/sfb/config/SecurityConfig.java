package com.br.sfb.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.br.sfb.custom.CustomUserDetailsService;




@EnableWebSecurity
@ComponentScan(basePackageClasses = CustomUserDetailsService.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	 @Autowired 
	 private UserDetailsService userDetailsService;
 
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
		.antMatchers("/layout/**").antMatchers("/usuario/**");
	}
	 @Autowired
	 public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {    
		 auth.userDetailsService(userDetailsService).passwordEncoder(passwordencoder());
	 } 
	 
 @Override
 protected void configure(final HttpSecurity http) throws Exception {
     http.authorizeRequests()
		.antMatchers("/**").authenticated()	
		.anyRequest().authenticated()	
		.and()
         .formLogin()      
         .loginPage("/login")
         .defaultSuccessUrl("/home",true) 
         .failureUrl("/login?error=true").permitAll()
         .and().logout().logoutSuccessUrl("/login").deleteCookies("JSESSIONID").and().csrf().disable();
     

 }
	@Bean(name="passwordEncoder")
    public PasswordEncoder passwordencoder(){
     return new BCryptPasswordEncoder();
    }
	

}
