package com.spark.lms.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "category", uniqueConstraints = {
		@UniqueConstraint(columnNames = "name", name = "uk_category_name"),
		@UniqueConstraint(columnNames = "short_name", name = "uk_category_short_name")
})
public class Category implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotNull(message = "*Por favor ingrese el nombre de la categoría")
	@NotBlank(message = "*Por favor ingrese el nombre de la categoría")
	@Column(name = "name")
	private String name;

	@NotNull(message = "*Por favor ingrese el nombre corto de la categoría")
	@NotBlank(message = "*Por favor ingrese el nombre corto de la categoría")
	@Length(max = 4, message = "*No debe exceder los 4 caracteres.")
	@Column(name = "short_name")
	private String shortName;

	@Column(name = "notes")
	@Length(max = 1000, message = "*No debe exceder los 1000 caracteres.")
	private String notes;

	@Column(name = "create_date")
	private Date createDate;

	@JsonIgnore
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Book> books;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
