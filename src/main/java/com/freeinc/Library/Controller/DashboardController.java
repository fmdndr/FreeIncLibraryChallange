package com.freeinc.Library.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.freeinc.Library.Business.IBookService;
import com.freeinc.Library.Entites.Book;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private IBookService bookService;

	@GetMapping("")
	public String showDashboar(@ModelAttribute("book") Book book, Model searchModel) {
		List<Book> books = bookService.getBooks();
		searchModel.addAttribute("book", books);
		return "dashboard";
	}

}
