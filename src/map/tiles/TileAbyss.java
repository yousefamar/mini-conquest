package map.tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import map.World;

public class TileAbyss extends TileType {

	
	public TileAbyss(Color color) {
		super(color);
	}

	@Override
	public void render(Graphics2D gfx, World world, int x, int y) {
		gfx.setColor(color);
		gfx.fillRect(x, y, 32, 32);
	}
}