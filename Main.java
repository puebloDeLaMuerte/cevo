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

	public void settings() {

		size(1000, 1000);

		map = new Map( this, 400,400 );
		gr = new GeneReader(this, map);

		// init food items
		for( int i = 0; i < 100; i++) {
			map.foodLayer.addEntity(new FoodItem((int)(random(1)*256)));
		}
		// pre-age food items
		for( int i = 0; i < 2800; i++) {
			map.foodLayer.Tick();
		}

		// init creatures
		for( int i = 0; i < 1800; i++) {
			map.creatureLayer.addNewCreature(null,0);
		}
	}


	public void setup() {
		// noLoop();
		surface.setTitle("cevo");
		//surface.setResizable(true);
	}

	public void draw() {

		map.Tick();
		map.drawMap(this.g);

		//gr.printTotalStatistics();

		//pushStyle();
		//fill(255);
		//text("creatures: " + map.creatureLayer.entityList.size(), 50, 50);
		//popStyle();

		surface.setTitle("cevo - " + "creatures: " + map.creatureLayer.entityList.size());
	}

	public void keyPressed() {
		if( key == 'c' ) {
			Creature c = (Creature)map.creatureLayer.entityList.get(0);
			gr.getRandomSubString(c.genome);
		}
	}
}
