package com.infy.student.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name="student")
@ApiModel(description="All details abou student entity")
//@JsonFilter("passportFilter")
public class Student {
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@Size(min=2)
	@ApiModelProperty(notes="name should be more than two charcaters")
	private String name;
	
	@Column(name="passportnumber")
	private String passportNumber;
	
	
	@OneToMany(mappedBy="student")
	private List<Posts> posts;
	
	public Student(@Size(min = 2) String name, String passportNumber, List<Posts> posts) {
		this.name = name;
		this.passportNumber = passportNumber;
		this.posts = posts;
	}
	public List<Posts> getPosts() {
		return posts;
	}
	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}

	
	public Student(String name, String passportNumber) {

		this.name = name;
		this.passportNumber = passportNumber;
	}
	public Student() {
	
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	
	

}
