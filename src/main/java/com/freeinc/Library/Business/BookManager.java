package com.freeinc.Library.Business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.DataAccess.IBookDao;
import com.freeinc.Library.Entites.Book;

@Service
public class BookManager implements IBookService {

	private IBookDao bookDao;

	@Autowired
	public BookManager(IBookDao bookDao) {
		this.bookDao = bookDao;
	}

	@Override
	@Transactional
	public void save(Book book) {
		this.bookDao.save(book);
	}

	@Override
	@Transactional
	public Book findById(int id) {
		return this.bookDao.findById(id);
	}

	@Override
	@Transactional
	public void update(Book book) {
		this.bookDao.update(book);

	}

	@Override
	@Transactional
	public List<Book> getBooks() {
		return this.bookDao.getBooks();
	}

	@Override
	@Transactional
	public void removeBook(Book book) {
		this.bookDao.removeBook(book);
	}

}
