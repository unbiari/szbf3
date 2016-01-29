package com.kidmobile.csr.util;

import org.springframework.core.convert.converter.Converter;

import com.kidmobile.csr.test.Level;

public class LevelToStringConverter implements Converter<Level, String> {

	@Override
	public String convert(Level level) {
		return String.valueOf(level.intValue());
	}

}
