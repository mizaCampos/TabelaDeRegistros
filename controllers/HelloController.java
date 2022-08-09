package com.miza.sprimgmvc.TabelaDeRegistros.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HelloController {

    @GetMapping("/hello-view")
    public ModelAndView hello(){
        System.out.println("***********");
        ModelAndView mv = new ModelAndView("hello");
        mv.addObject("nome", "maria" );
        return mv;
    }

    @GetMapping("/hello-model")
    public String hello(Model model){
        System.out.println("***********");
        model.addAttribute("nome", "miza");
        return "hello"; //o Spring vai renderizar o arquivo templates/hello.html
    }

    @GetMapping("/hello-servlet")
    public String hello(HttpServletRequest request){
        System.out.println("***********");
        request.setAttribute("nome", "miza");
        return "hello"; //o Spring vai renderizar o arquivo templates/hello.html
    }
}
