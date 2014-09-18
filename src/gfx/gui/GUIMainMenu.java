package gfx.gui;

import core.MiniConquest;

public class GUIMainMenu extends GUIElement {

	private MiniConquest game;

	public GUIMainMenu(MiniConquest game) {
		super();
		this.game = game;
		int centreWidthBegin = (MiniConquest.screenDims.width - 150)/2;
		subElements.add(new GUIButton(centreWidthBegin, 110, 150, 25, this, 0, "Single Player"));
		subElements.add(new GUIButton(centreWidthBegin, 160, 150, 25, this, 1, "Join"));
		subElements.add(new GUIButton(centreWidthBegin, 210, 150, 25, this, 2, "Host"));
		subElements.add(new GUIButton(centreWidthBegin, 260, 150, 25, this, 3, "Options"));
		subElements.add(new GUIButton(centreWidthBegin, 310, 150, 25, this, 4, "Bla"));
	}
	
	@Override
	protected void componentClicked(GUIElement element) {
		switch (((GUIButton) element).id) {
		case 0:
			game.startNewMatch();
			break;
		case 1:
			game.ioManager.joinGame("localhost", 8000);
			break;
		case 2:
			game.ioManager.hostGame(2);
			break;
		case 3:

			break;
		case 4:

			break;
		default:
			break;
		}
	}
}