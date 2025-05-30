package com.spark.lms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spark.lms.model.Category;
import com.spark.lms.service.CategoryService;

import java.util.Arrays;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
	public String showCategoriesPage(Model model) {

		model.addAttribute("breadcrumb", Arrays.asList("Home"));
		model.addAttribute("currentPage", "Listar Categoría");

		model.addAttribute("categories", categoryService.getAll());
		return "/category/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addCategoryPage(Model model) {

		model.addAttribute("breadcrumb", Arrays.asList("Home"));
		model.addAttribute("currentPage", "Agregar Categoría");

		model.addAttribute("category", new Category());
		return "/category/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editCategoryPage(@PathVariable(name = "id") Long id, Model model) {

		model.addAttribute("breadcrumb", Arrays.asList("Home"));
		model.addAttribute("currentPage", "Editar Categoría");

		Category category = categoryService.get(id);
		if( category != null ) {
			model.addAttribute("category", category);
			return "/category/form";
		} else {
			return "redirect:/category/add";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveCategory(@Valid Category category, BindingResult bindingResult,
							   final RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("breadcrumb", Arrays.asList("Home"));
			model.addAttribute("currentPage", category.getId() == null ? "Agregar Categoría" : "Editar Categoría");
			return "/category/form";
		}

		try {
			if (category.getId() == null) {
				categoryService.addNew(category);
				redirectAttributes.addFlashAttribute("successMsg",
						"'" + category.getName() + "' se agregó como una nueva categoría.");
				return "redirect:/category/add";
			} else {
				categoryService.save(category);
				redirectAttributes.addFlashAttribute("successMsg",
						"Los cambios para '" + category.getName() + "' se guardaron exitosamente.");
				return "redirect:/category/edit/" + category.getId();
			}
		} catch (Exception e) {
			model.addAttribute("errorMsg", e.getMessage());

			// Agregar errores específicos al BindingResult
			if (e.getMessage().contains("nombre")) {
				bindingResult.rejectValue("name", "duplicate.name", e.getMessage());
			} else if (e.getMessage().contains("nombre corto")) {
				bindingResult.rejectValue("shortName", "duplicate.shortName", e.getMessage());
			}

			return "/category/form";
		}
	}

	//@RequestMapping(value = "/save", method = RequestMethod.POST)
	//public String saveCategory(@Valid Category category, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {

	//if( bindingResult.hasErrors() ) {
	//return "/category/form";
	//}

	//if( category.getId() == null ) {
	//categoryService.addNew(category);
	//redirectAttributes.addFlashAttribute("successMsg", "'" + category.getName() + "' se agrego como una nueva categoría.");
	//return "redirect:/category/add";
	//} else {
	//Category updateCategory = categoryService.save( category );
	//redirectAttributes.addFlashAttribute("successMsg", "Los cambios para '" + category.getName() + "' se guardaron exitosamente. ");
	//return "redirect:/category/edit/"+updateCategory.getId();
	//}
	//}

	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removeCategory(@PathVariable(name = "id") Long id, Model model) {
		Category category = categoryService.get( id );
		if( category != null ) {
			if( categoryService.hasUsage(category) ) {
				model.addAttribute("categoryInUse", true);
				return showCategoriesPage(model);
			} else {
				categoryService.delete(id);
			}
		}
		return "redirect:/category/list";
	}

}
