package com.res.circuit;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RequiredArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @GetMapping("/book")
    public ResponseEntity<String> caller(@RequestParam(name = "status") String status) throws IOException {
        bookService.book(status);

        return ResponseEntity.ok("Response completed");
    }
}
