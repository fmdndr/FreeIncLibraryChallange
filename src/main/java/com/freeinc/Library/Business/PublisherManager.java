package com.freeinc.Library.Business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.DataAccess.IPublisherDao;
import com.freeinc.Library.Entites.Publisher;

@Service
public class PublisherManager implements IPublisherService {

	private IPublisherDao publisherDao;

	@Autowired
	public PublisherManager(IPublisherDao publisherDao) {
		this.publisherDao = publisherDao;
	}

	@Override
	@Transactional
	public void addPublisher(Publisher publisher) {
		this.publisherDao.addPublisher(publisher);
	}

	@Override
	@Transactional
	public List<Publisher> getAll() {
		return this.publisherDao.getAll();
	}

	@Override
	@Transactional
	public Publisher findById(int id) {
		return this.publisherDao.findById(id);
	}

	@Override
	@Transactional
	public void removePublisher(Publisher publisher) {
		this.publisherDao.removePublisher(publisher);
	}

	@Override
	@Transactional
	public void updatePublisher(Publisher publisher) {
		this.publisherDao.updatePublisher(publisher);
	}

}
