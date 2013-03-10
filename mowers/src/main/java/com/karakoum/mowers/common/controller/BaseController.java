package com.karakoum.mowers.common.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    Model model;
    HttpServletRequest request;
    HttpServletResponse response;

    public Model getModel() {
        return model;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }
    
    @ModelAttribute("command")
    public BaseController init(HttpServletRequest request, HttpServletResponse response, Model model) {
        this.model = model;
        this.request = request;
        this.response = response;
        ServletRequestDataBinder binder = new ServletRequestDataBinder(this, "command");
        binder.bind(request);
        return this;
    }
}  
