package com.learnwithhasan.springbootredis.modules.book.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.learnwithhasan.springbootredis.modules.book.model.Book;
import com.learnwithhasan.springbootredis.modules.book.service.BookService;

import lombok.RequiredArgsConstructor;

/*************************************************************************
 * {@link Book} Controller
 * 
 * @author Hasan Mahmud
 * @since 2020-12-28
 *************************************************************************/
@RestController
@RequestMapping("/api/book")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookController {

	private final BookService service;

	/*************************************************************************
	 * Create a new Book
	 * 
	 * @param ob {@link Book} object
	 * @param rs {@link HttpServletResponse} object
	 * @return {@link Book}
	 *************************************************************************/
	@PostMapping
	public Book create(@Valid @RequestBody Book ob, HttpServletRequest rq, HttpServletResponse rs) {
		ob.setCreatedBy(Long.valueOf(rq.getHeader("createdBy")));
		ob.setCreatedByEmp(rq.getHeader("createdByEmp") + " (ID:" + rq.getHeader("createdBy") + ")");

		rs.setStatus(HttpServletResponse.SC_CREATED);
		return service.create(ob, rs);
	}

	/*************************************************************************
	 * Update {@link Book}
	 * 
	 * @param ob {@link Book} object
	 * @return {@link Book}
	 *************************************************************************/
	@PutMapping
	public Book update(@Valid @RequestBody Book ob, HttpServletRequest rq, HttpServletResponse rs) {
		ob.setUpdatedBy(Long.valueOf(rq.getHeader("updatedBy")));
		ob.setUpdatedByEmp(rq.getHeader("updatedByEmp") + " (ID:" + rq.getHeader("updatedBy") + ")");
		return service.update(ob, rs);
	}

	/*************************************************************************
	 * Get active {@link Book}
	 * 
	 * @param id Id of a {@link Book}
	 * @return {@link Book}
	 *************************************************************************/
	@GetMapping("/get/{id}")
	public Book getById(@PathVariable Long id) {
		return service.getById(id);
	}

	/*************************************************************************
	 * Get active {@link Book}
	 * 
	 * @param id Id of a {@link Book}
	 * @return {@link Book}
	 *************************************************************************/
	@DeleteMapping("/get/{id}")
	public String deleteById(@PathVariable Long id) {
		return service.deleteById(id);
	}

}
