package com.spark.lms.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spark.lms.common.Constants;
import com.spark.lms.model.Member;
import com.spark.lms.service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

	@Autowired
	private MemberService memberService;


	@ModelAttribute(name = "memberTypes")
	public List<String> memberTypes() {
		return Constants.TIPOS_MIEMBROS;
	}

	@RequestMapping(value = {"/", "/list"},  method = RequestMethod.GET)
	public String showMembersPage(Model model) {

		model.addAttribute("breadcrumb", Arrays.asList("Home"));
		model.addAttribute("currentPage", "Listar Miembro");

		model.addAttribute("members", memberService.getAll());
		return "/member/list";
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String addMemeberPage(Model model) {

		model.addAttribute("breadcrumb", Arrays.asList("Home"));
		model.addAttribute("currentPage", "Agregar Miembro");

		model.addAttribute("member", new Member());
		return "/member/form";
	}

	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editMemeberPage(@PathVariable(name = "id") Long id, Model model) {

		model.addAttribute("breadcrumb", Arrays.asList("Home"));
		model.addAttribute("currentPage", "Editar Miembro");

		Member member = memberService.get( id );
		if( member != null ) {
			model.addAttribute("member", member);
			return "/member/form";
		} else {
			return "redirect:/member/add";
		}
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveMember(@Valid Member member, BindingResult bindingResult,
							 final RedirectAttributes redirectAttributes, Model model) {

		// Validaciones básicas del formulario
		if(bindingResult.hasErrors()) {
			model.addAttribute("breadcrumb", Arrays.asList("Home"));
			model.addAttribute("currentPage", member.getId() == null ? "Agregar Miembro" : "Editar Miembro");
			return "/member/form";
		}

		try {
			if(member.getId() == null) {
				memberService.addNew(member);
				redirectAttributes.addFlashAttribute("successMsg", "'" + member.getFirstName() + " " +
						(member.getMiddleName() != null ? member.getMiddleName() : "") +
						"' se agregó como nuevo miembro.");
				return "redirect:/member/add";
			} else {
				Member updatedMember = memberService.save(member);
				redirectAttributes.addFlashAttribute("successMsg", "Los cambios para '" +
						member.getFirstName() + " " +
						(member.getMiddleName() != null ? member.getMiddleName() : "") +
						"' se guardaron exitosamente.");
				return "redirect:/member/edit/" + updatedMember.getId();
			}
		} catch (Exception e) {
			// Manejo de errores de duplicado
			model.addAttribute("breadcrumb", Arrays.asList("Home"));
			model.addAttribute("currentPage", member.getId() == null ? "Agregar Miembro" : "Editar Miembro");
			model.addAttribute("errorMsg", e.getMessage());

			return "/member/form";
		}
	}


	@RequestMapping(value = "/remove/{id}", method = RequestMethod.GET)
	public String removeMember(@PathVariable(name = "id") Long id, Model model) {
		Member member = memberService.get( id );
		if( member != null ) {
			if( memberService.hasUsage(member) ) {
				model.addAttribute("memberInUse", true);
				return showMembersPage(model);
			} else {
				memberService.delete(id);
			}
		}
		return "redirect:/member/list";
	}

	private String formatName(Member member) {
		return "'" + member.getFirstName() +
				(member.getMiddleName() != null ? " " + member.getMiddleName() : "") +
				" " + member.getLastName() + "'";
	}


}

