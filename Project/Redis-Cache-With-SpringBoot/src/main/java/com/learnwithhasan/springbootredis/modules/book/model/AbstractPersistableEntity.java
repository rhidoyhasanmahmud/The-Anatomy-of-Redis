package com.learnwithhasan.springbootredis.modules.book.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Version;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
/**
 * The Abstract Persistable entity for all other db schema entity
 *
 * @author Hasan Mahmud
 * @since 2020-12-28
 */
@Data
@MappedSuperclass
public abstract class AbstractPersistableEntity implements Serializable {

	private static final long serialVersionUID = 4240005902936474749L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(updatable = false, nullable = false)
	private Long id;

	@Version
	private Long version;

	@Column(updatable = false, nullable = false)
	private LocalDateTime createdAt;


	@Column(updatable = false, nullable = false)
	private Long createdBy;

	@Setter(value = AccessLevel.PRIVATE)
	private LocalDateTime updatedAt;

	private Long updatedBy;
	
	@Column(nullable = false)
	private boolean isActive = true;
	
	private String createdByEmp;
	
	private String updatedByEmp;
	
	@PrePersist
	void onInsert() {
		createdAt = LocalDateTime.now();
	}
	
	@PreUpdate
	void onUpdate() {
		updatedAt = LocalDateTime.now();
	}
}
