package com.demo.ds;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer studentId;
	@NotEmpty(message = "Student Name must not be empty.")
	private String studentName;
	@NotNull(message= "Register Date must not be empty.")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate registerDate;
	@NotEmpty(message = "Address must not be empty.")
	private String address;
	
	@ManyToOne
	private ClassBean classBean;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public LocalDate getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		this.registerDate = registerDate;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public ClassBean getClassBean() {
		return classBean;
	}

	public void setClassBean(ClassBean classBean) {
		this.classBean = classBean;
	}
	
	
	
	
	
}
