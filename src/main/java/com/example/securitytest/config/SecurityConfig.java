package com.example.securitytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity // 필터 체인 관리 시작 어노테이션
public class SecurityConfig {
	
	@Bean
	public BCryptPasswordEncoder encodePwd() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	protected void configure(HttpSecurity http) throws Exception {
		
		http.csrf().disable(); // csrf 공격 방지
		http.authorizeHttpRequests() // httpServletRequest로 들어온 요청
			.requestMatchers("/user/**").authenticated() //인증만 되면 들어갈 수 있는 주소
			//.requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
			//.requestMatchers("/admin/**").access("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
			.requestMatchers("/admin/**").hasAnyRole("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll() // 위에서 설정해준 url외에 다른 url은 접근 가능
			.and()
			.formLogin()
			.loginPage("/login")
			.loginProcessingUrl("/loginProc") // login 주소가 호출 되면 시큐리티가 낚아채서 대신 로그인을 진행해준다.
			.defaultSuccessUrl("/"); // 특정페이지 url로 치고 들어갔을때 로그인창이 뜨고 로그인에 성공하면 바로 그 특정페이지로 이동하게 해준다.
	}
}





