package com.kidmobile.csr.util;

import org.springframework.core.convert.converter.Converter;

import com.kidmobile.csr.test.Level;

public class StringToLevelConverter implements Converter<String,Level> {

	@Override
	public Level convert(String text) {
		return Level.valueOf(Integer.parseInt(text));
	}

}
