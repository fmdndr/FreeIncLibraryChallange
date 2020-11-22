package com.freeinc.Library.DataAccess;

import java.util.List;

import com.freeinc.Library.Entites.Book;

public interface IBookDao {

	void save(Book book);

	List<Book> getBooks();

	void update(Book book);

	Book findById(int id);
	
	void removeBook(Book book);

}
