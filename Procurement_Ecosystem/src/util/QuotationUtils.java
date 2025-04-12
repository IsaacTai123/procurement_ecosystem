/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import model.quotation.Quotation;
import java.util.List;

/**
 *
 * @author qiyaochen
 */
public class QuotationUtils {
    public static Quotation getBestQuotation(List<Quotation> list) {
        return list.stream()
                   .min((a, b) -> Double.compare(a.getPrice(), b.getPrice()))
                   .orElse(null);
    }
}