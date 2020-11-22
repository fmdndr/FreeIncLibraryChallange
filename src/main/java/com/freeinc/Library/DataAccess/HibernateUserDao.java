package com.freeinc.Library.DataAccess;

import javax.persistence.EntityManager;
import org.hibernate.Session;

import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freeinc.Library.Entites.User;

@Repository
public class HibernateUserDao implements IUserDao {

	private EntityManager entityManager;

	@Autowired
	public HibernateUserDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public User findByUserName(String username) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query<User> existUser = currentSession.createQuery("from User where userName=:uName", User.class);
		existUser.setParameter("uName", username);
		User user = null;
		try {
			user = existUser.getSingleResult();
		} catch (Exception e) {
			user = null;
		}

		return user;

	}

	@Override
	public void registerUser(User user) {
		Session regSession = entityManager.unwrap(Session.class);
		regSession.saveOrUpdate(user);

	}

}
