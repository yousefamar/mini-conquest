package core;

import gfx.Sprite;
import gfx.gui.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import map.World;
import net.IOManager;

@SuppressWarnings("serial")
public class MiniConquest extends JPanel implements Runnable, MouseMotionListener, MouseListener, KeyListener {
	
	public static MiniConquest game;
	
	//TODO: Consider making these not static.
	//public static String saveDir;
	public static Font stdFont = new Font("Arial", Font.PLAIN, 18);
	public static Dimension screenDims = new Dimension(640, 360);
	
	public InputManager inputManager = new InputManager(this);
	public IOManager ioManager = new IOManager(this);
	public Player player = new Player();
	public GUIElement currentGUIScreen = GUIElement.nullGUI;
	public World currentWorld = null;
	
	
	public MiniConquest() {
		/*saveDir = System.getProperty("user.home")+File.separator+".miniConquest"+File.separator;
		File saveFile = new File(saveDir);
		if (!saveFile.exists() && !saveFile.mkdirs())
			System.err.println("Unable to create save directory.");
		File mapFile = new File(saveDir, "maps");
		if (!mapFile.exists() && !mapFile.mkdirs())
			System.err.println("Unable to create user maps directory.");
		 */
		Sprite.loadSprites();
		
		setPreferredSize(screenDims);
		setBackground(Color.BLACK);
		addMouseMotionListener(this);
		addMouseListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		addKeyListener(this);
		currentGUIScreen = new GUIMainMenu(this);
	}
	
	private void tickGame() {
		//TODO: Handle network IO.
		inputManager.handleInput();
		if(currentWorld != null)
			currentWorld.tick();
		currentGUIScreen.tick();
	}
	
	@Override
	public void run() {
		long lastGameTick = (System.nanoTime() / 1000000);
		while(true) {
			if ((System.nanoTime() / 1000000) - lastGameTick > 50) {
				tickGame();
				lastGameTick += 50;
			}
		}
	}
	
	public void startNewMatch() {
		//TODO: Game type and AI select.
		currentGUIScreen = GUIElement.nullGUI;
		currentWorld = new World(player);
		currentGUIScreen = new GUIWorld(currentWorld);
	}
	
	public void catchException(Exception e) {
		e.printStackTrace();
		System.exit(1);
	}
	
	@Override
	public void paint(Graphics gfx) {
		super.paint(gfx);
		gfx.setClip(0, 0, screenDims.width+2, screenDims.height+2);
		if(currentWorld != null)
			currentWorld.render((Graphics2D) gfx);
		currentGUIScreen.render((Graphics2D) gfx);

		repaint();
	}
	
	@Override
	public void update(Graphics gfx) {
		Rectangle bb = gfx.getClipBounds();
		Image offImg = createImage(bb.width, bb.height);
		Graphics offGfx = offImg.getGraphics();
		offGfx.setColor(getBackground());
		offGfx.fillRect(0, 0, bb.width, bb.height);
		offGfx.setColor(getForeground());
		offGfx.translate(-bb.x, -bb.y);
		paint(offGfx);
		gfx.drawImage(offImg, bb.x, bb.y, this);
	}

	@Override
	public void mouseMoved(MouseEvent event) {
		inputManager.addEvent(event);
	}

	@Override
	public void mouseDragged(MouseEvent event) {
		inputManager.addEvent(event);
	}
	
	@Override
	public void mousePressed(MouseEvent event) {
		inputManager.addEvent(event);
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {}

	@Override
	public void mouseReleased(MouseEvent event) {
		inputManager.addEvent(event);
	}

	@Override
	public void mouseEntered(MouseEvent event) {}

	@Override
	public void mouseExited(MouseEvent event) {}

	@Override
	public void keyPressed(KeyEvent event) {
		inputManager.addEvent(event);
	}

	@Override
	public void keyReleased(KeyEvent event) {
		inputManager.addEvent(event);
	}

	@Override
	public void keyTyped(KeyEvent event) {}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Conquest");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game = new MiniConquest();
		frame.add(game);
		new Thread(game, "Main Game Thread").start();
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}