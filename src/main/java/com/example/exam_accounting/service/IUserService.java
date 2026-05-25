package com.example.exam_accounting.service;

import com.example.exam_accounting.entity.User;
import com.example.exam_accounting.projection.IUserProjection;

import java.util.List;

public interface IUserService {
    List<IUserProjection> getAllUsers();
}
