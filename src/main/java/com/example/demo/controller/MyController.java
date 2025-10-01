package com.example.demo.controller;

import com.example.demo.dto.MarkdownRequest;
import com.example.demo.service.MarkdownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class MyController {

    private final MarkdownService markdownService;

    @Autowired
    public MyController(MarkdownService markdownService) {
        this.markdownService = markdownService;
    }

    @PostMapping(value = "/doc/download", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> convertMarkdownToWord(@RequestBody MarkdownRequest request) {
        try {
            byte[] wordDocument = markdownService.convertMarkdownToWord(request.getMarkdown());

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=AI_Response.docx")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(wordDocument);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
}
