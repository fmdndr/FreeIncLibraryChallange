package com.freeinc.Library.DataAccess;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.freeinc.Library.Entites.Publisher;

@Repository
public class HibernatePublisherDao implements IPublisherDao {

	private EntityManager entityManager;

	@Autowired
	public HibernatePublisherDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	@Transactional
	public void addPublisher(Publisher publisher) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(publisher);
	}

	@Override
	@Transactional
	public List<Publisher> getAll() {
		Session publisher = entityManager.unwrap(Session.class);
		List<Publisher> publish = publisher.createQuery("from Publisher", Publisher.class).getResultList();
		publisher.close();
		return publish;
	}

	@Override
	@Transactional
	public Publisher findById(int id) {
		Session findPublisherById = entityManager.unwrap(Session.class);
		Publisher founded = findPublisherById.get(Publisher.class, id);
		findPublisherById.close();
		return founded;
	}

	@Override
	@Transactional
	public void removePublisher(Publisher publisher) {
		Session deletePublisherById = entityManager.unwrap(Session.class);
		Publisher exPublisher = deletePublisherById.get(Publisher.class, publisher.getId());
		deletePublisherById.delete(exPublisher);
		deletePublisherById.close();
	}

	@Override
	@Transactional
	public void updatePublisher(Publisher publisher) {
		Session updatePublisher = entityManager.unwrap(Session.class);
		updatePublisher.saveOrUpdate(publisher);
		updatePublisher.close();
	}

}
