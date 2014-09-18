package entities.inert;

import java.awt.Graphics2D;
import physics.AABB;
import map.World;
import entities.Entity;
import entities.Tangible;
import gfx.Sprite;

public class Tree extends Entity implements Tangible { //TODO: Restructure and fix interfaces.

	private AABB aabb;
	
	public Tree(World world, int x, int y) {
		super(world, x, y);
		aabb = new AABB(this.x, this.y, 16, 16);
	}

	@Override
	public void render(Graphics2D gfx) {
		Sprite.tree.render(gfx, xRot-16, yRot-30);
	}
	
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		aabb.setCentre(x, y);
	}

	@Override
	public void onCollisionWithEntity(Entity entity) {
	}

	@Override
	public float getMass() {
		return -1;
	}

	@Override
	public AABB getBound() {
		return aabb;
	}
	
	@Override
	public Tree clone() {
		Tree entity = (Tree) super.clone();
		entity.aabb = (AABB) aabb.clone();
		return entity;
	}
}