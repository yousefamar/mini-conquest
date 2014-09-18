package gfx.gui;

import java.awt.*;

import core.Player;
import entities.Entity;

public class GUIBuildButton extends GUIElement {

	private Player player;
	private Entity entity;
	
	public GUIBuildButton(int x, int y, GUIElement parent, Player player, Entity entity) {
		super(x, y, 50, 50, parent);
		this.player = player;
		this.entity = entity;
		//TODO: Soft-code or re-implement.
		entity.xRot = x + 50/2;
		entity.yRot = y + 50/2 + 10;
	}
	
	@Override
	public boolean mousePressed(int button, int x, int y) {
		player.heldStructure = entity.clone();
		return true;
	}

	@Override
	public void render(Graphics2D gfx) {
		gfx.setColor(mouseOver?Color.GRAY:Color.DARK_GRAY);
		gfx.fillRect(x, y, width, height);
		gfx.setColor(Color.LIGHT_GRAY);
		gfx.drawRect(x, y, width, height);
		//TODO: If you remove the Renderable interface, consider adding coordinates to entity rendering.
		entity.render(gfx);
	}
}