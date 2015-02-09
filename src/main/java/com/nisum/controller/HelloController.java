package com.nisum.controller;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HelloController {

//    @Autowired
//    private SessionFactory sessionFactory;

	@RequestMapping()//method = RequestMethod.GET
	public String printWelcome(ModelMap model) {

     /*
        Session session = sessionFactory.getCurrentSession();

        session.beginTransaction();

        System.out.println(session.createSQLQuery("#############################"));
        System.out.println(session.createSQLQuery("Select 1"));


        session.close();
      */

		model.addAttribute("message", "Hello world!");
		return "hello";
	}
}