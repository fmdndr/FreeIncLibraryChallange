package com.freeinc.Library.Entites;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "publisher")
public class Publisher {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int Id;
	@Column(name = "publisher")
	private String publisher;
	@Column(name = "publisher_description")
	private String publisherDescription;
	@OneToMany(mappedBy = "publisher", fetch = FetchType.LAZY)
	private Set<Book> book = new HashSet<Book>();

	public Publisher(String publisher, String publisherDescription) {
		super();
		this.publisher = publisher;
		this.publisherDescription = publisherDescription;
	}

	public Publisher() {

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublisherDescription() {
		return publisherDescription;
	}

	public void setPublisherDescription(String publisherDescription) {
		this.publisherDescription = publisherDescription;
	}

	public Set<Book> getBook() {
		return book;
	}

	public void setBook(Set<Book> book) {
		this.book = book;
	}

}
