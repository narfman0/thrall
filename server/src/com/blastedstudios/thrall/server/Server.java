package com.blastedstudios.thrall.server;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.blastedstudios.entente.BaseNetwork;
import com.blastedstudios.entente.Host;
import com.blastedstudios.thrall.network.GameplayNetReceiver;
import com.blastedstudios.thrall.network.Messages;
import com.blastedstudios.thrall.ui.overworld.SkillCheckWindow.ISkillCheckListener;
import com.blastedstudios.thrall.world.IEncounterListener;
import com.blastedstudios.thrall.world.World;
import com.blastedstudios.thrall.world.encounter.Encounter;

public class Server extends ApplicationAdapter implements IEncounterListener{
	private static final int PORT = 42567;
	private Host host;
	private World world;
	private GameplayNetReceiver receiver;
	
	@Override public void create () {
		BaseNetwork.registerMessageOrigin(Messages.class);
		host = new Host(PORT);
		world = new World(System.nanoTime(), this);
		receiver = new GameplayNetReceiver(world, host);
	}
	
	@Override public void render() {
		float dt = Gdx.graphics.getDeltaTime();
		try {
			world.render(dt);
			receiver.update(dt);
			Thread.sleep((long)dt);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main (String[] argv) {
		new HeadlessApplication(new Server());
	}

	@Override
	public void triggerEncounter(Encounter encounter) {
		// TODO Auto-generated method stub
	}

	@Override
	public void triggerSkillCheck(ISkillCheckListener listener) {
		// TODO Auto-generated method stub
	}
}
