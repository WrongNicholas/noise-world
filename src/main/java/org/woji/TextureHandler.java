package org.woji;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class TextureHandler {

    Map<String, BufferedImage> textureMap = new HashMap<>();

    // Temporary
    public void initializeTextures() {
        loadTextureFromName("dirt");
        loadTextureFromName("grass");
        loadTextureFromName("stone");
        loadTextureFromName("player");
    }

    private void loadTextureFromName(String textureName) {
        try {
            String fullFilePath = "src/main/resources/"+textureName+".png";
            BufferedImage bufferedImage = ImageIO.read(new File(fullFilePath));
            textureMap.put(textureName, bufferedImage);
        } catch (IOException e) {
            System.err.println("ERROR: Could not load texture from name '" + textureName + "'");
        }
    }

    public BufferedImage getBufferedImage(String textureName) {
        return textureMap.get(textureName);
    }
}
