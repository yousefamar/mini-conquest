package utils;

public class Geom {

	public static float dist(Vec2D vec1, Vec2D vec2) {
		return mag(dir(vec1, vec2));
	}
	
	public static Vec2D unit(Vec2D vec) {
		float mag = Geom.mag(vec);
		return new Vec2D((int)(vec.x/mag), (int)(vec.y/mag));
	}

	public static Vec2D add(Vec2D vec1, Vec2D vec2) {
		return new Vec2D(vec1.x + vec2.x, vec1.y + vec2.y);
	}
	
	public static Vec2D sub(Vec2D vec1, Vec2D vec2) {
		return new Vec2D(vec1.x - vec2.x, vec1.y - vec2.y);
	}
	
	public static Vec2D dir(Vec2D vec1, Vec2D vec2) {
		return new Vec2D(vec2.x - vec1.x, vec2.y - vec1.y);
	}

	public static Vec2D scale(Vec2D vec, float amount) {
		return new Vec2D((int)(vec.x*amount), (int)(vec.y*amount));
	}

	public static float dot(Vec2D vec1, Vec2D vec2) {
		return (vec1.x*vec2.x + vec1.y*vec2.y);
	}
	
	public static float mag(Vec2D vec) {
		return (float) Math.sqrt(vec.x*vec.x + vec.y*vec.y);
	}
	
	public static float angle(Vec2D vec1, Vec2D vec2) {
		return (float) Math.acos((dot(vec1, vec2)/(mag(vec1)*mag(vec2))));
	}
}