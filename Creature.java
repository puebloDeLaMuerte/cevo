public class Creature extends MapEntity{

	int id;

	private float fitness;
	float maxFitness;
	public Genome genome;
	public GeneReader gr;

	float moveBufferX = 0;
	float moveBufferY = 0;

	public Creature( Genome genome, GeneReader gr, float fitness ) {
		this.genome = genome;
		this.gr = gr;
		this.fitness = fitness;
		this.maxFitness = fitness;
		this.id = (int)(Math.random()*1000000);
	}

	public void Tick() {
		fitness -= 0.1f;
		gr.executeGenome(this);
		checkFitness();
	}

	public float moveCreature(float x, float y) {

		moveBufferX += x;
		moveBufferY += y;

		int mx = 0;
		int my = 0;

		if( Math.abs(moveBufferX) > 1) {
			mx = (int)moveBufferX;
			moveBufferX -= mx;
		}
		if( Math.abs(moveBufferY) > 1) {
			my = (int)moveBufferY;
			moveBufferY -= my;
		}

		float cost =  mapLayer.moveEntity(this, mx, my);
		spendFitness(cost);

		return cost;
	}


	public float eat() {
		float energy = map.eatAt(positionX,positionY);
		fitness += energy;
		return energy - Settings.eatCost;
	}

	public void spendFitness(float cost) {
		fitness -= cost;
	}

	public void checkFitness() {
		if( fitness <= 0 ) {
			System.out.println("creature died: " + id);
			mapLayer.removeEntity(this);
		}
	}


	@Override
	public float getDrawingValue() {
		return fitness / maxFitness;
	}

	public Genome getGenome() {
		return genome;
	}

}
