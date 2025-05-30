package com.spark.lms.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.lms.common.Constants;

@Service
public class HomeService {

	@Autowired
	private MemberService memberService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private BookService bookService;

	public Map<String, Long> getTopTilesMap() {
		Map<String, Long> map = new HashMap<String, Long>();
		map.put("totalMiembros", memberService.getTotalCount());
		map.put("totalEstudiantes", memberService.getStudentsCount());
		map.put("totalProfesores", memberService.getTeachersCount());
		map.put("totalPadres", memberService.getParentsCount());
		map.put("totalCategorias", categoryService.getTotalCount());
		map.put("totalLibros", bookService.getTotalCount());
		map.put("totalLibrosPrestados", bookService.getTotalIssuedBooks());
		return map;
	}

}

