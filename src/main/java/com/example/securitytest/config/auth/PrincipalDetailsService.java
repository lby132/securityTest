package com.example.securitytest.config.auth;

import com.example.securitytest.model.User;
import com.example.securitytest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 시큐리티 설정에서 loginProcessingUrl("/login")
// login 요청이 오면 자동으로 UserDetailsService 타입으로 IOC 되어 있는 loadUserByUsername 함수가 실행됨.
@Service
public class PrincipalDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	//시큐리티 session(내부 Authentication(내부 UserDetails))
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			return null;
		}
		return new PrincipalDetails(user);
	}

}
