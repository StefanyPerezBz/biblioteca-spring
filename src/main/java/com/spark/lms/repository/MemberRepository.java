package com.spark.lms.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spark.lms.model.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
	public List<Member> findAllByOrderByFirstNameAscMiddleNameAscLastNameAsc();
	public Long countByType(String type);

	//
	// Buscar por nombre y apellido
	Optional<Member> findByFirstNameAndLastName(String firstName, String lastName);

	@Query("SELECT m FROM Member m WHERE m.firstName = :firstName AND m.lastName = :lastName")
	Optional<Member> findByFullName(@Param("firstName") String firstName,
									@Param("lastName") String lastName);

	// Para validación al editar (excluyendo el miembro actual)
	@Query("SELECT m FROM Member m WHERE m.firstName = :firstName AND m.lastName = :lastName AND m.id != :id")
	Optional<Member> findByFullNameExcludingId(@Param("firstName") String firstName,
											   @Param("lastName") String lastName,
											   @Param("id") Long id);
}

