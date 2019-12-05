package com.CS3560Project.utility;

import com.CS3560Project.exceptions.ParseFailureException;
import com.CS3560Project.sqlworkers.SQLHelper;
import com.CS3560Project.sqlworkers.Table;
import com.CS3560Project.sqlworkers.conditions.Conditional;
import com.CS3560Project.structures.TimeAtMoment;
import com.CS3560Project.structures.products.Product;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {
    /**
     * Generates one random character based off settings supplied
     * @param setting   The type of character you want in response
     * @return          One single character from requested set
     */
    private static char generateRandomChar(CharGeneratorSettings setting) {
        List<Integer> possibleChars = new ArrayList<>();
        // Add all characters to possible outcome list
        if (setting == CharGeneratorSettings.ALL || setting == CharGeneratorSettings.CHARACTER) {
            //  From     A      -> Z
            for (int i = 65; i <= 90; i++) {
                possibleChars.add(i);
            }

            // From      a      -> z
            for (int i = 97; i <= 122; i++) {
                possibleChars.add(i);
            }
        }

        // Add all numbers to possible outcome list
        if (setting == CharGeneratorSettings.ALL || setting == CharGeneratorSettings.NUMBER) {
            // From      0      -> 9
            for (int i = 48; i <= 57; i++) {
                possibleChars.add(i);
            }
        }

        // Selects a random element from 0 to SIZE
        return ((char) possibleChars.get(Global.random.nextInt(possibleChars.size())).intValue());
    }

    private enum CharGeneratorSettings {
        ALL,
        CHARACTER,
        NUMBER,
    }

    public static String generateID(Table table) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < Constants.ID_LENGTH; i++)
            sb.append(generateRandomChar(CharGeneratorSettings.ALL));

        String id = sb.toString();

        // If not allowing duplicate IDs, each database must contain an ID field
        List<?> results = SQLHelper.getFromTableWithConditions(table, new Conditional("id", id));
        if (results.size() > 0) {
            return generateID(table);
        } else {
            return id;
        }
    }

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
     * @param fxImage to convert
     * @return BufferedImage representation
     */
    public static java.awt.image.BufferedImage fxImageToBufferedImage(javafx.scene.image.Image fxImage) {
        return SwingFXUtils.fromFXImage(fxImage, null);
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
     * @param input The object to display
     */
    public static void log(Object input) {
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
                        if (Pattern.compile(Pattern.quote(term), Pattern.CASE_INSENSITIVE).matcher(product.getProductName()).find())
                            return true;
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
     */
    public static void printList(Collection<?> list) {
        for(Object obj : list) {
            System.out.println(obj);
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