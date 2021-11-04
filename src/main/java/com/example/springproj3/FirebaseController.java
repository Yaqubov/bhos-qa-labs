package com.example.springproj3;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;

// Use the application default credentials
@RestController
public class FirebaseController {

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

    @RequestMapping("/add")
    public String add() throws IOException, ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference docRef = db.collection("users").document("Armin");

        Map<String, Object> data = getUser();

        ApiFuture<WriteResult> result = docRef.set(data);

        System.out.println("Update time: " + result.get().getUpdateTime());

        return "Data is written";
    }

    @RequestMapping("/")
    public String home() throws ExecutionException, InterruptedException {
        String response;

        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> query = db.collection("users").get();
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();

        response = "";

        for (QueryDocumentSnapshot document : documents) {
            System.out.println("User: " + document.getId());
            response += "<b>User: " + document.getId() + "</b><br>Name: " + document.get("name") + "<br>Age: " + document.get("age")+"<br><br>";
//            response = String.format("User: %s\n\tName: %s\n\tAge: %s\n", document.getString("name"), document.getString("age"));
        }

        return response;
    }


}