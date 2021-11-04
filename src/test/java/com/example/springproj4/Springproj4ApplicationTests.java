package com.example.springproj4;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Springproj4ApplicationTests {

    @Autowired
    BooksService booksService;

    @Test
    void checkTitles() throws IOException {
        String books = booksService.getBooks();

        JSONArray results = new JSONArray(books);
        for (int i = 0; i < results.length(); i++){
            JSONObject obj1 = results.getJSONObject(i);
            JSONArray bookDetails = obj1.getJSONArray("book_details");
            String title = bookDetails.getJSONObject(0).getString("title");

            System.out.println("Title: " + title);

            assertThat(title).isNotEmpty();
        }
    }

    @Test
    void checkOrder() throws IOException {

        String books = booksService.getBooks();
        JSONArray results = new JSONArray(books);

        ArrayList<Integer> rankArray = new ArrayList<Integer>();

        for (int i = 0; i < results.length(); i++){
            Integer rank = results.getJSONObject(i).getInt("rank");
            rankArray.add(rank);
        }

        Collections.sort(rankArray);

        for (int i = 0; i < rankArray.size(); i++){
            assertEquals(i+1, rankArray.get(i));
        }
    }

}