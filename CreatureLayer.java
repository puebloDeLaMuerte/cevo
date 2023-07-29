import java.util.ArrayList;

public class CreatureLayer extends MapLayer{


	public CreatureLayer(Map map, int hue, int brightness) {
		super(map, hue, brightness);
	}


	public void checkMating() {

		ArrayList<Creature> matingList = new ArrayList<Creature>();

		for( MapEntity me : entityList ) {
			Creature c = (Creature)me;
			if( c.canMate() ) {
				matingList.add(c);
			}
		}

		if( matingList.size() > 1 ) {

			// find two creatures that are less than Settings.matingMaxDistance apart
			for( int i = 0; i < matingList.size(); i++ ) {
				for( int j = i+1; j < matingList.size(); j++ ) {

					Creature c1 = matingList.get(i);
					Creature c2 = matingList.get(j);

					float dist = map.main.dist(c1.positionX, c1.positionY, c2.positionX, c2.positionY);
					if( dist < Settings.matingMaxDistance ) {
						System.out.println("mating!");

						SplitGenesContainer genes1 = null;
						SplitGenesContainer genes2 = null;
						while( genes1 == null  ) {
							genes1 = map.main.gr.getRandomSubString(c1.doMate());
						}
						while (genes2 == null) {
							genes2 = map.main.gr.getRandomSubString(c2.doMate());
						}

						byte[] newBytes = map.main.gr.crossOverGenes(genes1, genes2);
						float newFittness = c1.getFitnessForMating() + c2.getFitnessForMating();
						addNewCreature(newBytes, newFittness);
					}
				}
			}
		}
	}


	public void addNewCreature(byte[] newGenes, float initialFitness ) {

		byte[] bytes;

		if( newGenes == null ) {
			bytes = new byte[(int)map.main.random(Settings.creatureInitialGenomeMinLen,Settings.creatureInitialGenomeMaxLen)];
			for( int g = 0; g < bytes.length; g++ ) {
				bytes[g] = (byte)map.main.random(0,255);
			}
		}
		else {
			bytes = newGenes;
		}
		Genome g = new Genome(bytes);

		if (initialFitness == 0) {
			initialFitness = Settings.creatureInitialFitness;
		}


		Creature c = new Creature(g, map.main.gr, initialFitness, map.getTime() );
		addEntity(c);
	}


	// override removeEntity to remove from creature list
	@Override
	public void removeEntity(MapEntity e) {
		super.removeEntity(e);
		//addNewCreature();
	}
}
