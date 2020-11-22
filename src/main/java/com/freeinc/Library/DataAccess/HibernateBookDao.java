package com.freeinc.Library.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.Entites.Book;

@Repository
public class HibernateBookDao implements IBookDao {

	private EntityManager entityManager;

	@Autowired
	public HibernateBookDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void save(Book book) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(book);
	}

	// I have created custom query books because of I'm managing Publishers and
	// Authors with two different table. I have implemented @OneToMany @ManyToOne
	// annotation
	// for Books Entity
	@Override
	@Transactional
	public List<Book> getBooks() {
		Session session = entityManager.unwrap(Session.class);
		List<Book> book = session.createNativeQuery(
				"SELECT book_id,book_title,book_description,isbn,series_name,sub_title,author_name as author,author_id ,publisher_id,publisher "
						+ "FROM books LEFT JOIN author ON books.author_id=author.id "
						+ "LEFT JOIN publisher ON books.publisher_id = publisher.id",
				Book.class).getResultList();
		System.out.println(book);
		return book;
	}

	@Override
	@Transactional
	public void update(Book book) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(book);
		session.close();
	}

	@Override
	@Transactional
	public Book findById(int id) {
		Session session = entityManager.unwrap(Session.class);
		Book book = session.get(Book.class, id);
		return book;
	}

	@Override
	@Transactional
	public void removeBook(Book book) {
		Session willRemovedBook = entityManager.unwrap(Session.class);
		Book exBook = willRemovedBook.get(Book.class, book.getId());
		willRemovedBook.delete(exBook);
		willRemovedBook.close();
	}

}
