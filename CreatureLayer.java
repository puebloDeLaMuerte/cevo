public class CreatureLayer extends MapLayer{


	public CreatureLayer(Map map, int hue, int brightness) {
		super(map, hue, brightness);
	}


	public void addNewRandomCreature() {

		byte[] bytes = new byte[(int)map.main.random(1,80000)];
		for( int g = 0; g < bytes.length; g++ ) {
			bytes[g] = (byte)map.main.random(0,255);
		}
		Genome g = new Genome(bytes);

		Creature c = new Creature(g, map.main.gr, 2000);
		addEntity(c);
	}


	// override removeEntity to remove from creature list
	@Override
	public void removeEntity(MapEntity e) {
		super.removeEntity(e);
		addNewRandomCreature();
	}
}
