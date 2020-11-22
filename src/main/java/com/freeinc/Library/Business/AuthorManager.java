package com.freeinc.Library.Business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.DataAccess.IAuthorDao;
import com.freeinc.Library.Entites.Author;

@Service
public class AuthorManager implements IAuthorService {

	private IAuthorDao authorDao;

	@Autowired
	public AuthorManager(IAuthorDao authorDao) {
		this.authorDao = authorDao;
	}

	@Override
	@Transactional
	public void saveAuthor(Author author) {
		this.authorDao.saveAuthor(author);
	}

	@Override
	@Transactional
	public List<Author> getAllAuthor() {
		return this.authorDao.getAllAuthor();
	}

	@Override
	@Transactional
	public Author findById(int id) {
		return this.authorDao.findById(id);
	}

	@Override
	@Transactional
	public void removeAuthor(Author author) {
		this.authorDao.removeAuthor(author);
	}

	@Override
	@Transactional
	public void updateAuthor(Author author) {
		this.authorDao.updateAuthor(author);
	}

}
