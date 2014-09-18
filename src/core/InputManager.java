package core;

import java.awt.event.*;
import java.util.*;

public class InputManager {

	private MiniConquest game;
	private Queue<InputEvent> eventQueue = new LinkedList<InputEvent>();
	
	private boolean[] mouseStates = new boolean[4]; //Booleans take up an entire byte just like bytes.
	private boolean[] keyStates = new boolean[256];
	
	public InputManager(MiniConquest game) {
		this.game = game;
	}
	
	public boolean isButtonDown(int button) {
		return mouseStates[button];
	}
	
	public boolean isKeyDown(int keyCode) {
		return keyStates[keyCode];
	}
	
	public void addEvent(InputEvent event) {
		synchronized (eventQueue) {
			eventQueue.add(event);
		}
	}

	public void handleInput() {
		int queueSize = 0;
		synchronized (eventQueue) {
			queueSize = eventQueue.size();
		}
		if(queueSize == 0)
			return;
		for (int i = 0; i < queueSize; i++) {
			InputEvent event;
			synchronized (eventQueue) {
				event = eventQueue.remove(); //Throws exception for safety.
			}
			switch (event.getID()) {
			case MouseEvent.MOUSE_MOVED:
				game.currentGUIScreen.mouseMoved(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				if (game.currentWorld != null)
					game.currentWorld.mouseMoved(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				break;
			case MouseEvent.MOUSE_DRAGGED:
				game.currentGUIScreen.mouseMoved(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				if (game.currentWorld != null)
					game.currentWorld.mouseMoved(((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				break;
			case MouseEvent.MOUSE_PRESSED:
				mouseStates[((MouseEvent)event).getButton()] = true; //TODO: Check if GUI drag affects world.
				if (!game.currentGUIScreen.mousePressed(((MouseEvent)event).getButton(), ((MouseEvent)event).getX(), ((MouseEvent)event).getY())) {
					if (game.currentWorld != null)
						game.currentWorld.mousePressed(((MouseEvent)event).getButton(), ((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				}
				break;
			case MouseEvent.MOUSE_RELEASED:
				mouseStates[((MouseEvent)event).getButton()] = false;
				if (!game.currentGUIScreen.mouseReleased(((MouseEvent)event).getButton(), ((MouseEvent)event).getX(), ((MouseEvent)event).getY())) {
					if (game.currentWorld != null)
						game.currentWorld.mouseReleased(((MouseEvent)event).getButton(), ((MouseEvent)event).getX(), ((MouseEvent)event).getY());
				}
				break;
			case KeyEvent.KEY_PRESSED:
				keyStates[((KeyEvent)event).getKeyCode()] = true;
				if (!game.currentGUIScreen.keyPressed(((KeyEvent)event).getKeyCode(), ((KeyEvent)event).getKeyChar())) {
					if (game.currentWorld != null)
						game.currentWorld.keyPressed(((KeyEvent)event).getKeyCode(), ((KeyEvent)event).getKeyChar());
				}
				break;
			case KeyEvent.KEY_RELEASED:
				keyStates[((KeyEvent)event).getKeyCode()] = false;
				if (!game.currentGUIScreen.keyReleased(((KeyEvent)event).getKeyCode(), ((KeyEvent)event).getKeyChar())) {
					if (game.currentWorld != null)
						game.currentWorld.keyReleased(((KeyEvent)event).getKeyCode(), ((KeyEvent)event).getKeyChar());
				}
				break;
			default:
				break;
			}
		}
	}
}