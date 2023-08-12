import java.util.Hashtable;

public class Creature extends MapEntity{

	int id;
	int timeOfBirth;

	private int nextTimeOfMate;
	private boolean canMate = false;
	private int timesMated = 0;

	Hashtable<Integer, Integer> memory;

	private float fitness;
	float maxFitness;
	public Genome genome;
	public GeneReader gr;

	float moveBufferX = 0;
	float moveBufferY = 0;

	public Creature( Genome genome, GeneReader gr, float fitness, int timeOfBirth) {
		this.genome = genome;
		this.gr = gr;
		this.fitness = fitness;
		this.maxFitness = fitness;
		this.id = (int)(Math.random()*1000000);
		this.timeOfBirth = timeOfBirth;
		this.nextTimeOfMate = timeOfBirth + Settings.creatureMatingAgeThreshold;

		this.memory = new Hashtable<Integer, Integer>();
	}

	public void Tick() {
		fitness -= Settings.tickCost;
		gr.executeGenome(this);
		checkFitness();
		moveCreature();
		checkMatingTime();
	}


	public float addToMoveBuffer(float x, float y) {
		moveBufferX += x;
		moveBufferY += y;

		float cost = x * Settings.movementCost;
		cost += y * Settings.movementCost;
		if( cost < 0 ) cost *= -1;

		return cost;
	}

	public void moveCreature() {

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

		mapLayer.moveEntity(this, mx, my);


		return;
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
			//System.out.println("creature died: " + id + "	after " + (map.time - timeOfBirth) + " ticks" );
			mapLayer.removeEntity(this);
		}
	}

	public void checkMatingTime() {

		if( map.time == nextTimeOfMate ) {
			canMate = true;
		}
	}

	public boolean canMate() {
		return canMate;
	}

	public Genome doMate() {
		canMate = false;
		timesMated++;
		nextTimeOfMate = map.time + (Settings.creatureMatingAgeThreshold / timesMated);
		return genome;
	}


	@Override
	public float getDrawingValue() {
		return fitness / maxFitness;
	}

	public Genome getGenome() {
		return genome;
	}

	public int getFitness() {
		return (int)fitness;
	}

	public float getFitnessForMating() {

		float childFitness = fitness * ( 1 - Settings.mateCostFactor);
		fitness *= Settings.mateCostFactor;
		return childFitness;
	}

	public int storeAt(int adress, int value) {

		memory.put(adress, value);
		return adress;
	}

	public int loadAt(int adress) {
		if( memory.containsKey(adress) ) {
			return memory.get(adress);
		}
		return 0;
	}
}
