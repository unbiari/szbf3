package com.kidmobile.csr.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kidmobile.csr.test.Level;

//@Controller
//public class TestConverterController {
//	
//    @Autowired
//    private ConversionService conversionService;
// 
//    @InitBinder
//    public void initBinder(WebDataBinder dataBinder) {
//        dataBinder.setConversionService(conversionService);
//    }
//
//	@RequestMapping(value = "/string2level.do", method = RequestMethod.GET)
//	public String modelAttr2get(@RequestParam Level level) {
//		//model.addAttribute( "interests", interests() );
//		System.out.println("level = " + level.toString());
//		return "modelattr2";
//	}
//
//}
