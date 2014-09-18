package net;

public class PacketPeerList extends Packet {
	private static final long serialVersionUID = -2970175121302821754L;

	public String[] peerInfo;
	
	public PacketPeerList(String[] peerInfo) {
		this.peerInfo = peerInfo;
	}
}