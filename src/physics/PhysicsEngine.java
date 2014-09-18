package physics;

import java.util.LinkedList;
import map.World;
import utils.*;
import entities.*;

public class PhysicsEngine {
	// TODO: Consider incorporation OBB-AABB collision detection.
	
	private World world;

	public PhysicsEngine(World world) {
		this.world = world;
	}

	public void tick() {
		Object[] entities;
		synchronized (world.entities) {
			if(world.entities.size()<1)
				return;
			entities = world.entities.toArray();
		}
		for (int i = 0; i < entities.length; i++) {
			for (int j = i+1; j < entities.length; j++) {
				Entity entity1 = (Entity) entities[i], entity2 = (Entity) entities[j];
				if(collideEntities(entity1, entity2)) {
					((Tangible) entity1).onCollisionWithEntity(entity2);
					((Tangible) entity2).onCollisionWithEntity(entity1);
				}
			}
		}
	}
	
	private boolean collideEntities(Entity entity1, Entity entity2) {
		if (!(entity1 instanceof Tangible) || !(entity2 instanceof Tangible))
			return false;
		
		float mass1 = ((Tangible) entity1).getMass(), mass2 = ((Tangible) entity2).getMass();
		Bound bound1 = ((Tangible) entity1).getBound(), bound2 = ((Tangible) entity2).getBound();
		
		Vec2D projVec = bound1.getProjectionVectorWithBound(bound2);
		if(projVec == null)
			return false;
		
		float massRatio = (mass1>0&&mass2>0)?mass1/(mass2*2):1;

		if (mass1>0)
			entity1.setPosition(entity1.x+(int)(projVec.x*massRatio), entity1.y+(int)(projVec.y*massRatio));
		if (mass2>0)
			entity2.setPosition(entity2.x-(int)(projVec.x*massRatio), entity2.y-(int)(projVec.y*massRatio)); //TODO: Check imbalanced ratios (1-r).
		return true;
	}

	public LinkedList<Entity> getAABBCollidingEntities(Bound bound) {
		LinkedList<Entity> collidingEntities = new LinkedList<Entity>();
		synchronized (world.entities) {
			for (Entity entity : world.entities) {
				if(!(entity instanceof Tangible))
					continue;
				if(bound.collidesWithBound(((Tangible) entity).getBound()))
					collidingEntities.add(entity);
			}
		}
		return collidingEntities;
	}
}