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
    
    public static Map<String, Object> getUserInfo(String json) {

        Map<String, Object> result = new HashMap<>();

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

                String orgName = firstUser.optString("orgName", null);
                String orgType = firstUser.optString("orgType", null);

                result.put("orgName", orgName);
                result.put("orgType", orgType);

                System.out.println("✅ orgName: " + orgName);
                System.out.println("✅ orgType: " + orgType);
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
