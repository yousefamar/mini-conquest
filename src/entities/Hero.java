package entities;

import gfx.Sprite;
import java.awt.Graphics2D;
import physics.AABB;
import map.World;
import map.tiles.TileType;

public class Hero extends EntityLiving {

	//TODO: Think of a better system for managing widths and heights.
	
	public Hero(World world, int x, int y) {
		super(world, x, y);
		aabb = new AABB(this.x, this.y, 24, 24);
	}
	
	public boolean canTraverse(int x, int y) {
		return world.getTile(x, y) == TileType.grass;
	}
	
	@Override
	public void onCollisionWithEntity(Entity entity) {
	}

	@Override
	public float getMass() {
		return 1;
	}

	@Override
	public void render(Graphics2D gfx) {
		//TODO: Animations.
		Sprite.player[1][dirRot].render(gfx, xRot-11, yRot-25);
	}
}