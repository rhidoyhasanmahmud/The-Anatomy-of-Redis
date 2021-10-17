package com.learnwithhasan.springbootredis.modules.book.service;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learnwithhasan.springbootredis.modules.book.model.Book;
import com.learnwithhasan.springbootredis.modules.book.repository.BookRepo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*************************************************************************
 * {@link Book} implementation class
 * 
 * @author Hasan Mahmud
 *
 * @since 2020-10-05
 *************************************************************************/
@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BookServiceImpl implements BookService {

	private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);
	private final BookRepo repo;

	/*************************************************************************
	 * Create a new Book
	 * 
	 * @param ob {@link Book} object
	 * @return {@link Book}
	 *************************************************************************/
	@Override
	public Book create(Book ob, HttpServletResponse rs) {
		try {
			logger.info("Add a Book.");
			return repo.save(ob);
		} catch (Exception e) {
			log.warn("Failed to create  Book: ", e);
			rs.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return ob;
		}
	}

	/*************************************************************************
	 * Update {@link Book}
	 * 
	 * @param ob {@link Book} object
	 * @return {@link Book}
	 *************************************************************************/
	@Override
	@CachePut(cacheNames = "books", key = "#book.id")
	public Book update(Book ob, HttpServletResponse rs) {
		try {
			logger.info("Update a Book.");
			return repo.save(ob);
		} catch (Exception e) {
			log.warn("Failed to update  Book: ", e);
			rs.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return ob;
		}
	}

	/*************************************************************************
	 * Get {@link Book} by id and isActive
	 * 
	 * @param id Id of a {@link Book}
	 * @return {@link List< Book>}
	 *************************************************************************/
	@Override
	@Cacheable(cacheNames = "books", key = "#id")
	public Book getById(Long id) {
		logger.info("Fetching a Book from DB.");
		Book ob = repo.findById(id).get();
		return ob;
	}

	/*************************************************************************
	 * Get {@link Book} by id and isActive
	 * 
	 * @param id Id of a {@link Book}
	 * @return {@link List< Book>}
	 *************************************************************************/
	@Override
	@CacheEvict(cacheNames = "books", key = "#id")
	public String deleteById(Long id) {
		repo.deleteById(id);
		return "Book Deleted";
	}
}
