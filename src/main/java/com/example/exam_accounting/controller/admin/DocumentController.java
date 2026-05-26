package com.example.exam_accounting.controller.admin;

import com.example.exam_accounting.dto.DocumentRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/admin/documents")
public class DocumentController {
    @PostMapping("/create")
    public ResponseEntity<?> createDocument(@RequestBody DocumentRequestDto request) {
        System.out.println("Nhận được hóa đơn từ khách hàng ID: " + request.customerId());
        return ResponseEntity.ok("Đã nhận dữ liệu thành công!");
    }
}
