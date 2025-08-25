package com.skibob.home;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class WebappApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebappApplication.class, args);
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("name", "Björn Andersson");
        model.addAttribute("title", "Högskoleingenjör i Datateknik – Portfolio och CV");
        return "index";
    }
}
