package map;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.util.*;

import physics.*;
import map.tiles.TileType;
import utils.*;
import core.MiniConquest;
import core.Player;
import entities.*;
import entities.inert.Tree;
import gfx.*;

public class World implements Renderable {

	//TODO: Clean up.
	public Player player; //TODO: Encapsulate.
	private Terrain terrain = new Terrain(this);
	public LinkedList<Entity> entities = new LinkedList<Entity>(); //TODO: Encapsulate.
	private Queue<Tickable> entitiesToTick = new LinkedList<Tickable>();
	private PhysicsEngine physEngine = new PhysicsEngine(this);
	private int clickX, clickY;
	private int mouseX, mouseY; //TODO: Consider moving to InputManager (GUI and World layer separation?).
	public Viewport viewport = new Viewport();
	private MouseBox mouseBox;
	
	public World(Player player) {
		//TODO: Change world constructor and move to different package?
		this.player = player;
		terrain.loadFromSave("testMap");
		joinEntity(new Hero(this, 200, 200));
		joinEntity(mouseBox = new MouseBox(this));
	}
	
	public int getTerrainWidth() { //TODO: Rethink terrain field visibilty.
		return terrain.getWidth();
	}
	
	public int getTerrainHeight() {
		return terrain.getHeight();
	}
	
	public TileType getTile(int x, int y) {
		return terrain.getTile(x, y);
	}
	
	public void joinEntity(Entity entity) {
		synchronized (entities) {
			entities.add(entity);
			Collections.sort(entities);
		}
	}
	
	public void scheduleEntityTick(Tickable entity) {
		entitiesToTick.add(entity);
	}

	public void mouseMoved(int x, int y) {
		Vec2D perspectivePos = viewport.perspectiveToPosition(x, y);
		mouseBox.setPosition(perspectivePos.x, perspectivePos.y);
		if (player.heldStructure != null) {
			player.heldStructure.xRot = x;
			player.heldStructure.yRot = y;
		}
		if (MiniConquest.game.inputManager.isButtonDown(MouseEvent.BUTTON1)) {
			if(x<clickX) {
				player.selection.x = x;
				player.selection.width = clickX - x;
			} else {
				player.selection.x = clickX;
				player.selection.width = x - clickX;
			}
			if(y<clickY) {
				player.selection.y = y;
				player.selection.height = clickY - y;
			} else {
				player.selection.y = clickY;
				player.selection.height = y - clickY;
			}
			Rectangle selectionRect = (Rectangle) player.selection.clone();
			selectionRect.setLocation(selectionRect.x+viewport.viewOffsetX, selectionRect.y+viewport.viewOffsetY);
			synchronized (player.entitiesSelected) {
				player.entitiesSelected.clear();
				synchronized (entities) {
					for (Entity entity : entities) {
						if(!(entity instanceof Tangible))
							continue;
						Vec2D entityPos = viewport.positionToPerspective(entity.x, entity.y);
						if(selectionRect.contains(entityPos.x, entityPos.y))
							player.entitiesSelected.add(entity);
					}
				}
			}
		} else if (MiniConquest.game.inputManager.isButtonDown(MouseEvent.BUTTON3) && MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_CONTROL)) {
			viewport.rotate(x-mouseX, y-mouseY);
		}
		mouseX = x;
		mouseY = y;
	}
	
	public void mousePressed(int button, int x, int y) {
		Vec2D clickPos = viewport.perspectiveToPosition(x, y);
		if (button == MouseEvent.BUTTON1) { //TODO: Consider switch.
			player.entitiesSelected.clear();
			if (player.heldStructure == null) {
				//TODO: Click select units.
			} else { //TODO: Check if structure can be placed here.
				Entity nextStructure = player.heldStructure.clone();
				player.heldStructure.world = this;
				player.heldStructure.setPosition(clickPos);
				joinEntity(player.heldStructure);
				player.heldStructure = MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_SHIFT)?nextStructure:null;
				//TODO: Get rid of it (control gui/esc etc.).
			}
		} else if (button == MouseEvent.BUTTON3) {
			if(MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_CONTROL))
				return;
			for (Entity entity : player.entitiesSelected) {
				if (player.canMove(entity))
					if (entity instanceof EntityLiving)
						((EntityLiving) (entity)).setPathTo(clickPos.x, clickPos.y);
			}
		}
		mouseX = clickX = x;
		mouseY = clickY = y;
	}

	public void mouseReleased(int button, int x, int y) {
		if (button == MouseEvent.BUTTON1) {
			player.selection.setSize(-1, -1);
		}
	}
	
	public void keyPressed(int keyCode, char keyChar) {
	}
	
	public void keyReleased(int keyCode, char keyChar) {
	}
	
	public void tick() {
		//TODO: Optimise this.
		double cosVA = FastMath.cos((float) Math.toDegrees(viewport.viewYaw));
		double sinVA = FastMath.sin((float) Math.toDegrees(viewport.viewYaw));
		//TODO: Consider moving this to render tick.
		boolean hasPanned = false;
		if(MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_W)) {
			viewport.pan((int) (5*sinVA), (int) (-5*cosVA));
			hasPanned = true;
		} if(MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_S)) {
			viewport.pan((int) (5*-sinVA), (int) (5*cosVA));
			hasPanned = true;
		} if(MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_A)) {
			viewport.pan((int) (5*-cosVA), (int) (-5*sinVA));
			hasPanned = true;
		} if(MiniConquest.game.inputManager.isKeyDown(KeyEvent.VK_D)) {
			viewport.pan((int) (5*cosVA), (int) (5*sinVA));
			hasPanned = true;
		}
		if (hasPanned) {
			mouseMoved(mouseX, mouseY);
		}
		
		for (int i = 0; i < entitiesToTick.size(); i++) {
			Tickable entity = entitiesToTick.poll();
			if (entity != null)
				entity.tick();
		}
		
		physEngine.tick();
		
		synchronized (entities) {
			for (Entity entity : entities)
				entity.updateRotatedPostion(viewport.viewYaw);
			Collections.sort(entities);
		}
	}
	
	@Override
	public void render(Graphics2D gfx) {
		gfx.setColor(TileType.abyss.color);
		gfx.fillRect(0, 0, MiniConquest.screenDims.width+2, MiniConquest.screenDims.height+2);
		AffineTransform tempTrans = gfx.getTransform();
		gfx.translate(-viewport.viewOffsetX, -viewport.viewOffsetY);
		gfx.translate(viewport.getViewCentreX(), viewport.getViewCentreY());
		gfx.scale(1, viewport.viewPitch*0.5);
		gfx.rotate(-viewport.viewYaw);
		gfx.translate(-viewport.getViewCentreX(), -viewport.getViewCentreY());
		terrain.render(gfx);
		synchronized(player.entitiesSelected) {
			for (Entity entity : player.entitiesSelected)
				((Tangible) entity).getBound().render(gfx);
		}
		gfx.setTransform(tempTrans); //It sets a copy, not instance.
		gfx.translate(-viewport.viewOffsetX, -viewport.viewOffsetY);
		synchronized(entities) {
			for (Entity entity : entities)
				entity.render(gfx);
		}
		gfx.setTransform(tempTrans);
		gfx.setColor(new Color(0xFF, 0xFF, 0x00, 0xFF));
		gfx.draw(player.selection);
		gfx.setColor(new Color(0xFF, 0xFF, 0x00, 0x55));
		gfx.fill(player.selection);
	}
}