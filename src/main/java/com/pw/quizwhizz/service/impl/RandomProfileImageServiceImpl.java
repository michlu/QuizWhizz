package com.pw.quizwhizz.service.impl;

import com.pw.quizwhizz.service.RandomProfileImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * Implementacja serwisu udostepniajacej losowe zdjecia dla profili uzytkownikow
 * @author Michał Nowiński
 */
@Service
public class RandomProfileImageServiceImpl implements RandomProfileImageService {

    /** Sciezka do folderu z plikami obrazow */
//    final private String PATH_IMG_PROFILE_DEFAULT = "\\resources\\gfx\\img_profile_default\\"; //windows
    final private String PATH_IMG_PROFILE_DEFAULT = File.separator + "resources" + File.separator + "gfx" + File.separator + "img_profile_default" + File.separator;

    final private ServletContext context;
    final private String directory;
    final private Random random = new Random();

    @Autowired
    public RandomProfileImageServiceImpl(ServletContext context) {
        this.context = context;
        directory = context.getRealPath("")+PATH_IMG_PROFILE_DEFAULT; // pobiera aktualna sciezke z kontekstu aplikacji
    }

    /**
     * Metoda zczytuje pliki z danej sciezki i wypisuje nazwy do konsoli
     */
    @Override
    public void readFile() {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * Metoda zwraca plik losowego obrazu. Wymagany przy rejestracji uzytkownika. Losuje obraz dla zdjec profilowych
     * uzytkownikow.
     * @return zwraca obiekt pliku ktory jest obrazem
     * @throws IOException wymagany przez ImageIO w isImage
     */
    @Override
    public File getRandomProfileImage() throws IOException {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        int size = listOfFiles.length;
        int randomNumber = random.nextInt(size);

        System.out.println("RandomProfileImageService: Ilosc plikow w folderze [img_profile_default]: " + listOfFiles.length);

        // jezeli nie jest obrazem, losuj kolejny plik
        while(!isImage(listOfFiles[randomNumber])){
            randomNumber = random.nextInt(size);
        }
        return listOfFiles[randomNumber];
    }

    /**
     * Metoda sprawdza czy dany plik jest obrazem
     * @param file przyjmuje obiekt pliku (File)
     * @return zwraca wartosc logiczna
     */
    @Override
    public boolean isImage(File file) throws IOException {
        return (ImageIO.read(file) != null);
    }
}
