package com.pw.quizwhizz.util;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Narzedzia do edycji obrazow
 * @author Michał Nowiński
 */
@Component
public class ImageUtil {

    /**
     * Zmienia rozmiar obrazu
     * @param image obraz przekazany jako byte
     * @param width szerokosc wyjsciowa obrazu
     * @param height wysokosc wyjsciowa obrazu
     * @return zwraca BufferedImage obrazu
     * @throws IOException wymagane dla ImageIO
     */
    public static BufferedImage resizeImage(byte[] image, int width, int height) throws IOException {
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage originalImage = ImageIO.read(in);
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(originalImage, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

    /**
     * Metoda przeciazona
     * @param originalImage przyjmuje obraz jako BufferedImage
     */
    public static BufferedImage resizeImage(BufferedImage originalImage, int width, int height) throws IOException {
        final BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        final Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setComposite(AlphaComposite.Src);
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(originalImage, 0, 0, width, height, null);
        graphics2D.dispose();
        return bufferedImage;
    }

    /**
     * @param image przujmuje BufferedImage
     * @return zwraca obraz w wersji byte'owej. Konwertuje na format PNG
     */
    public static byte[] imageToByte(BufferedImage image) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "PNG", stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return stream.toByteArray();
    }


}
