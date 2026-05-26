package com.example.exam_accounting.utils;

import com.example.exam_accounting.entity.User;
import com.example.exam_accounting.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    @Autowired
    @Lazy
    private IUserRepository userRepository;

    public Long getCurrentUserId() {
        // Lấy username (đang là email) từ context
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        // Truy vấn DB để lấy User, sau đó trả về ID
        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với email: " + email));
    }
}
