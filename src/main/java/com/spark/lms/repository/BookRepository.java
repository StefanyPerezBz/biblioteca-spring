package com.spark.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Book;
import com.spark.lms.model.Category;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
	public Book findByTag(String tag);
	public List<Book> findByCategory(Category category);
	public List<Book> findByCategoryAndStatus(Category category, Integer status);
	public Long countByStatus(Integer status);

	//Buscar libro por isbn
	Optional<Book> findByIsbn(String isbn);

	@Query("SELECT b FROM Book b WHERE b.isbn = :isbn AND b.id != :id")
	Optional<Book> findByIsbnExcludingId(@Param("isbn") String isbn, @Param("id") Long id);

	//
	List<Book> findByStatus(Integer status);

}