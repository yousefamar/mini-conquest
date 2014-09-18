package core;

import javax.swing.JApplet;

@SuppressWarnings("serial")
public class MiniConquestApplet extends JApplet {
	public void init() {
		MiniConquest.game = new MiniConquest();
		add(MiniConquest.game);
		new Thread(MiniConquest.game, "Main Game Thread").start();
	}
}
