import processing.core.PApplet;

public class Main extends PApplet {

	public static void main(String[] args) {
		System.out.println("args.length: " + args.length);
		/// print all args
		for( int i = 0; i < args.length; i++ ) {
			System.out.println("arg[" + i + "]: " + args[i]);
		}
		PApplet.main("Main");
	}


	Map map;
	GeneReader gr;
	Statistics stats;

	int creaturesSpawned = 0;

	public void settings() {

		size(1300, 1000);

		map = new Map( this, 650,500 );
		stats = new Statistics();
		gr = new GeneReader(this, map, stats);

		// init food items
		for( int i = 0; i < 25; i++) {
			map.foodLayer.addEntity(new FoodItem((int)(random(1)*256)));
		}
		// pre-age food items
		for( int i = 0; i < 14800; i++) {
			map.foodLayer.Tick();
		}

		// init creatures
		for( int i = 0; i < 1800; i++) {

		}
	}


	public void setup() {
		// noLoop();
		surface.setTitle("cevo");
		//surface.setResizable(true);
	}

	public void draw() {

		stats.reset();

		map.Tick();
		map.drawMap(this.g);


		stats.printTotalAverages();
		//gr.printTotalStatistics();

		//pushStyle();
		//fill(255);
		//text("creatures: " + map.creatureLayer.entityList.size(), 50, 50);
		//popStyle();

		surface.setTitle("cevo - " + "creatures: " + map.creatureLayer.entityList.size());

		if( creaturesSpawned < Settings.initialCreatureCount ) {
			map.creatureLayer.addNewCreature(null,0);
			creaturesSpawned++;
		}
	}

	public void keyPressed() {
		if( key == 'c' ) {
			map.creatureLayer.addNewCreature(null,0);
		}
	}
}
