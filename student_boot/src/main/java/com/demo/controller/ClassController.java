package com.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.demo.ds.ClassBean;
import com.demo.service.ClassService;

@Controller
public class ClassController {
	@Autowired
	private ClassService classService;

	@GetMapping("/class-reg")
	public String addStudent(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
 	
		model.addAttribute("classBean", new ClassBean());
		return "classRegister";
	}
	
	

	@PostMapping("/class-process")
	public String save(@Valid ClassBean classBean, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return "classRegister";
		}

		classService.save(classBean);
		return "redirect:/class-list";
	}

	@GetMapping("/class-list")
	public String showClassList(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		model.addAttribute("classes", classService.findAll());
		return "classList";
	}

	@GetMapping("/delete-class/{id}")
	public String deleteClass(@PathVariable Integer id, Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		ClassBean classBean = classService.findById(id);
		classService.delete(classBean);
		return "redirect:/class-list";
	}

}
