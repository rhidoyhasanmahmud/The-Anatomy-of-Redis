package com.learnwithhasan.springbootredis.modules.book.model;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Trim the entity data.
 *
 * @author Hasan mahmud
 * @since 2020-12-21
 */

@Converter
public class StringTrimConverter implements AttributeConverter<String, String> {

	/**
	 * Converts the value stored in the entity attribute into the data
	 * representation to be stored in the database.
	 **/

	@Override
	public String convertToDatabaseColumn(String value) {
		if (value != null) {
			if (value.trim().length() > 0) {
				return value.trim();
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Override
	public String convertToEntityAttribute(String value) {
		if (value == null) {
			return null;
		}
		return value.trim();
	}

}