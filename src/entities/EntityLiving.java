package entities;

import physics.AABB;
import utils.Vec2D;
import ai.PathFinder;
import map.World;

public abstract class EntityLiving extends Entity implements Tangible, Tickable { //TODO: Think about if these interfaces are really necessary.

	//TODO: Encapsulate.
	public AABB aabb;
	private PathFinder pathFinder = new PathFinder(this);
	private int pathX, pathY;
	protected int dir = 0, dirRot = 0;
	protected int movSpeed = 4; //TODO: Fix this.
	
	public EntityLiving(World world, int x, int y) {
		super(world, x, y);
		this.x = pathX = ((x>>5)<<5)+16;
		this.y = pathY = ((y>>5)<<5)+16;
		world.scheduleEntityTick(this);
	}
	
	public void setPosition(int x, int y) {
		super.setPosition(x, y);
		aabb.setCentre(x, y);
	}
	
	public void setPathTo(int x, int y) {
		//TODO: Think about if you should schedule a tick on setting a new path.
		pathFinder.generatePathAStar(this.x, this.y, x, y);
		if (pathFinder.hasNextNode()) {
			Vec2D pathVec = pathFinder.nextNode();
			pathX = pathVec.x;
			pathY = pathVec.y;
		}
	}
	
	public boolean canTraverse(int x, int y) {
		return true;
	}
	
	@Override
	public AABB getBound() {
		return aabb;
	}

	@Override
	public void tick() {
		if (x == pathX && y == pathY) {
			if (pathFinder.hasNextNode()) {
				Vec2D pathVec = pathFinder.nextNode();
				pathX = pathVec.x;
				pathY = pathVec.y;
			}
		}
		int movX = 0, movY = 0;
		if (x != pathX || y != pathY) {
			for (int i = 0; i < movSpeed; i++) {
				if (x+movX != pathX)
					movX += pathX>x?1:-1;
				if (y+movY != pathY)
					movY += pathY>y?1:-1;
			}

			//TODO: Find a more efficient way to do this?
			int movDirX = movX>0?1:movX<0?-1:0, movDirY = movY>0?1:movY<0?-1:0;
			switch ((movDirX+1)+(movDirY+1)*3) {
			case 0:
				dir = 3;
				break;
			case 1:
				dir = 4;
				break;
			case 2:
				dir = 5;
				break;
			case 3:
				dir = 2;
				break;
			case 4:
				dir = 0;
				break;
			case 5:
				dir = 6;
				break;
			case 6:
				dir = 1;
				break;
			case 7:
				dir = 0;
				break;
			case 8:
				dir = 7;
				break;
			default:
				dir = 0;
				break;
			}
			setPosition(x+movX, y+movY);
		}
		dirRot =  (((int) (-Math.toDegrees(world.viewport.viewYaw)/45F - 0.5D) & 7)+dir)%8;
	
		world.scheduleEntityTick(this);
	}
}