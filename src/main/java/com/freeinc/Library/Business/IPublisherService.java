package com.freeinc.Library.Business;

import java.util.List;

import com.freeinc.Library.Entites.Publisher;

public interface IPublisherService {

	List<Publisher> getAll();

	void addPublisher(Publisher publisher);

	Publisher findById(int id);

	void removePublisher(Publisher publisher);

	void updatePublisher(Publisher publisher);

}
