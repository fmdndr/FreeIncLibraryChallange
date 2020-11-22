package com.freeinc.Library.DataAccess;

import java.util.List;

import com.freeinc.Library.Entites.Publisher;

public interface IPublisherDao {

	void addPublisher(Publisher publisher);

	List<Publisher> getAll();

	Publisher findById(int id);

	void removePublisher(Publisher publisher);

	void updatePublisher(Publisher publisher);

}
