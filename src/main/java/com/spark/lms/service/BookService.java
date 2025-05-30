package com.spark.lms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Book;
import com.spark.lms.model.Category;
import com.spark.lms.repository.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private IssuedBookService issuedBookService;

	public Long getTotalCount() {
		return bookRepository.count();
	}

	public Long getTotalIssuedBooks() {
		return bookRepository.countByStatus(Constants.ESTADO_LIBRO_PRESTADO);
	}

	public List<Book> getAll() {
		return bookRepository.findAll();
	}

	public Book get(Long id) {
		return bookRepository.findById(id).get();
	}

	public Book getByTag(String tag) {
		return bookRepository.findByTag(tag);
	}

	public List<Book> get(List<Long> ids) {
		return bookRepository.findAllById(ids);
	}

	public List<Book> getByCategory(Category category) {
		return bookRepository.findByCategory(category);
	}

	public List<Book> geAvailabletByCategory(Category category) {
		return bookRepository.findByCategoryAndStatus(category, Constants.ESTADO_LIBRO_DISPONIBLE);
	}

	public Book addNew(Book book) throws Exception{
		//
		// Validar que no exista otro libro con el mismo ISBN
		if (book.getIsbn() != null && !book.getIsbn().isEmpty() &&
				bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
			throw new Exception("Ya existe un libro con este ISBN");
		}

		book.setCreateDate(new Date());
		book.setStatus( Constants.ESTADO_LIBRO_DISPONIBLE );
		return bookRepository.save(book);
	}

	public Book save(Book book) throws Exception{
		//
		// Validar que no exista otro libro con el mismo ISBN (excluyendo el actual)
		if (book.getIsbn() != null && !book.getIsbn().isEmpty() &&
				bookRepository.findByIsbnExcludingId(book.getIsbn(), book.getId()).isPresent()) {
			throw new Exception("Ya existe otro libro con este ISBN");
		}

		return bookRepository.save(book);
	}

	public void delete(Book book) {
		bookRepository.delete(book);
	}

	public void delete(Long id) {
		bookRepository.deleteById(id);
	}

	public boolean hasUsage(Book book) {
		return issuedBookService.getCountByBook(book)>0;
	}

	//
	public List<Book> getAvailableBooks() {
		return bookRepository.findByStatus(Constants.ESTADO_LIBRO_DISPONIBLE);
	}
}

