package com.demo.ds;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Entity
public class ClassBean {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotEmpty(message = "Class Name Must not be empty")
	private String className;
	private double fees;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public double getFees() {
		return fees;
	}
	public void setFees(double fees) {
		this.fees = fees;
	}
	
	

	
	
}
