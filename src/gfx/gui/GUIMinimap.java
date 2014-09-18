package gfx.gui;

import java.awt.*;
import java.awt.geom.AffineTransform;
import entities.*;
import map.World;
import map.tiles.TileType;

public class GUIMinimap extends GUIElement {

	private World world;
	private int cenOffsetX, cenOffsetY;

	public GUIMinimap(int x, int y, World world) { //TODO: Zoom.
		super(x, y, 100, 100, null);
		this.world = world;
		this.cenOffsetX = world.getTerrainWidth()>>5;
		this.cenOffsetY = world.getTerrainHeight()>>5;
	}

	@Override
	public void render(Graphics2D gfx) {
		//TODO: Clip to minimap bounds.
		gfx.setColor(TileType.abyss.color);
		gfx.fillRect(x, y, width, height);
		
		AffineTransform tempTrans = gfx.getTransform();
		gfx.translate(x + (width>>1) - cenOffsetX, y + (height>>1) - cenOffsetY);
		gfx.translate(cenOffsetX, cenOffsetY);
		gfx.scale(1, world.viewport.viewPitch*0.5);
		gfx.rotate(-world.viewport.viewYaw);
		gfx.translate(-cenOffsetX, -cenOffsetY);
		
		//TODO: Do not iterate through coordinates outside the window. Use the window diagonal as a max and min.
		for (int x = 0; x < world.getTerrainWidth(); x+=32) {
			for (int y = 0; y < world.getTerrainHeight(); y+=32) {
				gfx.setColor(world.getTile(x, y).color);
				gfx.fillRect(x>>4, y>>4, 3, 3);
			}
		}
		
		synchronized(world.entities) {
			for (Entity entity : world.entities) {
				if (!(entity instanceof Tangible))
					continue;
				//TODO: Render enemy and neutral entities.
				if (world.player.controls(entity)) {
					gfx.setColor(world.player.color);
					gfx.fillRect(entity.x>>4, entity.y>>4, 2, 2);
				}
			}
		}
		gfx.setTransform(tempTrans); //It sets a copy, not instance.
		
		gfx.setColor(Color.BLACK);
		gfx.drawRect(x, y, width-1, height-1);
	}
}