package com.infy.student.controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.infy.student.Exceptions.StudentNotFoundException;
import com.infy.student.model.Posts;
import com.infy.student.model.Student;
import com.infy.student.repository.PostRepository;
import com.infy.student.repository.StudentRepository;

@RestController
public class StudentController {
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@GetMapping("/students")
	public List<Student> retrieveAllStudents() {
		return studentRepository.findAll();
	}
	@GetMapping("/students/{id}/posts")
	public List<Posts> retrieveAllStudentsPosts(@PathVariable Integer id) {
		Optional<Student> student = studentRepository.findById(id);
		if (!student.isPresent())
			throw new StudentNotFoundException("id-"+id);
		
		
		return student.get().getPosts();
	}
	@GetMapping("/students/byid/{id}")
	public Resource<Student> retrieveStudent(@PathVariable Integer id) {
		Optional<Student> student = studentRepository.findById(id);

		if (!student.isPresent())
			throw new StudentNotFoundException("id-"+id);
		
		
		  Resource<Student> resource=new Resource<Student>(student.get());
		  ControllerLinkBuilder link=linkTo(methodOn(this.getClass()).retrieveAllStudents()); 
		  resource.add(link.withRel("all-users"));
		 
		return resource;
	}
	@DeleteMapping("/students/{id}")
	public void deleteStudent(@PathVariable Integer id) {
		Optional<Student> student = studentRepository.findById(id);

		if (!student.isPresent()) {
			throw new StudentNotFoundException("id-"+id);
		}

		studentRepository.delete(student.get());
	}
	
	@PostMapping("/students")
	public ResponseEntity<Object> createStudent(@Valid @RequestBody Student student) {
		Student savedStudent = studentRepository.save(student);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(savedStudent.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	@PostMapping("/students/{id}/posts")
	public ResponseEntity<Object> createPosts(@PathVariable Integer id,@RequestBody Posts post) {
		Optional<Student> optional = studentRepository.findById(id);

		if (!optional.isPresent()) {
			throw new StudentNotFoundException("id-"+id);
		}
		Student student=optional.get();
		
		post.setStudent(student);
		postRepository.save(post);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(post.getId()).toUri();

		return ResponseEntity.created(location).build();

	}
	@PutMapping("/students/{id}")
	public ResponseEntity<Object> updateStudent(@RequestBody Student student, @PathVariable Integer id) {
		Optional<Student> studentOptional = studentRepository.findById(id);
		if (!studentOptional.isPresent())
			return ResponseEntity.notFound().build();
		student.setId(id);	
		studentRepository.save(student);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/students/byname/{name}")
	public List<Student> retrieveStudentByName(@PathVariable String name) {
		List<Student> student = studentRepository.findByName(name);
		return student;
	}
	@GetMapping("/students/by-id-name/{id}/{name}")
	public List<Student> retrieveStudentByNameAndId(@PathVariable Integer id, @PathVariable String name) {
		List<Student> student = studentRepository.findByIdAndName(id, name);;
		return student;
	}
	
	/*
	 * @GetMapping("/students/fileterd/{id}") public MappingJacksonValue
	 * retrieveStudentFiletered(@PathVariable Integer id) { Optional<Student>
	 * student = studentRepository.findById(id);
	 * 
	 * if (!student.isPresent()) throw new StudentNotFoundException("id-"+id);
	 * MappingJacksonValue mapping=new MappingJacksonValue(student.get());
	 * SimpleBeanPropertyFilter
	 * filter=SimpleBeanPropertyFilter.filterOutAllExcept("id","name");
	 * FilterProvider filters=new SimpleFilterProvider().addFilter("passportFilter",
	 * filter);
	 * 
	 * mapping.setFilters(filters);
	 * 
	 * return mapping; }
	 */
}
