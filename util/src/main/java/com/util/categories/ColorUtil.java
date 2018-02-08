package com.util.categories;


public final class ColorUtil {
    private ColorUtil() {

    }

    /**
     * Add alpha string.
     *
     * @param originalColor color, without alpha
     * @param alpha         from 0.0 to 1.0
     * @return string
     */
    public static String addAlpha(String originalColor, double alpha) {
        long alphaFixed = Math.round(alpha * 255);
        String alphaHex = Long.toHexString(alphaFixed);
        if (alphaHex.length() == 1) {
            alphaHex = "0" + alphaHex;
        }
        originalColor = originalColor.replace("#", "#" + alphaHex);
        return originalColor;
    }
}
