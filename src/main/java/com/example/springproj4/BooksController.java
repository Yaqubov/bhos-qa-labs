package com.example.springproj4;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.json.JSONArray;

import java.io.IOException;

@RestController
public class BooksController {

    @Autowired
    BooksService booksService;

    @RequestMapping("/")
    public String home() throws IOException {
        String books = booksService.getBooks();

        JSONArray results = new JSONArray(books);
        for (int i = 0; i < results.length(); i++){
            JSONObject obj1 = results.getJSONObject(i);
            JSONArray bookdetails = obj1.getJSONArray("book_details");
            System.out.println("Title: " + bookdetails.getJSONObject(0).getString("title"));
        }

        return booksService.getBooks();
    }
}