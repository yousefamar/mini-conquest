package entities;

import physics.AABB;

public interface Tangible {

	public void onCollisionWithEntity(Entity entity);
	/**
	 * @return Returns a value for mass that affects collision mobility.
	 * A mass of 0 denotes something that can detect collisions but is moved through (e.g. a swarm of bees).
	 * A mass of -1 denotes immobility.
	 */
	public float getMass();
	public AABB getBound();
}