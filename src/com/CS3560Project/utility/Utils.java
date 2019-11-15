package com.CS3560Project.utility;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.structures.TimeAtMoment;
import com.CS3560Project.structures.products.Product;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    /**
     * Converts Base64 to it's BufferedImage representation
     * @param base64 to convert
     * @return BufferedImage representation
     */
    public static java.awt.image.BufferedImage base64ToBufferedImage(String base64) {
        try {
            byte[] array = Base64.getMimeDecoder().decode(base64);
            ByteArrayInputStream bis = new ByteArrayInputStream(array);
            return ImageIO.read(bis);
        } catch (Exception e) {
            throw new ParseFailureException(base64, java.awt.image.BufferedImage.class);
        }
    }

    /**
     * Converts BufferedImage to it's FXImage representation
     * @param bufferedImage to convert
     * @return FXImage representation
     */
    public static javafx.scene.image.Image bufferedImageToFXImage(java.awt.image.BufferedImage bufferedImage) {
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    /**
     * Converts a FXImage to it's BufferedImage representation
     * @param fximage to convert
     * @return BufferedImage representation
     */
    public static java.awt.image.BufferedImage fxImageToBufferedImage(javafx.scene.image.Image fximage) {
        return SwingFXUtils.fromFXImage(fximage, null);
    }

    /**
     * Converts a BufferedImage to it's Base64 representation
     * @param bufferedImage to convert
     * @return base64 representation
     */
    public static String bufferedImageToBase64(java.awt.image.BufferedImage bufferedImage) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", bos);
            byte[] imageBytes = bos.toByteArray();
            return Base64.getMimeEncoder().encodeToString(imageBytes);
        } catch (Exception e) {
            throw new ParseFailureException(bufferedImage, Base64.class);
        }
    }

    /**
     * Sends a message out to console with time stamp of log execution
     * @param input The message to display
     */
    public static void log(String input) {
        TimeAtMoment timeAtMoment = new TimeAtMoment(System.currentTimeMillis());
        System.out.println("[" + timeAtMoment + "] " + input);
    }

    /**
     * Given a product list, and a search term phrase, it will return a list containing
     * products with the relevant search terms
     * @param   productList   product list to search through
     * @param   searchRequest the string to get search terms out of
     * @return  A filtered product list by search terms
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
     * @param   sentence The sentence to capitalize.
     * @return  capitalized phrase
     */
    public static String capitalize(String sentence) {
        String[] split = sentence.replaceAll("_", " ").split(" ");
        List<String> out = new ArrayList<>();
        for (String s : split)
            out.add(s.length() > 0 ? s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() : "");
        return String.join(" ", out);
    }
}