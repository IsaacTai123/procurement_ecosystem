/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;
import java.io.IOException;
import java.net.http.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject; // ✅ You need org.json or another JSON library
import org.json.JSONArray;


/**
 *
 * @author linweihong
 */
public class DBApiUtil {
    
    public static Map<String, String> getUserInfo() {

        Map<String, String> result = new HashMap<>();

        try {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://localhost:8000/mocks"))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("✅ Response from server: " + response.body());

            // ✅ Parse response as JSON array
            JSONArray jsonArray = new JSONArray(response.body());

            if (!jsonArray.isEmpty()) {
                JSONObject firstUser = jsonArray.getJSONObject(0); // get the first item in array

                String enterpriseName = firstUser.optString("enterpriseName", null);
                String enterpriseType = firstUser.optString("enterpriseType", null);
                String orgType = firstUser.optString("orgType", null);
                String userName = firstUser.optString("userName", null);
                String userPassword = firstUser.optString("userPassword", null);
                String userType = firstUser.optString("userType", null);
                
                
                result.put("enterpriseName", enterpriseName);
                result.put("enterpriseType", enterpriseType);
                result.put("orgType", orgType);
                result.put("userName", userName);
                result.put("userPassword", userPassword);
                result.put("userType", userType);
                

                System.out.println("✅ orgType: " + orgType);
                System.out.println("✅ enterpriseName: " + enterpriseName);
                System.out.println("✅ enterpriseType: " + enterpriseType);
                System.out.println("✅ userName: " + userName);
                System.out.println("✅ userPassword: " + userPassword);
                System.out.println("✅ userType: " + userType);
                
            } else {
                System.out.println("⚠️ Empty array returned from API.");
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("❌ Network error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("❌ Other error: " + e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
   
}
