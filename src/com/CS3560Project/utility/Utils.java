package com.CS3560Project.utility;

import com.CS3560Project.structures.Product;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    /**
     * Given a product list, and a search term phrase, it will return a list containing
     * products with the relevant search terms
     * @param productList   product list to search through
     * @param searchRequest the string to get search terms out of
     * @return              A filtered product list by search terms
     */
    public static List<Product> filterByTerms(List<Product> productList, String searchRequest) {
        return productList.stream()
                .filter(product -> {
                    for (String term : searchRequest.split(Constants.SPACE_REGEX)) {
                        for (String productTerm : product.getSearchTerms()) {
                            if (productTerm.trim().equalsIgnoreCase(term.trim()))
                                return true;
                        }
                    }
                    return false;
                })
                .collect(Collectors.toList());
    }

    /**
     * Formats a double to given decimal precision
     * @param   num               The number to truncate
     * @param   numberOfDecimals  Number of decimals places
     * @return  formatted double
     */
    public static String formatDouble(double num, int numberOfDecimals) {
        StringBuilder formatter = new StringBuilder();
        formatter.append("#.");
        for (int i = 0; i < numberOfDecimals; i++)
            formatter.append("#");

        return (new DecimalFormat(formatter.toString())).format(num);
    }

    /**
     * Prints out the each item in the list
     * @param list  The list to print out
     * @param <T>   Generic Key for contents of list
     */
    public static <T> void printList(List<T> list) {
        for(T item : list) {
            System.out.println(item);
        }
    }

    /**
     * Capitalize every letter after a space.
     * @param sentence The sentence to capitalize.
     * @return capitalized
     */
    public static String capitalize(String sentence) {
        String[] split = sentence.replaceAll("_", " ").split(" ");
        List<String> out = new ArrayList<>();
        for (String s : split)
            out.add(s.length() > 0 ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : "");
        return String.join(" ", out);
    }
}