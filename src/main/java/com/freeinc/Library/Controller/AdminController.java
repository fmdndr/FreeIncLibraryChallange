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
import org.springframework.web.servlet.ModelAndView;

import com.freeinc.Library.Business.IAuthorService;
import com.freeinc.Library.Business.IBookService;
import com.freeinc.Library.Business.IPublisherService;
import com.freeinc.Library.Entites.Author;
import com.freeinc.Library.Entites.Book;
import com.freeinc.Library.Entites.Publisher;

@Controller
@RequestMapping("/admin")
public class AdminController {

	private String UPLOAD_DIR = "./src/main/resources/static/book_images/";

	private IBookService bookService;

	private IPublisherService publisherService;

	private IAuthorService authorService;

	// Constructor Injection
	@Autowired
	public AdminController(IBookService bookService, IPublisherService publisherService, IAuthorService authorService) {
		this.bookService = bookService;
		this.publisherService = publisherService;
		this.authorService = authorService;
	}

	// Admin dashboard View
	@GetMapping("")
	public ModelAndView showAdminPanel(Model model) {
		try {
			Authentication loggedInUser = SecurityContextHolder.getContext().getAuthentication();
			String username = loggedInUser.getName();
			model.addAttribute("username", username);
			List<Book> book = bookService.getBooks();
			model.addAttribute("book", book);
			return new ModelAndView("admin/dashboard");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/dashboard");
		}
	}

	// Add book getter
	@GetMapping("/add")
	public ModelAndView showBook(Model showBookModel) {
		try {
			showBookModel.addAttribute("book", new Book());
			List<Author> author = authorService.getAllAuthor();
			showBookModel.addAttribute("author", author);
			List<Publisher> pub = publisherService.getAll();
			showBookModel.addAttribute("publisher", pub);
			return new ModelAndView("admin/add");
		} catch (Exception e) {
			showBookModel.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/add");
		}
	}

