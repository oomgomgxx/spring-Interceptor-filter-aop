package com.td.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TestController {

    @InitBinder
    public void InitBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");//表单 20180818
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @RequestMapping("/")
    public String test() {
        System.out.println("执行处理器");
        return "index";
    }

    @ResponseBody
    @PostMapping("/add")
    public Person add(@RequestBody Person person) { // 接收的是Json
        System.out.println(person);
        person.setName("server ：" + person.getName());
        return person;
    }

    @ResponseBody
    @PostMapping("/add2")
    public Person add2(@RequestBody String person) throws Exception {
        person = URLDecoder.decode(person, "utf-8");
        System.out.println(person);
        ObjectMapper mapper = new ObjectMapper();
        Person p = mapper.readValue(person, Person.class);
        p.setName("super:" + p.getName());
        return p;
    }

    @ResponseBody
    @PostMapping("date")
    public Date date(Date date) {
        System.out.println(date);
        return date;
    }

}
