package com.freeinc.Library.Business;

import java.util.List;

import com.freeinc.Library.Entites.Author;

public interface IAuthorService {

	void saveAuthor(Author author);

	List<Author> getAllAuthor();

	Author findById(int id);
	
	void removeAuthor(Author author);
	
	void updateAuthor(Author author);

}
