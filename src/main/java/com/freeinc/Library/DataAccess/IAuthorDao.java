package com.freeinc.Library.DataAccess;

import java.util.List;

import com.freeinc.Library.Entites.Author;

public interface IAuthorDao {

	void saveAuthor(Author author);

	List<Author> getAllAuthor();

	Author findById(int id);
	
	void removeAuthor(Author author);
	
	void updateAuthor(Author author);

	
}
