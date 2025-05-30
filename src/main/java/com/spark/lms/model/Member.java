package com.spark.lms.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "member", uniqueConstraints = @UniqueConstraint(
		columnNames = {"first_name", "last_name"},
		name = "uk_member_name"))
public class Member implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	@NotEmpty(message = "*Por favor seleccione el tipo de miembro")
	@NotNull(message = "*Por favor seleccione el tipo de miembro")
	@Column(name = "type")
	private String type;

	@NotEmpty(message = "*Por favor ingrese su nombre")
	@NotNull(message = "*Por favor ingrese su nombre")
	@Column(name = "first_name")
	private String firstName;


	@Column(name = "middle_name")
	private String middleName;

	@NotEmpty(message = "*Por favor ingresa el apellido")
	@NotNull(message = "*Por favor ingresa el apellido")
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty(message = "*Por favor seleccione género")
	@NotNull(message = "*Por favor seleccione género")
	@Column(name = "gender")
	private String gender;

	@Column(name = "date_of_birth")
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	private Date dateOfBirth;

	@Column(name = "joining_date")
	private Date joiningDate;

	@Column(name = "contact")
	private String contact;

	@Column(name = "email")
	private String email;


	public Member(@NotNull String type, @NotNull String firstName, @NotNull String middleName, @NotNull String lastName,
				  @NotNull String gender, @NotNull Date dateOfBirth, @NotNull Date joiningDate) {
		super();
		this.type = type;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.joiningDate = joiningDate;
	}

	public Member() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getJoiningDate() {
		return joiningDate;
	}

	public void setJoiningDate(Date joiningDate) {
		this.joiningDate = joiningDate;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

