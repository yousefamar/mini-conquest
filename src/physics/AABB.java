package physics;

import java.awt.Color;
import java.awt.Graphics2D;

import utils.FastMath;
import utils.Vec2D;

public class AABB extends Bound {

	public int x, y;
	public int width, height;
	
	public AABB(int cenX, int cenY, int width, int height) {
		this.width = width;
		this.height = height;
		this.setCentre(cenX, cenY);
	}

	public void setCentre(int cenX, int cenY) {
		this.x = cenX - (width/2);
		this.y = cenY - (height/2);
	}

	@Override
	public boolean collidesWithBound(Bound bound) {
		if (bound instanceof AABB) {
			AABB aabb = (AABB) bound;
			int minX1 = x, minX2 = aabb.x;
			int maxX1 = x + width, maxX2 = aabb.x + aabb.width;
			int minY1 = y, minY2 = aabb.y;
			int maxY1 = y + height, maxY2 = aabb.y + aabb.height;

			return !(maxX1 < minX2 || minX1 > maxX2 || maxY1 < minY2 || minY1 > maxY2);
		}
		
		return false; //TODO: Think this through.
	}

	@Override
	public Vec2D getProjectionVectorWithBound(Bound bound) {
		if (bound instanceof AABB) {
			AABB aabb = (AABB) bound;
			int minX1 = x, minX2 = aabb.x;
			int maxX1 = x + width, maxX2 = aabb.x + aabb.width;
			int minY1 = y, minY2 = aabb.y;
			int maxY1 = y + height, maxY2 = aabb.y + aabb.height;

			if (maxX1 < minX2 || minX1 > maxX2 || maxY1 < minY2 || minY1 > maxY2)
				return null;
			
			int	projX = (maxX2 < maxX1)?(maxX2 - minX1):(minX2 - maxX1);
			int projY = (maxY2 < maxY1)?(maxY2 - minY1):(minY2 - maxY1);
			if (FastMath.abs(projX)<FastMath.abs(projY)) {
				projY = 0;
			} else {
				projX = 0;
			}
			
			return new Vec2D(projX, projY);
		}
		
		return null; //TODO: Think this through.
	}

	@Override
	public void render(Graphics2D gfx) {
		gfx.setColor(Color.ORANGE);
		gfx.drawRect(x, y, width, height);
	}
}
