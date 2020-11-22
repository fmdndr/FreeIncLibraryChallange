package com.freeinc.Library.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.Entites.Author;

@Repository
public class HibernateAuthorDao implements IAuthorDao {

	private EntityManager entityManager;

	@Autowired
	public HibernateAuthorDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void saveAuthor(Author author) {
		Session authorSession = entityManager.unwrap(Session.class);
		authorSession.saveOrUpdate(author);
	}

	@Override
	@Transactional
	public List<Author> getAllAuthor() {
		Session authors = entityManager.unwrap(Session.class);
		List<Author> authorList = authors.createQuery("from Author", Author.class).getResultList();
		return authorList;
	}

	@Override
	@Transactional
	public Author findById(int id) {
		Session findAuthorById = entityManager.unwrap(Session.class);
		Author getAuthor = findAuthorById.get(Author.class, id);
		return getAuthor;
	}

	@Override
	@Transactional
	public void removeAuthor(Author author) {
		Session willRemovedAuthor = entityManager.unwrap(Session.class);
		Author exAuthor = willRemovedAuthor.get(Author.class, author.getId());
		willRemovedAuthor.delete(exAuthor);
		willRemovedAuthor.close();
	}

	@Override
	@Transactional
	public void updateAuthor(Author author) {
		Session updateAuthor = entityManager.unwrap(Session.class);
		updateAuthor.saveOrUpdate(author);
		updateAuthor.close();
		
	}

}
