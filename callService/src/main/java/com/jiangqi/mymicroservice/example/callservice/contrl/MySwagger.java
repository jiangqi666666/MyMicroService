package com.jiangqi.mymicroservice.example.callservice.contrl;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MySwagger {

	@RequestMapping("/api/calltest/swagger")
    public String resource() {
        return "forward:/swagger-ui.html";
    }
}
