package com.spark.lms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "book", uniqueConstraints = {
		@UniqueConstraint(columnNames = "tag", name = "uk_book_tag"),
		@UniqueConstraint(columnNames = "isbn", name = "uk_book_isbn")
})
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull(message = "*Por favor ingrese el título del libro")
	@NotBlank(message = "*Por favor ingrese el título del libro")
	@Column(name = "title")
	private String title;

	@NotNull(message = "*Por favor ingresa la etiqueta del libro")
	@NotBlank(message = "*Por favor ingresa la etiqueta del libro")
	@Column(name = "tag")
	private String tag;

	@NotNull(message = "*Por favor ingrese los autores del libro")
	@NotBlank(message = "*Por favor ingrese los autores del libro")
	@Column(name = "authors")
	private String authors;

	@Column(name = "publisher")
	private String publisher;

	@NotNull(message = "*Por favor ingrese el isbn")
	@NotBlank(message = "*Por favor ingrese el isbn")
	@Column(name = "isbn")
	private String isbn;

	@Column(name = "status")
	private Integer status;

	@Column(name = "create_date")
	private Date createDate;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "category_id")
	@NotNull(message = "*Por favor seleccione categoría")
	private Category category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getAuthors() {
		return authors;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


}

