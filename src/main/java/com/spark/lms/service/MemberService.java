package com.spark.lms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Member;
import com.spark.lms.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private IssueService issueService;

	public Long getTotalCount() {
		return memberRepository.count();
	}

	public Long getParentsCount() {
		return memberRepository.countByType(Constants.MIEMBRO_PADRE);
	}

	public Long getStudentsCount() {
		return memberRepository.countByType(Constants.MIEMBRO_ESTUDIANTE);
	}

	public Long getTeachersCount() {
		return memberRepository.countByType(Constants.MIEMBRO_DOCENTE);
	}

	public List<Member> getAll() {
		return memberRepository.findAllByOrderByFirstNameAscMiddleNameAscLastNameAsc();
	}

	public Member get(Long id) {
		return memberRepository.findById(id).get();
	}

	public Member addNew(Member member) throws Exception{

		//
		// Validar que no exista otro miembro con mismo nombre y apellido
		if (memberRepository.findByFullName(member.getFirstName(), member.getLastName()).isPresent()) {
			throw new Exception("Ya existe un miembro con este nombre y apellido");
		}

		member.setJoiningDate( new Date() );
		return memberRepository.save( member );
	}

	public Member save(Member member) throws Exception{

		//
		// Validar que no exista otro miembro con mismo nombre y apellido (excluyendo el actual)
		if (memberRepository.findByFullNameExcludingId(
				member.getFirstName(),
				member.getLastName(),
				member.getId()).isPresent()) {
			throw new Exception("Ya existe otro miembro con este nombre y apellido");
		}

		return memberRepository.save( member );
	}

	public void delete(Member member) {
		memberRepository.delete(member);
	}

	public void delete(Long id) {
		memberRepository.deleteById(id);
	}

	public boolean hasUsage(Member member) {
		return issueService.getCountByMember(member) > 0;
	}



}

