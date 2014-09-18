package gfx.gui;

import java.awt.*;
import core.MiniConquest;
import core.Player;
import entities.inert.Barrel;
import entities.inert.Tree;

public class GUIActionBar extends GUIElement {

	private Color bgColor = new Color(0, 0, 44);

	private Player player;

	public GUIActionBar(int x, int y, Player player) {
		super(x, y, MiniConquest.screenDims.width-97, 80, null);
		this.player = player;
		
		this.subElements.add(new GUIBuildButton(x+15, y+15, this, player, new Tree(null, 0, 0)));
		this.subElements.add(new GUIBuildButton(x+80, y+15, this, player, new Barrel(null, 0, 0)));
	}
	
	@Override
	public boolean mousePressed(int button, int x, int y) {
		if(!super.mousePressed(button, x, y))
			player.heldStructure = null;
		return true;
	}

	@Override
	public void render(Graphics2D gfx) {
		gfx.setColor(bgColor);
		gfx.fillRect(x, y, width-1, height-1);
		gfx.setColor(Color.BLACK);
		gfx.drawRect(x, y, width-1, height-1);
		
		super.render(gfx);
	}
}