import processing.core.PApplet;

public class Main extends PApplet {

	public static void main(String[] args) {
		PApplet.main("Main");
	}

	GeneReader gr = new GeneReader();

	public void settings() {

		size(200, 200);
	}

	public void setup() {
		// noLoop();
	}

	public void draw() {

		if( frameCount % 2 == 0) {
			background(255);
			stroke(0);
		}
		else {
			background(0);
			stroke(255);
		}
		line(0, 0, width, height);
		line(0, height, width, 0);

		println("");
		println("");
		println("+----------------------+");
		println("     new genome: ");
		println("+----------------------+");
		println("");

		// make a byte[] with a random length between 1 and 1000, and fill it with random bytes
		byte[] bytes = new byte[(int)random(1,10000)];
		for( int i = 0; i < bytes.length; i++ ) {
			bytes[i] = (byte)random(0,255);
		}

		Genome g = new Genome(bytes);

		gr.executeGenome(g);

		// delay execution by 1 second
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
