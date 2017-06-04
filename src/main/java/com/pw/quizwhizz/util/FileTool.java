package com.pw.quizwhizz.util;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

/**
 * Narzedzie obslugi plikow (File)
 * @author Michał Nowiński
 */
@Component
public class FileTool {
    /**
     * Metoda rozczytuje rozszerzenie pliku
     * @param file przyjmuje obiekt pliku
     * @return zwraca String z rozszerzeniem
     */
    public static String getFileExtension(File file) {
        if (file == null) {
            throw new NullPointerException("FileTool: file jest nullem");
        }
        if (!file.isFile()) {
            throw new IllegalArgumentException("FileTool: wywałołany obiekt nie jest plikiem. Scieżka: "
                    + file.getAbsolutePath());
        }
        String fileName = file.getName();
        int i = fileName.lastIndexOf('.');
        if (i > 0) {
            return fileName.substring(i + 1);
        } else {
            return "";
        }
    }

    /**
     * Metoda konwertuje pliki MultipartFile na plik File
     */
    private File convertMultipartFileToFile(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        convertFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return convertFile;
    }

    /**
     * Metoda zwraca tablice bitowa konwetujac z pliku (File)
     * @param file przyjmuje plik File
     * @return zwraca tablice byte[]
     * @throws IOException wymagany przez FileInputStream
     */
    public static byte[] getFileBytes(File file) throws IOException {
        ByteArrayOutputStream ous = null;
        InputStream ios = null;
        try {
            byte[] buffer = new byte[4096];
            ous = new ByteArrayOutputStream();
            ios = new FileInputStream(file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1)
                ous.write(buffer, 0, read);
        } finally {
            try {
                if (ous != null)
                    ous.close();
            } catch (IOException e) {
            }
            try {
                if (ios != null)
                    ios.close();
            } catch (IOException e) {
            }
        }
        return ous.toByteArray();
    }
}
