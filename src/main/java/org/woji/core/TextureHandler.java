package org.woji.core;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextureHandler {

    Map<String, BufferedImage> textureMap = new HashMap<>();
    ArrayList<BufferedImage> blockTextureArrayList = new ArrayList<>();

    // Temporary
    public void initializeTextures() {

        loadBlockTextureFromName("grass");
        loadBlockTextureFromName("dirt");
        loadBlockTextureFromName("stone");

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

    private void loadBlockTextureFromName(String textureName) {
        try {
            String fullFilePath = "src/main/resources/"+textureName+".png";
            BufferedImage bufferedImage = ImageIO.read(new File(fullFilePath));
            blockTextureArrayList.add(bufferedImage);
        } catch (IOException e) {
            System.err.println("ERROR: Could not load texture from name '" + textureName + "'");
        }
    }

    public BufferedImage getBufferedImage(String textureName) {
        return textureMap.get(textureName);
    }

    public BufferedImage getBufferedImage(int id) {
        return blockTextureArrayList.get(id - 1);
    }
}
