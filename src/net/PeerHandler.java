package net;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;

public class PeerHandler implements Runnable {

	public int id;
	private Socket clientSoc;
	private IOManager serverIO;
	private volatile ObjectInputStream in;
	private volatile ObjectOutputStream out;
	
	private volatile Queue<Packet> packetQueue;
	
	public PeerHandler(int id, Socket clientSoc, IOManager serverIO) throws IOException {
		this.id = id;
		this.clientSoc = clientSoc;
		this.serverIO = serverIO;

		out = new ObjectOutputStream(clientSoc.getOutputStream());
		out.flush();
		in = new ObjectInputStream(clientSoc.getInputStream());
		
		packetQueue = new LinkedList<Packet>();
	}

	public void run() {
		try {
			while (acceptPacket((Packet)in.readObject()));
		} catch (SocketException e) {
			//TODO: Code this in.
			System.out.println("Peer "+id+" has disconnected.");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dcClient();
		}
	}

	private boolean acceptPacket(Packet packet) {
		if(packet == null)
			return false;
		packetQueue.add(packet);
		return true;
	}

	public void sendPacket(Packet packet) {
		try {
			out.writeObject(packet);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void handlePackets() {
		for (int i = 0; i < packetQueue.size(); i++)
			serverIO.packetHandler.handlePacket(packetQueue.remove(), this);
	}
	
	private void dcClient() {
		try {
			in.close();
			out.close();
			clientSoc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			serverIO.onClientDC(this);
		}
	}
	
	public String getInfo() {
		return clientSoc.getInetAddress()+":"+clientSoc.getPort();
	}
}