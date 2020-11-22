package com.freeinc.Library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.freeinc.Library.Business.IAuthorService;
import com.freeinc.Library.Business.IBookService;
import com.freeinc.Library.Business.IPublisherService;
import com.freeinc.Library.Entites.Author;
import com.freeinc.Library.Entites.Book;
import com.freeinc.Library.Entites.Publisher;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {

	@Autowired
	private IBookService bookService;
	@Autowired
	private IAuthorService authorService;
	@Autowired
	private IPublisherService publisherService;

	@GetMapping("")
	public String showDashboar(@ModelAttribute("book") Book book, Model searchModel) {
		List<Book> books = bookService.getBooks();
		searchModel.addAttribute("book", books);
		return "moderator/dashboard";
	}

	@GetMapping("/add")
	public String showAdd(@ModelAttribute("book") Book book, Model addModel) {
		addModel.addAttribute("book", new Book());
		List<Author> author = authorService.getAllAuthor();
		addModel.addAttribute("author", author);
		List<Publisher> pub = publisherService.getAll();
		addModel.addAttribute("publisher", pub);
		return "moderator/add";

	}
	@PostMapping("/add_book")
	public String addBook(@ModelAttribute("book") Book book, Model bookModel,
			@RequestParam(name = "authorId") int authorId, @RequestParam(name = "publishId") int publishId) {
		Author get = authorService.findById(authorId);
		get.getBook().add(book);
		book.setAuthor(get);
		Publisher foundedPublisher = publisherService.findById(publishId);
		foundedPublisher.getBook().add(book);
		book.setPublisher(foundedPublisher);
		bookService.save(book);
		return "redirect:.";
	}
	
	@GetMapping("/update/{id}")
	public String updateBook(@ModelAttribute("book") Book book, Model model, @PathVariable int id) {
		book = bookService.findById(id);
		List<Author> author = authorService.getAllAuthor();
		model.addAttribute("author", author);
		List<Publisher> pub = publisherService.getAll();
		model.addAttribute("publisher", pub);
		model.addAttribute("update", book);
		return "moderator/update";
	}

	@PostMapping("/updateBook")
	public String processUpdate(@ModelAttribute("update") Book book, Model bookModel,
			@RequestParam(name = "bookId") int bookId, @RequestParam(name = "authorId") int authorId,
			@RequestParam(name = "publishId") int publishId) {
		Book updatedBook = bookService.findById(bookId);
		System.out.println(bookId);
		Author get = authorService.findById(authorId);
		get.getBook().add(book);
		updatedBook.setAuthor(get);
		Publisher foundedPublisher = publisherService.findById(publishId);
		foundedPublisher.getBook().add(book);
		updatedBook.setPublisher(foundedPublisher);
		
		// Book credentials
		updatedBook.setBookDescription(book.getBookDescription());
		updatedBook.setBookTitle(book.getBookTitle());
		updatedBook.setSeriesName(book.getSeriesName());
		updatedBook.setIsbn(book.getIsbn());
		updatedBook.setSubTitle(book.getBookTitle());
		updatedBook.setBookTitle(book.getBookTitle());
		bookService.update(updatedBook);
		return "redirect:.";
	}

}
