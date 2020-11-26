package com.freeinc.Library.Entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id")
	private int id;
	@Column(name = "book_title")
	private String bookTitle;
	@Column(name = "sub_title")
	private String subTitle;
	@Column(name = "series_name")
	private String seriesName;
	@Column(name = "isbn")
	private String isbn;
	@Column(name = "book_description")
	private String bookDescription;
	@Column(name = "book_image_url")
	private String bookImageUrl;
	@ManyToOne(fetch = FetchType.LAZY)
	private Author author;
	@ManyToOne(fetch = FetchType.LAZY)
	private Publisher publisher;

	public Book(String bookTitle, String subTitle, String seriesName, String publisher, String isbn,
			String bookDescription, String bookImageUrl) {
		super();
		this.bookTitle = bookTitle;
		this.subTitle = subTitle;
		this.seriesName = seriesName;
		this.isbn = isbn;
		this.bookDescription = bookDescription;
		this.bookImageUrl = bookImageUrl;

	}

	public Book() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getBookDescription() {
		return bookDescription;
	}

	public void setBookDescription(String bookDescription) {
		this.bookDescription = bookDescription;
	}

	public String getBookImageUrl() {
		return bookImageUrl;
	}

	public void setBookImageUrl(String bookImageUrl) {
		this.bookImageUrl = bookImageUrl;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

}
