package com.freeinc.Library.Business;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.DataAccess.IRoleDao;
import com.freeinc.Library.DataAccess.IUserDao;
import com.freeinc.Library.Entites.Role;
import com.freeinc.Library.Entites.User;

@Service
public class UserManager implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private IRoleDao roleDao;

	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public User findByUserName(String userName) {
		return this.userDao.findByUserName(userName);
	}

	@Override
	@Transactional
	public void registerUser(User user) {
		User register = new User();
		register.setUserName(user.getUserName());
		register.setFirstName(user.getFirstName());
		register.setLastName(user.getLastName());
		register.setPassword(passwordEncoder.encode(user.getPassword()));
		register.setEmail(user.getEmail());

		register.setRoles(Arrays.asList(roleDao.findRoleByName("ROLE_USERS")));
		// Save user
		userDao.registerUser(register);

	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userDao.findByUserName(userName);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

}
