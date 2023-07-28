public class FoodItem extends MapEntity {

	private int energy;
	private int maxEnergy = 256;

	public FoodItem( int energy ) {
		this.energy = energy;
	}

	@Override
	public void Tick() {
		if( energy < maxEnergy ) {
			// grow
			if( Math.random() < 0.3 ) {
				energy++;
			}
		}
		else {
			// reproduce
			//System.out.println("reproducing");
			int relX = (int)(Math.random()*3) - 1;
			int relY = (int)(Math.random()*3) - 1;
			mapLayer.addEntity(new FoodItem(1), positionX + relX, positionY + relY);
		}

	}

	public float getEnergy() {
		return energy;
	}

	@Override
	public float getDrawingValue() {
		return energy / 256.0f;
	}
}
