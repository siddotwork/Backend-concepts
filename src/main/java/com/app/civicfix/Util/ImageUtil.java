package com.app.civicfix.Util;

import java.io.ByteArrayOutputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ImageUtil {

    public static byte[] compressImage(byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0]; // Return empty array if input is empty
        }

        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[4096];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                outputStream.write(buffer, 0, count);
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Failed to compress image", e);
        } finally {
            deflater.end();
        }
    }

    public static byte[] decompressImage(byte[] data) {
        if (data == null || data.length == 0) {
            return new byte[0]; // Return empty array if input is empty
        }

        Inflater inflater = new Inflater();
        inflater.setInput(data);

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length)) {
            byte[] buffer = new byte[4096];
            while (!inflater.finished()) {
                try {
                    int count = inflater.inflate(buffer);
                    if (count == 0) {
                        break;
                    }
                    outputStream.write(buffer, 0, count);
                } catch (DataFormatException e) {
                    throw new RuntimeException("Failed to decompress image", e);
                }
            }
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error during decompression", e);
        } finally {
            inflater.end();
        }
    }
}
