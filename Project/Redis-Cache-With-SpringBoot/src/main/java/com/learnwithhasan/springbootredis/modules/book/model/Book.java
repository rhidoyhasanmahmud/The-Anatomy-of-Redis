package com.learnwithhasan.springbootredis.modules.book.model;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The persistent class for the rdms_book database table.
 *
 * @author Hasan Mahmud
 * @since 2020-12-28
 */

@Getter
@Setter
@Entity
@Table(name = "rdms_book")
@ToString(callSuper = true)
public class Book extends AbstractPersistableEntity {

	private static final long serialVersionUID = 1L;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(nullable = false)
	@NotNull
	private String bookName;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(nullable = false)
	@NotNull
	private String authorName;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(nullable = false)
	@NotNull
	private String edition;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(nullable = false)
	@NotNull
	private String bookCategory;
	
	@Convert(converter = StringTrimConverter.class)
	@Column(nullable = false)
	@NotNull
	private String bookPublisher;

}
