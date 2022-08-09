package com.miza.sprimgmvc.TabelaDeRegistros.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(){
        return "home"; //rederiza o arquivo templates/home.html
    }
}
