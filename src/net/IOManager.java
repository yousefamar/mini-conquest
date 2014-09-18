package net;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

import core.MiniConquest;

public class IOManager {
	
	private MiniConquest game;
	private ArrayList<PeerHandler> peers = new ArrayList<PeerHandler>();

	public PacketHandler packetHandler;

	public IOManager(MiniConquest game) {
		this.game = game;
		packetHandler = new PacketHandler(game);
	}
	
	public void hostGame(final int playerNum) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					int port = 8000;
					System.out.println("Waiting for "+playerNum+" players to connect.");
					ServerSocket s = new ServerSocket(port);
					String[] peerInfo = new String[playerNum];
					//TODO: Is this safe?
					for (int id = 0; id < playerNum; id++) {
						PeerHandler peer = new PeerHandler(id, s.accept(), IOManager.this);
						peers.add(peer);
						System.out.println("Client "+id+" has joined the game.");
						new Thread(peer, "Peer"+id+" Thread").start();
						peerInfo[id] = peer.getInfo();
						sendPacketToAll(new PacketPeerList(peerInfo));
					}
				} catch (IOException e) {
					game.catchException(e);
				}
				
			}
		}, "Peer Connect Thread").start();
		joinGame("localhost", 8000);
	}
	
	public void joinGame(String host, int port) {
		try {
			peers.add(new PeerHandler(0, new Socket(host, port), this));
			System.out.println("Connected to host.");
			new Thread(peers.get(0), "Peer0 Thread").start();
		} catch (UnknownHostException e) {
			System.err.println("Could not resolve host.");
			//TODO: Handle exception.
			System.exit(1);
		} catch (ConnectException e) {
			System.err.println("Could not connect to host.");
			//TODO: Handle exception.
			System.exit(1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handlePackets() {
		for (PeerHandler peer : peers)
			peer.handlePackets();
	}
	
	public void sendPacketToAll(Packet packet) {
		for (PeerHandler peer : peers)
			if(peer!=null)
				peer.sendPacket(packet);
	}
	
	public void onClientDC(PeerHandler peer) {
		peers.set(peer.id, null);
	}
}