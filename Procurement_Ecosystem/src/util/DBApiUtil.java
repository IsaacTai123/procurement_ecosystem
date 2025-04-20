/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.io.IOException;
import java.net.http.*;
import java.net.URI;

/**
 *
 * @author linweihong
 */
public class DBApiUtil {
    
    public static void callDB() {

        // call api
        try {
            String json = """
                {
                    "name": "Alvin",
                    "password": "alvin"
                }
                """;

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8000/Login"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            json
                    ))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("✅ Response from server: " + response.body());

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Network error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Other error: " + e.getMessage());
            e.printStackTrace();
        }

    }
    
   
}
