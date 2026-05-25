package com.example.exam_accounting.service;

import com.example.exam_accounting.projection.ICustomerProjection;
import com.example.exam_accounting.repository.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    ICustomerRepository customerRepository;

    @Override
    public List<ICustomerProjection> getAllCustomers() {
        return customerRepository.findAllCustomerProjections();
    }
}
