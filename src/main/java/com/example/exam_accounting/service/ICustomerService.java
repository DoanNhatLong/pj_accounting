package com.example.exam_accounting.service;

import com.example.exam_accounting.projection.ICustomerProjection;

import java.util.List;

public interface ICustomerService {
    List<ICustomerProjection> getAllCustomers();
}
