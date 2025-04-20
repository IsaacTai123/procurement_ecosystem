/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author linweihong
 */
public class TimeUtil {
    
    public static String getCurrentDate() {

        LocalDateTime now = LocalDateTime.now();
        String date = now.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"));

        System.out.println("date: " + date);

        return date;
    }
    
    public static String getCurrentTime() {

        LocalDateTime now = LocalDateTime.now();
        String time = now.format(DateTimeFormatter.ofPattern("hh:mma")); // 12-hour with AM/PM

        System.out.println("time: " + time);

        return time;
    }
}