	// Add book process
	@PostMapping("/add_book")
	public ModelAndView addBook(@ModelAttribute("book") Book book, Model bookModel,
			@RequestParam(name = "authorId") int authorId, @RequestParam(name = "publishId") int publishId,
			@RequestParam(name = "file") MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		try {
			String fileUploadDir = UPLOAD_DIR + book.getBookTitle();
			Path uploadPath = Paths.get(fileUploadDir);

			if (!Files.exists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
			Path filePath = uploadPath.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			System.out.println(filePath);
			book.setBookImageUrl(fileName);
			Author get = authorService.findById(authorId);
			get.getBook().add(book);
			book.setAuthor(get);
			Publisher foundedPublisher = publisherService.findById(publishId);
			foundedPublisher.getBook().add(book);
			book.setPublisher(foundedPublisher);
			bookService.save(book);
			return new ModelAndView("redirect:.");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			bookModel.addAttribute("message", e.getMessage());
			return new ModelAndView("redirect:.");
		}
	}

	// Update book page view
	@GetMapping("/update/{id}")
	public ModelAndView updateBook(@ModelAttribute("book") Book book, Model model, @PathVariable int id) {
		try {
			book = bookService.findById(id);
			List<Author> author = authorService.getAllAuthor();
			model.addAttribute("author", author);
			List<Publisher> pub = publisherService.getAll();
			model.addAttribute("publisher", pub);
			model.addAttribute("update", book);
			return new ModelAndView("admin/update");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/update");
		}
	}

	// Update book process
	@PostMapping("/updateBook")
	public ModelAndView processUpdate(@ModelAttribute("update") Book book, Model bookModel,
			@RequestParam(name = "bookId") int bookId, @RequestParam(name = "authorId") int authorId,
			@RequestParam(name = "publishId") int publishId) {
		try {
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
			return new ModelAndView("redirect:.");
		} catch (Exception e) {
			bookModel.addAttribute("message", e.getMessage());
			return new ModelAndView("redirect:.");
		}
	}

	// Delete book process
	@PostMapping("/delete_book/{id}")
	public ModelAndView deleteBook(@ModelAttribute("book") Book book, @PathVariable int id, Model updateModel) {
		try {
			book = bookService.findById(id);
			bookService.removeBook(book);
			return new ModelAndView("redirect:..");
		} catch (Exception e) {
			updateModel.addAttribute("message", e.getMessage());
			return new ModelAndView("redirect:..");
		}
	}

	// Publisher
	@GetMapping("/publisher")
	public ModelAndView showPublisher(@ModelAttribute("publish") Publisher publisher, Model publisherModel) {
		try {
			publisherModel.addAttribute("publish", publisher);
			List<Publisher> pub = publisherService.getAll();
			publisherModel.addAttribute("pub", pub);
			return new ModelAndView("admin/publisher");
		} catch (Exception e) {
			publisherModel.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/publisher");
		}

	}

	// Add publisher
	@PostMapping("/add_publisher")
	public ModelAndView publisherProcess(@ModelAttribute("publish") Publisher publisher) {
		try {
			publisherService.addPublisher(publisher);
			return new ModelAndView("redirect:.");
		} catch (Exception e) {
			ModelAndView m = new ModelAndView();
			m.addObject("message", e.getMessage());
			return new ModelAndView("redirect:.");
		}

	}

	@GetMapping("/update_publisher/{id}")
	public ModelAndView showUpdatePub(@ModelAttribute("publish") Publisher publisher, Model updateModel,
			@PathVariable(name = "id") int id) {
		try {
			Publisher viewOfPublisher = publisherService.findById(id);
			updateModel.addAttribute("publish", viewOfPublisher);
			return new ModelAndView("admin/update_publisher");
		} catch (Exception e) {
			updateModel.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/update_publisher");
		}
	}

	// Update publisher
	@PostMapping("/update_publisher/{id}")
	public ModelAndView updatePublisher(@ModelAttribute("publish") Publisher publisher, Model model,
			@PathVariable(name = "id") int id) {
		try {
			Publisher update = publisherService.findById(id);
			update.setPublisher(publisher.getPublisher());
			update.setPublisherDescription(publisher.getPublisherDescription());
			publisherService.updatePublisher(update);
			return new ModelAndView("redirect:..");
		} catch (Exception e) {
			model.addAttribute("message", e.getMessage());
			return new ModelAndView("redirect:..");
		}
	}

	// Remove publisher
	@PostMapping("/delete_publisher/{id}")
	public ModelAndView deletePublisher(@ModelAttribute("publish") Publisher publisher, @PathVariable int id) {
		try {

			publisher = publisherService.findById(id);
			publisherService.removePublisher(publisher);
			return new ModelAndView("redirect:..");
		} catch (Exception e) {
			ModelAndView m = new ModelAndView();
			m.addObject("message", e.getMessage());
			return new ModelAndView("redirect:..");
		}
	}

	// Author page view
	@GetMapping("/author")
	public ModelAndView showAuthor(@ModelAttribute("author") Author author, Model authorModel) {
		try {
			authorModel.addAttribute("author", author);
			List<Author> authorList = authorService.getAllAuthor();
			authorModel.addAttribute("authorList", authorList);
			return new ModelAndView("admin/author");
		} catch (Exception e) {
			authorModel.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/author");
		}
	}

	// Add Author
	@PostMapping("/add_author")
	public ModelAndView authorProcess(@ModelAttribute("author") Author addAuthor) {
		authorService.saveAuthor(addAuthor);
		return new ModelAndView("redirect:.");
	}

	// Remove Author
	@PostMapping("/delete_author/{id}")
	public ModelAndView deleteAuthor(@ModelAttribute("author") Author author, @PathVariable int id) {
		try {
			author = authorService.findById(id);
			authorService.removeAuthor(author);
			return new ModelAndView("redirect:..");
		} catch (Exception e) {
			ModelAndView m = new ModelAndView();
			m.addObject("message", e.getMessage());
			return new ModelAndView("redirect:..");
		}
	}

	@GetMapping("/update_author/{id}")
	public ModelAndView showUpdateAuthor(@ModelAttribute("author") Author author, Model viewUpdateModel,
			@PathVariable(name = "id") int id) {
		try {
			Author viewOfAuthor = authorService.findById(id);
			viewUpdateModel.addAttribute("author", viewOfAuthor);
			return new ModelAndView("admin/update_author");
		} catch (Exception e) {
			viewUpdateModel.addAttribute("message", e.getMessage());
			return new ModelAndView("admin/update_author");
		}
	}

	// Update author
	@PostMapping("/update_author/{id}")
	public ModelAndView updateAuthor(@ModelAttribute("author") Author author, @PathVariable(name = "id") int id) {
		try {
			Author update = authorService.findById(id);
			update.setAuthorName(author.getAuthorName());
			update.setAuthorSummary(author.getAuthorSummary());
			authorService.updateAuthor(update);
			return new ModelAndView("redirect:..");
		} catch (Exception e) {
			ModelAndView m = new ModelAndView();
			m.addObject("message", e.getMessage());
			return new ModelAndView("redirect:..");
		}
	}

}
