package com.example.securitytest.config.auth;

import com.example.securitytest.model.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 시큐리티가 login 주소 요청이 오면 낚아채서 로그인을 진행시킨다.
// 로그인 진행이 완료되면 시큐리티 session 을 만들어 준다. (Security ContextHolder)
// 시큐리티가 가지고 있는 세션에 들어갈 수 있는 유일한 객체 타입은 Authentication 이다.
// Authentication 안에 user 정보가 있어야 하는데 이 user 정보를 저장하는곳이 UserDetails 이다.
// 한마디로 Security session 에 세션을 저장할 수 있는 객체는 Authentication 이고 Authentication 에 UserDetails 를 저장한다.
// Security session 에서 Authentication 에 접근해서 UserDetails을 꺼내오면 사용자 정보를 알 수 있다.
// 일반적으로 로그인을 하면 UserDetails 타입으로 Authentication에 들어가게 되고 구글이나 페이스북 같은 OAuth 로그인 할땐 OAuth2User 타입으로 들어가게 된다.
// UserDetails 타입으로 Authentication에 들어가게 되고 시큐리티 세션은 그 Authentication 을 가지고 있는 것.

// Authentication 객체에 저장할 수 있는 유일한 타입
@Data
public class PrincipalDetails implements UserDetails{

	private User user;

	public PrincipalDetails(User user) {
		super();
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {

		// 1년동안 회원이 로그인 안하면 휴면계정으로 하기로 함.
		// 현재 시간 - 로그인 시간 => 1년을 초과하면 return false;

		return true;
	}

	// 해당 유저의 권한을 리턴하는 곳
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collet = new ArrayList<>();
		collet.add(()->{ return user.getRole();});
		return collet;
	}


	
}
