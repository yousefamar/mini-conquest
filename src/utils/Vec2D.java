package utils;

public class Vec2D {

	public int x;
	public int y;

	public Vec2D(int x, int y) {
		setVec(x, y);
	}

	public void setVec(Vec2D vec) {
		setVec(vec.x, vec.y);
	}
	
	public void setVec(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vec2D translate(Vec2D vec) {
		return this.translate(vec.x, vec.y);
	}

	public Vec2D translate(int x, int y) {
		this.setVec(this.x + x, this.y + y);
		return this;
	}
	
	public Vec2D rotateZ(double theta) {
		float ct = FastMath.cos((float) Math.toDegrees(theta));
		float st = FastMath.sin((float) Math.toDegrees(theta));
		this.setVec((int) (ct*x-st*y), (int) (st*x+ct*y));
		return this; 
	}

	public Vec2D scale(float amount) {
		this.setVec((int) (x*amount), (int) (y*amount));
		return this;
	}
	
	public Vec2D scaleXY(float xAmount, float yAmount) {
		this.setVec((int) (x*xAmount), (int) (y*yAmount));
		return this;
	}
	
	public Vec2D scaleX(float amount) {
		this.setVec((int) (x*amount), y);
		return this;
	}

	public Vec2D scaleY(float amount) {
		this.setVec(x, (int) (y*amount));
		return this;
	}
	
	/*public Vec2D normalise() {
		float mag = Geom.mag(this);
		this.setVec(x/mag, y/mag);
		return this;
	}*/

	public Vec2D reset() {
		this.setVec(0, 0);
		return this;
	}
	
	public String toString() {
		return "("+x+", "+y+")";
	}
}
