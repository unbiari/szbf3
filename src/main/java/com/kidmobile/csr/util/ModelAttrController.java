package com.kidmobile.csr.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.kidmobile.csr.test.Board;


@Controller
public class ModelAttrController {
	
	@ModelAttribute("interests")
	public Map<String, String> Interests() {
		Map<String, String> interests = new HashMap<String, String>();
		interests.put("A", "java");
		interests.put("B", "c++");
		
		return interests;
	}
	
	@ModelAttribute("users")
	public List<String> Users() {
		List<String> list = new ArrayList<String>();
		list.add(0, "all");
		list.add(1, "admin");
		list.add(2, "root");
		
		return list;
	}
	
	@RequestMapping(value = "/modelattr.do", method = RequestMethod.GET)
	public String modelAttr() {
		//model.addAttribute( "interests", interests() );
		
		return "modelattr";
	}

	@RequestMapping(value = "/modelattr2.do", method = RequestMethod.GET)
	public String modelAttr2get() {
		//model.addAttribute( "interests", interests() );
		
		return "modelattr2";
	}
	
	@RequestMapping(value = "/modelattr2.do", method = RequestMethod.POST)
	public String modelAttr2put(@ModelAttribute Board board ) {
		//model.addAttribute( "interests", interests() );
		System.out.println(board.getTitle());
		System.out.println(board.getContent());
		
		return "modelattr2";
	}
//	@RequestMapping(value = "/modelattr2.do", method = RequestMethod.POST)
//	public String modelAttr2param(@RequestParam("title") String title, @RequestParam("content") String content ) {
//		//model.addAttribute( "interests", interests() );
//		System.out.println(title);
//		System.out.println(content);
//		
//		return "modelattr2";
//	}

}
