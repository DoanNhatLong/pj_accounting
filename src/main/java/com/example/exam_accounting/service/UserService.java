package com.example.exam_accounting.service;

import com.example.exam_accounting.entity.User;
import com.example.exam_accounting.projection.IUserProjection;
import com.example.exam_accounting.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    IUserRepository userRepository;

    @Override
    public List<IUserProjection> getAllUsers() {
        return userRepository.findAllUserProjections();
    }
}
