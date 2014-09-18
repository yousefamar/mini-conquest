package net;

import java.io.Serializable;

public class PacketEntityPos extends Packet implements Serializable{
	private static final long serialVersionUID = 5212038685191346522L;
	
	public int entityID, x, y, z;
}
