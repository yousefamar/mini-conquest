package gfx.gui;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import core.MiniConquest;
import gfx.Renderable;

public abstract class GUIElement implements Renderable { //TODO: Rename after implementing the panel system.
	
	//TODO: Think about if this is a good idea.
	public static GUIElement nullGUI = new GUIElement(0, 0, 0, 0, null) {
		@Override
		public void render(Graphics2D gfx) {}
	};
	
	protected int x, y;
	protected int width, height;
	protected boolean mouseOver;

	protected GUIElement parent;
	protected ArrayList<GUIElement> subElements = new ArrayList<GUIElement>();
	
	public GUIElement() {
		//TODO: Think this through and check. Consider a method of identifying guiScreens.
		this(0, 0, MiniConquest.screenDims.width, MiniConquest.screenDims.height, null);
	}

	public GUIElement(int x, int y, int width, int height, GUIElement parent) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.parent = parent;
	}

	public void mouseMoved(int x, int y) {
		mouseOver = isPosInBounds(x, y);
		for (GUIElement subElement : subElements)
			subElement.mouseMoved(x, y);
	}
	
	public boolean mousePressed(int button, int x, int y) {
		for (int id = subElements.size()-1; id >= 0 ; id--) {
			GUIElement subElement = subElements.get(id);
			if(subElement.mouseOver)
				return subElement.mousePressed(button, x, y);
		}
		return false;
	}
	
	public boolean mouseReleased(int button, int x, int y) {
		for (int id = subElements.size()-1; id >= 0 ; id--) {
			GUIElement subElement = subElements.get(id);
			if(subElement.mouseOver)
				return subElement.mouseReleased(button, x, y);
		}
		return false;
	}

	public boolean keyPressed(int keyCode, char keyChar) {
		for (int id = subElements.size()-1; id >= 0 ; id--) {
			GUIElement subElement = subElements.get(id);
			if(subElement.mouseOver)
				return subElement.keyPressed(keyCode, keyChar);
		}
		return false;
	}

	public boolean keyReleased(int keyCode, char keyChar) {
		for (int id = subElements.size()-1; id >= 0 ; id--) {
			GUIElement subElement = subElements.get(id);
			if(subElement.mouseOver)
				return subElement.keyReleased(keyCode, keyChar);
		}
		return false;
	}
	
	protected void componentClicked(GUIElement element) {} //TODO: Make parent pass handling methods for other events too.
	
	public void tick(){}
	
	protected boolean isPosInBounds(int x, int y) {
		return (x >= this.x && x <= (this.x + width) && y >= this.y && y <= (this.y + height));
	}
	
	@Override
	public void render(Graphics2D gfx) {
		for (GUIElement element : subElements)
			element.render(gfx);
	}
}