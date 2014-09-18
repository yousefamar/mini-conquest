package gfx.gui;

import java.awt.*;
import core.MiniConquest;

public class GUIButton extends GUIElement {

	protected int id;
	private String text;

	public GUIButton(int x, int y, int width, int height, GUIElement parent, int id, String text) {
		super(x, y, width, height, parent);
		this.id = id;
		this.text = text;
	}
	
	@Override
	public boolean mousePressed(int button, int x, int y) {
		parent.componentClicked(this);
		return true;
	}

	@Override
	public void render(Graphics2D gfx) {
		gfx.setColor(mouseOver?Color.GRAY:Color.DARK_GRAY);
		gfx.fillRect(x, y, width, height);
		gfx.setColor(Color.LIGHT_GRAY);
		gfx.drawRect(x, y, width, height);
		gfx.setFont(MiniConquest.stdFont);
		int textWidth = gfx.getFontMetrics().stringWidth(text);
		gfx.drawString(text, (x+x+width)/2-textWidth/2, (y+y+height+gfx.getFont().getSize()-4)/2);
	}
}