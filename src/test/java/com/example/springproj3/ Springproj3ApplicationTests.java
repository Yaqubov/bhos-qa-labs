package com.example.springproj3;

import com.google.api.core.ApiFuture;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class Springproj3ApplicationTests {

    private Map<String, Object> getUser() {
        Random random = new Random();
        Map<String, Object> data = new HashMap<>();

        int age = random.nextInt(100);
        String username = "Armin_" + age;
        System.out.println("Username: " + username);

        data.put("name", username);
        data.put("age", age);

        return data;
    }

    @Test
    @Order(1)
    void FirebaseDataInsertTest() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        Map<String, Object> data = getUser();
        System.out.println("Data: " + data);
        DocumentReference docRef = db.collection("users").document(data.get("name").toString());

        ApiFuture<WriteResult> result = docRef.set(data);

        String updateTime = result.get().getUpdateTime().toString();

        System.out.println("Update time: " + updateTime);

        assertFalse(updateTime.isEmpty());
    }

    @Test
    @Order(2)
    void FirebaseDataStoreTest() throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        Map<String, Object> data = getUser();
        System.out.println("Data: " + data);

        DocumentReference docRef = db.collection("users").document(data.get("name").toString());
        ApiFuture<WriteResult> result =  docRef.set(data);

        String updateTime = result.get().getUpdateTime().toString();

        System.out.println("Data is written");
        System.out.println("Fetching the data");

        docRef = db.collection("users").document(data.get("name").toString());

        ApiFuture<DocumentSnapshot> future = docRef.get();

        DocumentSnapshot document = future.get();

        System.out.println("Fetched data: " + document.getData());

        assertTrue(document.exists());
        assertThat(data.get("name").toString()).isEqualTo(document.get("name").toString());
        assertThat(data.get("age").toString()).isEqualTo(document.get("age").toString());
    }
}