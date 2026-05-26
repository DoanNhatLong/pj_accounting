package com.example.exam_accounting.controller.admin;

import com.example.exam_accounting.projection.ICustomerProjection;
import com.example.exam_accounting.service.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/customers")
public class CustomerController {
    @Autowired
    ICustomerService customerService;
    @GetMapping
    public List<ICustomerProjection> getAllCustomers() {
        return customerService.getAllCustomers();
    }

}
