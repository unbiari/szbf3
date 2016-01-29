package com.kidmobile.csr.util;

import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.LocaleEditor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.LocaleResolver;

/**
 * Change Locale
 */
@Controller("ChangeLocaleController")
public class ChangeLocaleController {

	@Inject
	private LocaleResolver localeResolver;
	
	@Autowired
	private MessageSource messageSource;
	
	@RequestMapping("/loginChangeLocale.do")
	public String login(
			@RequestParam(value = "locale", defaultValue = "en_US") String newLocale,
			Model model, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LocaleEditor localeEditor = new LocaleEditor();
		localeEditor.setAsText(newLocale);
		localeResolver.setLocale(request, response,
				(Locale) localeEditor.getValue());

		// return "forward:history.back();";
		return "forward:/loginMovieFinder.do?method=list";
	}
	
	public void change( HttpServletRequest request, HttpServletResponse response, String newLocale ) {
		LocaleEditor localeEditor = new LocaleEditor();
		localeEditor.setAsText(newLocale);
		localeResolver.setLocale(request, response, (Locale) localeEditor.getValue());
	}
	
	public String getLocaleMessage(String code_key) {
		String msg = messageSource.getMessage(code_key, null, LocaleContextHolder.getLocale());

		return msg;		
	}
	
}
