package com.blastedstudios.thrall.world;

import java.util.Random;

public class Terrain {
	public final TileEnum[][] tiles;
	public final Random random;
	
	public Terrain(long seed){
		random = new Random(seed);
		int width = random.nextInt(50) + 200;
		tiles = new TileEnum[width][width];
		for(int i=0; i<width; i++)
			for(int j=0; j<width; j++){
				tiles[i][j] = TileEnum.DESERT;
				if(random.nextInt(50) == 0)
					tiles[i][j] = TileEnum.values()[random.nextInt(TileEnum.values().length-1) + 1];
			}
	}
}
