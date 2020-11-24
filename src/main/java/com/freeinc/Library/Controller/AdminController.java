package com.freeinc.Library.Controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.freeinc.Library.Business.IAuthorService;
import com.freeinc.Library.Business.IBookService;
import com.freeinc.Library.Business.IPublisherService;
import com.freeinc.Library.Business.IUserService;
import com.freeinc.Library.Entites.Author;
import com.freeinc.Library.Entites.Book;
import com.freeinc.Library.Entites.Publisher;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private final String UPLOAD_DIR = "./uploads/";

	@Autowired
	public IUserService userSerivce;
	@Autowired
	public IBookService bookService;
	@Autowired
	public IPublisherService publisherService;
	@Autowired
	public IAuthorService authorService;

	// Admin dashboard View
	@GetMapping("")
	public String showAdminPanel(Model model) {
		Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
		String username = loggedInUser.getName();
		model.addAttribute("username", username);
		List<Book> book = bookService.getBooks();
		model.addAttribute("book", book);

		return "admin/dashboard";
	}

	// Add book getter
	@GetMapping("/add")
	public String showBookAdd(Model model) {
		model.addAttribute("book", new Book());
		List<Author> author = authorService.getAllAuthor();
		model.addAttribute("author", author);
		List<Publisher> pub = publisherService.getAll();
		model.addAttribute("publisher", pub);
		return "admin/add";
	}

	// Add book process
	@PostMapping("/add_book")
	public String addBook(@ModelAttribute("book") Book book, Model bookModel,
			@RequestParam(name = "authorId") int authorId, @RequestParam(name = "publishId") int publishId,
			@RequestParam(name = "file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			Path path = Paths.get(UPLOAD_DIR + book.getBookTitle() + "/" + fileName);
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			Author get = authorService.findById(authorId);
			get.getBook().add(book);
			book.setAuthor(get);
			Publisher foundedPublisher = publisherService.findById(publishId);
			foundedPublisher.getBook().add(book);
			book.setPublisher(foundedPublisher);
			bookService.save(book);
			return "redirect:.";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "redirect:.";
		}
	}

	// Update book page view
	@GetMapping("/update/{id}")
	public String updateBook(@ModelAttribute("book") Book book, Model model, @PathVariable int id) {
		book = bookService.findById(id);
		List<Author> author = authorService.getAllAuthor();
		model.addAttribute("author", author);
		List<Publisher> pub = publisherService.getAll();
		model.addAttribute("publisher", pub);
		model.addAttribute("update", book);
		return "admin/update";
	}

	// Update book process
	@PostMapping("/updateBook")
	public String processUpdate(@ModelAttribute("update") Book book, Model bookModel,
			@RequestParam(name = "bookId") int bookId, @RequestParam(name = "authorId") int authorId,
			@RequestParam(name = "publishId") int publishId) {
		// POJO
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

	// Delete book process
	@PostMapping("/delete_book/{id}")
	public String deleteBook(@ModelAttribute("book") Book book, @PathVariable int id) {
		book = bookService.findById(id);
		bookService.removeBook(book);
		return "redirect:..";
	}

	// Publisher
	@GetMapping("/publisher")
	public String showPublisher(@ModelAttribute("publish") Publisher publisher, Model publisherModel) {
		publisherModel.addAttribute("publish", publisher);
		List<Publisher> pub = publisherService.getAll();
		publisherModel.addAttribute("pub", pub);
		return "admin/publisher";

	}

	// Add publisher
	@PostMapping("/add_publisher")
	public String publisherProcess(@ModelAttribute("publish") Publisher publisher) {
		publisherService.addPublisher(publisher);
		return "redirect:.";
	}

	@GetMapping("/update_publisher/{id}")
	public String showUpdatePublisher(@ModelAttribute("publish") Publisher publisher, Model viewUpdateModel,
			@PathVariable(name = "id") int id) {
		Publisher viewOfPublisher = publisherService.findById(id);
		viewUpdateModel.addAttribute("publish", viewOfPublisher);
		return "admin/update_publisher";
	}

	// Update publisher
	@PostMapping("/update_publisher/{id}")
	public String updatePublisher(@ModelAttribute("publish") Publisher publisher, @PathVariable(name = "id") int id) {
		Publisher update = publisherService.findById(id);
		update.setPublisher(publisher.getPublisher());
		update.setPublisherDescription(publisher.getPublisherDescription());
		publisherService.updatePublisher(update);
		return "redirect:..";
	}

	// Remove publisher
	@PostMapping("/delete_publisher/{id}")
	public String deletePublisher(@ModelAttribute("publish") Publisher publisher, @PathVariable int id) {
		publisher = publisherService.findById(id);
		publisherService.removePublisher(publisher);
		return "redirect:..";
	}

	// Author page view
	@GetMapping("/author")
	public String showAuthor(@ModelAttribute("author") Author author, Model authorModel) {
		authorModel.addAttribute("author", author);
		List<Author> authorList = authorService.getAllAuthor();
		authorModel.addAttribute("authorList", authorList);
		return "admin/author";
	}

	// Add Author
	@PostMapping("/add_author")
	public String authorProcess(@ModelAttribute("author") Author addAuthor) {
		authorService.saveAuthor(addAuthor);
		return "redirect:.";
	}

	// Remove Author
	@PostMapping("/delete_author/{id}")
	public String deleteAuthor(@ModelAttribute("author") Author author, @PathVariable int id) {
		author = authorService.findById(id);
		authorService.removeAuthor(author);
		return "redirect:..";
	}

	@GetMapping("/update_author/{id}")
	public String showUpdateAuthor(@ModelAttribute("author") Author author, Model viewUpdateModel,
			@PathVariable(name = "id") int id) {
		Author viewOfAuthor = authorService.findById(id);
		viewUpdateModel.addAttribute("author", viewOfAuthor);
		return "admin/update_author";
	}

	// Update author
	@PostMapping("/update_author/{id}")
	public String updateAuthor(@ModelAttribute("author") Author author, @PathVariable(name = "id") int id) {
		Author update = authorService.findById(id);
		update.setAuthorName(author.getAuthorName());
		update.setAuthorSummary(author.getAuthorSummary());
		authorService.updateAuthor(update);
		return "redirect:..";
	}

}
