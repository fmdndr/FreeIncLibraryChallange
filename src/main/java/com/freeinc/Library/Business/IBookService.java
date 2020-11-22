package com.freeinc.Library.Business;

import java.util.List;

import com.freeinc.Library.Entites.Book;

public interface IBookService {

	void save(Book book);

	Book findById(int id);

	void update(Book book);

	List<Book> getBooks();

	void removeBook(Book book);
}
