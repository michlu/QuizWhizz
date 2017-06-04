package com.pw.quizwhizz.service;

import java.io.File;
import java.io.IOException;

/**
 * Abstrakcyjna warstwa serwisu
 * @author Michał Nowiński
 */
public interface RandomProfileImageService {
    void readFile();
    File getRandomProfileImage() throws IOException;
    boolean isImage(File file) throws IOException;
}
