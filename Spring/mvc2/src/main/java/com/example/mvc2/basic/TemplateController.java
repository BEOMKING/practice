package com.example.mvc2.basic;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/template")
public class TemplateController {

    @GetMapping("/fragment")
    public String templateFragment() {
        return "template/fragment/fragmentMain";
    }

    @GetMapping("/layout")
    public String templateLayout() {
        return "template/layout/layoutMain";
    }

    @GetMapping("/layoutExtend")
    public String templateLayoutExtend() {
        return "template/layoutExtend/layoutExtendMain";
    }
}
