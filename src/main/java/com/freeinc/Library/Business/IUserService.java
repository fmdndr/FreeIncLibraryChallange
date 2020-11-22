package com.freeinc.Library.Business;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.freeinc.Library.Entites.User;

public interface IUserService extends UserDetailsService {

	User findByUserName(String userName);

	void registerUser(User user);
}
