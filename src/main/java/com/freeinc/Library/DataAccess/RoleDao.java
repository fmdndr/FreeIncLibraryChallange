package com.freeinc.Library.DataAccess;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.freeinc.Library.Entites.Role;

@Repository
public class RoleDao implements IRoleDao {

	private EntityManager entityManager;

	@Autowired
	public RoleDao(EntityManager entityManager) {
		this.entityManager = entityManager;

	}

	@Override
	public Role findRoleByName(String roleName) {

		Session roleSession = entityManager.unwrap(Session.class);
		Query<Role> existingRole = roleSession.createQuery("from Role where name=:roleName", Role.class);
		existingRole.setParameter("roleName", roleName);
		Role userRole = null;

		try {
			userRole = existingRole.getSingleResult();
		} catch (Exception e) {
			userRole = null;
		}
        System.out.println(userRole);
		return userRole;
	}

}
