package net;

import core.MiniConquest;

public class PacketHandler {

	private MiniConquest server;

	public PacketHandler(MiniConquest server) {
		this.server = server;
	}
	
	public void handlePacket(Packet packet, PeerHandler client) {
		if(packet instanceof PacketEntityPos)
			handlePacket((PacketEntityPos) packet);
		else if(packet instanceof PacketPeerList)
			handlePacket((PacketPeerList) packet);
	}

	public void handlePacket(PacketEntityPos packet) {
		System.out.println(packet.entityID+" "+packet.x+" "+packet.y+" "+packet.z);
	}
	
	public void handlePacket(PacketPeerList packet) {
		//server.ioManager.
	}
}