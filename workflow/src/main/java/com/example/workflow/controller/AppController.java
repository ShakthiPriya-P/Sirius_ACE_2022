package com.example.workflow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import com.example.workflow.model.Job;
import com.example.workflow.model.User;

import com.example.workflow.service.CustomUserDetailsService;
import com.example.workflow.service.JobService;

//https://fluvid.com/videos/detail/4XaD5tQZ7QHOz-21G
 
@Controller
public class AppController {
    
	@Autowired
    @Qualifier("userService")
    private CustomUserDetailsService userService;
	
    @Autowired
    @Qualifier("jobService")
    private JobService jobService;
    
    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }
    String username;
    
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
         
        return "signup";
    }
    
  @GetMapping("/menu")
  public String viewMenuPage() {
      return "menu";
  }
  
    @PostMapping("/process_register")
    public String processRegister(User user) {
    	userService.encodePassword(user);
        return "register_success";
    }
    
    @GetMapping("/enter_job")
    public String showJobForm(Model model) {
        model.addAttribute("job", new Job());
         
        return "enter_job";
    }
    
    @PostMapping("/process_save")
    public String processSave(Job job) {
    	jobService.SaveJob(job);
        return "save_success";
    }
    
    @GetMapping("/jobs")
    public String viewJobPage(Model model) {
    	jobService.Jobmodel(model);
        return "jobs";
    }
    
    @GetMapping("/users")
    public String listUsers(Model model) {
    	userService.userModel(model); 
        return "users";
    }

    @PostMapping("/save_assignment")
    public String saveAssignment(Job job) {
    	jobService.assignment(job); 
        return "save_success";
    }
    
    
    
    @GetMapping("/accept/{job}")
    public String acceptButton(@PathVariable Job job) {
    	jobService.accept(job);
    	jobService.calculateStatus(job);
    	
    	return "menu";
    	
    }
    
    @GetMapping("/reject/{job}")
    public String rejectButton(@PathVariable Job job) {
    	jobService.reject(job);
    	jobService.calculateStatus(job);
    	jobService.deleteJob(job);
    	return "enter_job";
    }
        
}