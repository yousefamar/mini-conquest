package gfx;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Sprite {
	
	public static Sprite player[][] = new Sprite[3][9];
	public static Sprite grassTiles[][] = new Sprite[4][4];
	public static Sprite tree;
	public static Sprite barrel;
	
	public static void loadSprites() {
		BufferedImage spriteMap = null; 
		try {
			spriteMap = ImageIO.read(Sprite.class.getResource("textures/spritemap.png"));
		} catch (IOException e) {
			System.err.println("Unable to load sprites.");
		}

		//TODO: Consider avoiding rotations and standardise tile sprite loading. (also add inverted corners?)
		for (int i = 0; i < 4; i++)
			grassTiles[0][i] = new Sprite(spriteMap, (i%2)*16, (i/2)*16, 16, 16).replaceColor(0xFF000000, 0xFF004400);
		for (int i = 0; i < 4; i++)
			grassTiles[1][i] = new Sprite(spriteMap, (i%2)*16, (i/2)*16, 16, 16).centreScale(-1, 1, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).replaceColor(0xFF000000, 0xFF004400);
		for (int i = 0; i < 4; i++)
			grassTiles[2][i] = new Sprite(spriteMap, (i%2)*16, (i/2)*16, 16, 16).centreScale(1, -1, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).replaceColor(0xFF000000, 0xFF004400);
		for (int i = 0; i < 4; i++)
			grassTiles[3][i] = new Sprite(spriteMap, (i%2)*16, (i/2)*16, 16, 16).centreScale(-1, -1, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).replaceColor(0xFF000000, 0xFF004400);
		
		for (int row = 0; row < 3; row++)
			for (int col = 0; col < 9; col++)
				player[row][col] = new Sprite(spriteMap, col*24, 32+(row*32), 24, 32);
		
		tree = new Sprite(spriteMap, 32, 0, 32, 32);
		barrel = new Sprite(spriteMap, 64, 0, 32, 32);
	}
	
	
	private BufferedImage spriteImg;
	
	private Sprite(BufferedImage spriteMap, int x, int y, int width, int height) {
		this.spriteImg = spriteMap.getSubimage(x, y, width, height);
	}
	
	//TODO: Think of a better way of managing colors.
	private Sprite replaceColor(int oldColor, int newColor){
		for (int x = 0; x < spriteImg.getWidth(); x++)
			for (int y = 0; y < spriteImg.getHeight(); y++)
				if(spriteImg.getRGB(x, y) == oldColor)
					spriteImg.setRGB(x, y, newColor);
		return this;
	}
	
	private Sprite centreScale(double x, double y, int interpolationType){
		AffineTransform transform = new AffineTransform();
		transform.translate(spriteImg.getWidth()/2, spriteImg.getHeight()/2);
		transform.scale(x, y);
		transform.translate(-spriteImg.getWidth()/2, -spriteImg.getHeight()/2);
		spriteImg = new AffineTransformOp(transform, interpolationType).filter(spriteImg, new BufferedImage(spriteImg.getWidth(), spriteImg.getHeight(), spriteImg.getType()));
		return this;
	}
	
	private Sprite rotate(double angle, double x, double y, int interpolationType){
		AffineTransform transform = new AffineTransform();
		transform.rotate(Math.toRadians(angle), x, y);
		spriteImg = new AffineTransformOp(transform, interpolationType).filter(spriteImg, new BufferedImage(spriteImg.getWidth(), spriteImg.getHeight(), spriteImg.getType()));
		return this;
	}

	public void render(Graphics2D gfx, int x, int y) {
		gfx.drawImage(spriteImg, null, x, y);
	}
}