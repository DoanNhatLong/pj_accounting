package com.example.exam_accounting.controller.admin;

import com.example.exam_accounting.projection.IUserProjection;
import com.example.exam_accounting.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping
    public List<IUserProjection> getAllUsers() {
        return userService.getAllUsers();
    }

}
