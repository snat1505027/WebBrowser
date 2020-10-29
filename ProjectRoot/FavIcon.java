
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import net.sf.image4j.codec.ico.ICODecoder;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Nahida
 */
public class FavIcon {

    HashMap<String, ImageIcon> favicon = new HashMap<>();
    String URL;
    String finalUrl;

    public FavIcon(String URL) throws MalformedURLException {
        this.URL = URL;
        final int protocolSepLoc = this.URL.indexOf("://");
        if (protocolSepLoc > 0) {
            // workout the location of the favicon.
            final int pathSepLoc = this.URL.indexOf("/", protocolSepLoc + 3);
            String rootLoc = (pathSepLoc > 0) ? this.URL.substring(0, pathSepLoc) : this.URL;
            finalUrl = rootLoc + "/favicon.ico";
        }
    }

    public ImageIcon getIcon() {
        if (favicon.get(finalUrl) != null) {
            return favicon.get(finalUrl);
        }
        java.util.List<BufferedImage> images = null;
        try {
            InputStream istr = new URL(finalUrl).openStream();
            images = ICODecoder.read(istr);

        } catch (MalformedURLException ex) {
            Logger.getLogger(FavIcon.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(FavIcon.class.getName()).log(Level.SEVERE, null, ex);
        }
        ImageIcon img = new ImageIcon(images.get(0));
        Image image = img.getImage();
        ImageIcon finalImg = new ImageIcon(getScaledImage(image, 16, 16));
        favicon.put(finalUrl, finalImg);
        return img;
    }

    private Image getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

}
