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
		for( int i = 0; i < 200; i++) {
			map.foodLayer.addEntity(new FoodItem((int)(random(1)*256)));
		}


		// init creatures
		for( int i = 0; i < 40; i++) {
			map.creatureLayer.addNewRandomCreature();
			/*
			byte[] bytes = new byte[(int)random(1,20000)];
			for( int g = 0; g < bytes.length; g++ ) {
				bytes[g] = (byte)random(0,255);
			}
			Genome g = new Genome(bytes);

			map.creatureLayer.addEntity( new Creature(g,gr,5000) );
			*/
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

		gr.printTotalStatistics();
	}
}
