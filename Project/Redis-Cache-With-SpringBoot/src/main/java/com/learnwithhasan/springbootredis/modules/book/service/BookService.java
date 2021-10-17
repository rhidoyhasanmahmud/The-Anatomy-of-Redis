package com.learnwithhasan.springbootredis.modules.book.service;

import javax.servlet.http.HttpServletResponse;

import com.learnwithhasan.springbootredis.modules.book.model.Book;

/*************************************************************************
 * {@link Book} service class
 * 
 * @author Hasan Mahmud
 * @since 2020-12-28
 *************************************************************************/
public interface BookService {

	/*************************************************************************
	 * Create a new Book
	 * 
	 * @param ob {@link Book} object
	 * @param rs
	 * @return {@link Book}
	 *************************************************************************/
	Book create(Book ob, HttpServletResponse rs);

	/*************************************************************************
	 * Update {@link Book}
	 * 
	 * @param ob {@link Book} object
	 * @param rs
	 * @return {@link Book}
	 *************************************************************************/
	Book update(Book ob, HttpServletResponse rs);

	/*************************************************************************
	 * Get {@link Book} by id and isActive
	 * 
	 * @param id Id of a {@link Book}
	 * @return {@link Book}
	 *************************************************************************/
	Book getById(Long id);

	/*************************************************************************
	 * Get {@link Book} by id and isActive
	 * 
	 * @param id Id of a {@link Book}
	 * @return {@link Book}
	 *************************************************************************/
	String deleteById(Long id);
}
