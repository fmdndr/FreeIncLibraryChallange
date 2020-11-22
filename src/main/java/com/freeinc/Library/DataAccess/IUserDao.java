package com.freeinc.Library.DataAccess;

import com.freeinc.Library.Entites.User;

public interface IUserDao {

	User findByUserName(String username);

	void registerUser(User user);

}
