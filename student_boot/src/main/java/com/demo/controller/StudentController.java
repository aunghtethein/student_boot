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
import com.demo.ds.Student;
import com.demo.service.ClassService;
import com.demo.service.StudentService;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private ClassService classService;
	
	
	@GetMapping("/student-reg")
	public String create(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		model.addAttribute("student",new Student());
		model.addAttribute("classes",classService.findAll());
		return "studentRegister";
	}
	@PostMapping("/students")
	public String process(@Valid Student student,BindingResult result,Model model) {
		if(result.hasErrors()) {
			model.addAttribute("classes",classService.findAll());
			return "studentRegister";
		}
		
		ClassBean classBean = classService.findById(student.getClassBean().getId());
		student.setClassBean(classBean);
		studentService.save(student);
		return "redirect:/student/all";
	}
	@GetMapping("/student/all")
	public String showAllStudnts(Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		model.addAttribute("students", studentService.findAll());
		return "studentList";
	}
	
	@GetMapping("/edit-student/{id}")
	public String updateStudent(@PathVariable Integer id,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		Student student = studentService.findStudentById(id);
		model.addAttribute("student",student);
		model.addAttribute("classes",classService.findAll());
		return "studentUpdate";
	}
	@PostMapping("/update-student/{studentId}")
	public String updateStudentProcess(@PathVariable Integer studentId,@Valid Student student,BindingResult result,Model model) {
		if(result.hasErrors()) {
			student.setStudentId(studentId);
			return "studentUpdate";
		}
		studentService.save(student);
		return "redirect:/student/all";
	}
	@GetMapping("/delete-student/{id}")
	public String deleteStudent(@PathVariable Integer id,Model model,HttpServletRequest request) {
		if(request.getSession().getAttribute("currentUser") == null) {
			request.getSession(false).invalidate();
			model.addAttribute("error", "Please Login First");
			return "redirect:/login";
		}
		Student student = studentService.findStudentById(id);
		studentService.delete(student);
		return "redirect:/student/all";
	}
}
