package com.alkemy.ong.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class SwaggerController {

    @RequestMapping("/api/docs")
    public String getSwaggerDocumentation() {
        return "redirect:/swagger-ui/index.html";
    }
}
