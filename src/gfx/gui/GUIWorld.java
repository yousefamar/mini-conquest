package gfx.gui;

import java.awt.*;
import core.MiniConquest;
import map.World;

public class GUIWorld extends GUIElement {

	//TODO: Consider implementing a layer system.
	
	private World world;
	
	private int fps;
	private int renderTicks;
	private long lastFPS = (System.nanoTime() / 1000000);
	private float usedRAM;

	public GUIWorld(World world) {
		super();
		this.world = world;
		this.subElements.add(new GUIActionBar(99, MiniConquest.screenDims.height - 78, world.player));
		this.subElements.add(new GUIMinimap(0, MiniConquest.screenDims.height - 98, world));
	}

	public void tick(){
		usedRAM = (((float)Runtime.getRuntime().totalMemory())/((float)Runtime.getRuntime().maxMemory()))*100;
	}
	
	@Override
	public void render(Graphics2D gfx) {
		if ((System.nanoTime() / 1000000) - lastFPS > 1000) {
			fps = renderTicks;
			renderTicks = 0;
			lastFPS += 1000;
		}
		renderTicks++;

		super.render(gfx);
		if (world.player.heldStructure != null) {
			Composite tempComp = gfx.getComposite();
			AlphaComposite alphaComp = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6F);
		    gfx.setComposite(alphaComp);
			world.player.heldStructure.render(gfx);
			gfx.setComposite(tempComp);
		}
		
		gfx.setColor(Color.WHITE);
		gfx.setFont(MiniConquest.stdFont);
		gfx.drawString("FPS: "+fps, 5, 20);
		gfx.drawString("RAM: "+usedRAM+"%", 5, 40);
	}
}