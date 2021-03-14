package com.example.TimeTracker.service.Impl;

import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;

public class Utils {
    public static byte[] convertIntegersToBytes(int[] integers) {
        if (integers != null) {
            byte[] outputBytes = new byte[integers.length * 4];

            for(int i = 0, k = 0; i < integers.length; i++) {
                int integerTemp = integers[i];
                for(int j = 0; j < 4; j++, k++) {
                    outputBytes[k] = (byte)((integerTemp >> (8 * j)) & 0xFF);
                }
            }
            return outputBytes;
        } else {
            return null;
        }
    }


    public static int[] byte2int(byte[]src) {
        if (src == null) return null;
        int dstLength = src.length >>> 2;
        int[]dst = new int[dstLength];

        for (int i=0; i<dstLength; i++) {
            int j = i << 2;
            int x = 0;
            x += (src[j++] & 0xff) << 0;
            x += (src[j++] & 0xff) << 8;
            x += (src[j++] & 0xff) << 16;
            x += (src[j++] & 0xff) << 24;
            dst[i] = x;
        }
        return dst;
    }


    public static String extractResourceName(String url) {
        try {
            URL url1 = new URL(url);
            return url1.getHost();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String extractProtocolIdentifier(String url) {
        try {
            URL url1 = new URL(url);
            return url1.getProtocol();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
