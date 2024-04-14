package com.longpc.devmon.portal.quizportal.util;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.lang.RandomStringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Long PC
 * 05/03/2024| 00:06 | 2024
 **/
public class DataUtil {
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

    public static String generateId() {
        return RandomStringUtils.randomAlphanumeric(6);
    }

    public static String generateSlug(String content) {
        String nowhitespace = WHITESPACE.matcher(content).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static String generateQRCodeImage(String url) throws Exception {
        int imageSize = 200;
        BitMatrix matrix = new MultiFormatWriter().encode(url, BarcodeFormat.QR_CODE,
                imageSize, imageSize);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "png", bos);
        String image = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode
        return image;
    }
}
