package map.tiles;

import gfx.Sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import map.World;

public class TileGrass extends TileType {
	
	public TileGrass(Color color) {
		super(color);
	}

	@Override
	public void render(Graphics2D gfx, World world, int x, int y) {
		/*Sprite.grassTiles[0][3].render(gfx, x, y);
		Sprite.grassTiles[1][3].render(gfx, x+16, y);
		Sprite.grassTiles[2][3].render(gfx, x, y+16);
		Sprite.grassTiles[3][3].render(gfx, x+16, y+16);*/
		
		boolean up = world.getTile(x, y-32)==this;
		boolean down = world.getTile(x, y+32)==this;
		boolean left = world.getTile(x-32, y)==this;
		boolean right = world.getTile(x+32, y)==this;

		if (!up && !left)
			Sprite.grassTiles[0][0].render(gfx, x, y);
		else if (!up && left)
			Sprite.grassTiles[0][1].render(gfx, x, y);
		else if (up && !left)
			Sprite.grassTiles[0][2].render(gfx, x, y);
		else if (up && left)
			Sprite.grassTiles[0][3].render(gfx, x, y);
		
		if (!up && !right)
			Sprite.grassTiles[1][0].render(gfx, x+16, y);
		else if (!up && right)
			Sprite.grassTiles[1][1].render(gfx, x+16, y);
		else if (up && !right)
			Sprite.grassTiles[1][2].render(gfx, x+16, y);
		else if (up && right)
			Sprite.grassTiles[1][3].render(gfx, x+16, y);
		
		if (!down && !left)
			Sprite.grassTiles[2][0].render(gfx, x, y+16);
		else if (!down && left)
			Sprite.grassTiles[2][1].render(gfx, x, y+16);
		else if (down && !left)
			Sprite.grassTiles[2][2].render(gfx, x, y+16);
		else if (down && left)
			Sprite.grassTiles[2][3].render(gfx, x, y+16);
		
		if (!down && !right)
			Sprite.grassTiles[3][0].render(gfx, x+16, y+16);
		else if (!down && right)
			Sprite.grassTiles[3][1].render(gfx, x+16, y+16);
		else if (down && !right)
			Sprite.grassTiles[3][2].render(gfx, x+16, y+16);
		else if (down && right)
			Sprite.grassTiles[3][3].render(gfx, x+16, y+16);
	}
}