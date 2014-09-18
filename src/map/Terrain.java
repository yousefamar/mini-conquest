package map;

import gfx.Renderable;
import gfx.Sprite;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import javax.imageio.ImageIO;
import map.tiles.TileType;
import core.MiniConquest;

public class Terrain implements Renderable {

	private static HashMap<Integer, TileType> colorTileMap = new HashMap<Integer, TileType>();
	static {
		colorTileMap.put(0xFF007f00, TileType.grass);
	}
	
	private TileType[][] tiles;
	private World world;
	
	//TODO: Flesh out constructor.
	public Terrain(World world) {
		this.world = world;
	}

	public void generate(int seed) {} //TODO: Think about terrain and entity generation (e.g. trees).
	
	public void loadFromSave(String mapName) {
		BufferedImage mapImg = null;
		try {
			//mapImg = ImageIO.read(new File(MiniConquest.saveDir+"maps"+File.separator, mapName+".png"));
			mapImg = ImageIO.read(Sprite.class.getResource("textures/"+mapName+".png"));
		} catch (IOException e) {
			System.err.println("Unable to load custom map \""+mapName+"\".");
		}
		tiles = new TileType[mapImg.getWidth()][mapImg.getHeight()];
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				int terrainCol = mapImg.getRGB(x, y);
				if((terrainCol>>24)==0) {
					tiles[x][y] = TileType.abyss;
				} else {
					TileType tileType = colorTileMap.get(terrainCol);
					if (tileType == null) {
						//TODO: Code in sand.
						//System.out.println("Warning: The custom map \""+mapName+"\" contains invalid data at ["+x+","+y+"].");
						tiles[x][y] = TileType.abyss;
					} else 
						tiles[x][y] = tileType;
				}
			}
		}
	}

	@Override
	public void render(Graphics2D gfx) {
		int startX = 0;
		int startY = 0;
		//TODO: Do not iterate through coordinates outside the window. Use the window diagonal as a max.
		int endX = tiles.length;
		int endY = tiles[0].length;
		for (int x = startX; x < endX; x++)
			for (int y = startY; y < endY; y++)
				tiles[x][y].render(gfx, world, x<<5, y<<5);
		/*AffineTransform tempTrans = gfx.getTransform();
		gfx.scale(32, 32);
		gfx.drawImage(mapImg, null, 0, 0);
		gfx.setTransform(tempTrans);*/
	}
	
	public int getWidth() {
		return tiles.length<<5; //TODO: Softcode tile-size.
	}

	public int getHeight() {
		return tiles[0].length<<5;
	}

	public TileType getTile(int x, int y) {
		try {
			return tiles[x>>5][y>>5];
		} catch (ArrayIndexOutOfBoundsException e) {
			return TileType.abyss;
		}
	}
	
	public TileType[][] getTiles() {
		//TODO: Encapsulate.
		return tiles;
	}
}