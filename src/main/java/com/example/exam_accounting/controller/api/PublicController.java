package com.example.exam_accounting.controller.api;

import com.example.exam_accounting.entity.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = "*")
public class PublicController {
    @GetMapping("/doc-types")
    public ResponseEntity<List<String>> getDocumentTypes() {
        List<String> types = Arrays.stream(Document.DocumentType.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        return ResponseEntity.ok(types);
    }
}
