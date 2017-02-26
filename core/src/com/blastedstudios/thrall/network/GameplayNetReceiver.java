package com.blastedstudios.thrall.network;

import java.net.Socket;
import java.util.List;

import com.blastedstudios.entente.BaseNetwork;
import com.blastedstudios.thrall.world.World;
import com.google.protobuf.Message;

public class GameplayNetReceiver{
	private final World world;
	public final BaseNetwork network;
	
	public GameplayNetReceiver(World world, BaseNetwork network){
		this.world = world;
		this.network = network;
	}
	
	public void update(float dt){
		network.update();
	}
	
	public void send(Message message, List<Socket> destinations) {
		if(network != null)
			network.send(message, destinations);
	}
	
	public void send(Message message){
		send(message, null);
	}
}
