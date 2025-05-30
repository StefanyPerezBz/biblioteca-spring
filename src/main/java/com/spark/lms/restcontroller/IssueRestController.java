package com.spark.lms.restcontroller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Book;
import com.spark.lms.model.Issue;
import com.spark.lms.model.IssuedBook;
import com.spark.lms.model.Member;
import com.spark.lms.service.BookService;
import com.spark.lms.service.IssueService;
import com.spark.lms.service.IssuedBookService;
import com.spark.lms.service.MemberService;

@RestController
@RequestMapping(value = "/rest/issue")
public class IssueRestController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private BookService bookService;

	@Autowired
	private IssueService issueService;

	@Autowired
	private IssuedBookService issuedBookService;

	@RequestMapping(value="/save", method = RequestMethod.POST)
	public ResponseEntity<String> save(@RequestParam Map<String, String> payload) {
		try {
			String memberIdStr = payload.get("member");
			String[] bookIdsStr = payload.get("books").toString().split(",");

			Long memberId = null;
			List<Long> bookIds = new ArrayList<>();
			try {
				memberId = Long.parseLong(memberIdStr);
				for(String bookIdStr : bookIdsStr) {
					bookIds.add(Long.parseLong(bookIdStr));
				}
			} catch (NumberFormatException ex) {
				return ResponseEntity.badRequest().body("Formato de número no válido");
			}

			Member member = memberService.get(memberId);
			if(member == null) {
				return ResponseEntity.badRequest().body("Miembro no encontrado");
			}

			List<Book> books = bookService.get(bookIds);
			if(books.isEmpty()) {
				return ResponseEntity.badRequest().body("Libros no encontrados");
			}

			Issue issue = new Issue();
			issue.setMember(member);
			issue = issueService.addNew(issue);

			for(Book book : books) {
				book.setStatus(Constants.ESTADO_LIBRO_PRESTADO);
				bookService.save(book);

				IssuedBook ib = new IssuedBook();
				ib.setBook(book);
				ib.setIssue(issue);
				issuedBookService.addNew(ib);
			}

			return ResponseEntity.ok("exitoso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al procesar la solicitud: " + e.getMessage());
		}
	}

	@RequestMapping(value = "/{id}/return/all", method = RequestMethod.GET)
	public ResponseEntity<String> returnAll(@PathVariable(name = "id") Long id) {
		try {
			Issue issue = issueService.get(id);
			if(issue == null) {
				return ResponseEntity.badRequest().body("Préstamo no encontrado");
			}

			for(IssuedBook ib : issue.getIssuedBooks()) {
				ib.setReturned(Constants.LIBRO_DEVUELTO);
				issuedBookService.save(ib);

				Book book = ib.getBook();
				book.setStatus(Constants.ESTADO_LIBRO_DISPONIBLE);
				bookService.save(book);
			}

			issue.setReturned(Constants.LIBRO_DEVUELTO);
			issueService.save(issue);

			return ResponseEntity.ok("exitoso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al devolver los libros: " + e.getMessage());
		}
	}

	@RequestMapping(value="/{id}/return", method = RequestMethod.POST)
	public ResponseEntity<String> returnSelected(@RequestParam Map<String, String> payload,
												 @PathVariable(name = "id") Long id) {
		try {
			Issue issue = issueService.get(id);
			if(issue == null) {
				return ResponseEntity.badRequest().body("Préstamo no encontrado");
			}

			String[] issuedBookIds = payload.get("ids").split(",");
			List<String> idsList = Arrays.asList(issuedBookIds);

			for(IssuedBook ib : issue.getIssuedBooks()) {
				if(idsList.contains(ib.getId().toString())) {
					ib.setReturned(Constants.LIBRO_DEVUELTO);
					issuedBookService.save(ib);

					Book book = ib.getBook();
					book.setStatus(Constants.ESTADO_LIBRO_DISPONIBLE);
					bookService.save(book);
				}
			}

			return ResponseEntity.ok("exitoso");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error al devolver los libros seleccionados: " + e.getMessage());
		}

	}

	//
	@RequestMapping(value="/available-books", method = RequestMethod.GET)
	public ResponseEntity<List<Book>> getAvailableBooks() {
		List<Book> availableBooks = bookService.getAvailableBooks();
		return ResponseEntity.ok(availableBooks);
	}
}

