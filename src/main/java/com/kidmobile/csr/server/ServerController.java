package com.kidmobile.csr.server;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kidmobile.csr.util.ChangeLocaleController;

/**
 * Handles requests for the application home page.
 */
@Controller
public class ServerController{
	@Autowired private ServletContext servletContext;
	
	@Inject
	private ChangeLocaleController changeLocaleController;	
	
	private static final Logger logger = LoggerFactory.getLogger(ServerController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/index_server.do", method = RequestMethod.GET)
	public String home(Locale locale, Model model, HttpServletRequest request ) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		request.getSession();
		//getServletContext().
		//getServletContext().getInitParameter("mail");
		//javax.servlet.http.HttpServlet application = request.getServletPath(); //getHttpServlet();
		//getServletContext();
		logger.info("... count = " + (Integer)servletContext.getAttribute("count"));
		HashMap hash_map = new HashMap();
		hash_map.put("001", "001001001");
		hash_map.put("002", "002002002");
		servletContext.setAttribute( "hash_map_s", hash_map);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		return "server/home";
	}

	@RequestMapping(value = "/project_create.do", method = RequestMethod.GET)
	public String project_create(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		changeLocaleController.change(request, response, "ko_KR");
		
		return "project/project_create";
	}
	@RequestMapping(value = "/sales_create.do", method = RequestMethod.GET)
	public String sales_create(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		changeLocaleController.change(request, response, "ko_KR");
		
		return "project/sales_create";
	}
	@RequestMapping(value = "/purchase_create.do", method = RequestMethod.GET)
	public String purchase_create(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		changeLocaleController.change(request, response, "ko_KR");
		
		return "project/purchase_create";
	}
	@RequestMapping(value = "/expense_create.do", method = RequestMethod.GET)
	public String expense_create(Locale locale, Model model, HttpServletRequest request, HttpServletResponse response) {
		changeLocaleController.change(request, response, "ko_KR");
		
		return "project/expense_create";
	}

	@RequestMapping(value = "/helpdesk.do", method = RequestMethod.GET)
	public String main_helpdesk(Locale locale, Model model) {
		return "helpdesk/main_helpdesk";
	}
	
	@RequestMapping(value = "/layout.do", method = RequestMethod.GET)
	public String  layout(Locale locale, Model model) {
		return "server/layout";
	}

}
