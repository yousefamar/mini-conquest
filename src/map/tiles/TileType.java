package map.tiles;

import java.awt.*;

import map.World;

public abstract class TileType {

	public static TileType abyss = new TileAbyss(new Color(0x00, 0x00, 0x88));
	public static TileType water;
	public static TileType grass = new TileGrass(new Color(0x00, 0x7f, 0x00));

	public Color color;
	
	public TileType(Color color) {
		this.color = color;
	}

	//TODO: Do you really need world or just terrain?
	public abstract void render(Graphics2D gfx, World world, int x, int y);
}