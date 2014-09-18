package core;

import java.awt.*;
import java.util.LinkedList;

import entities.*;

public class Player {

	public Color color = Color.red; //TODO: Implement color system.
	public Entity heldStructure;
	public Rectangle selection = new Rectangle();
	public LinkedList<Entity> entitiesSelected = new LinkedList<Entity>();
	
	public boolean controls(Entity entity) {
		return ((Tangible) entity).getMass()>=0; //TODO: Implement sides and owned units.
	}
	
	public boolean canMove(Entity entity) {
		return ((Tangible) entity).getMass()>=0; //TODO: Implement sides and owned units.
	}
}