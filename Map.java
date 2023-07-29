import processing.core.PConstants;
import processing.core.PGraphics;

public class Map {

	int width;
	int height;

	int time = 0;

	Main main;
	MapLayer foodLayer;
	CreatureLayer creatureLayer;

	public Map( Main m, int w, int h ) {
		this.main = m;
		this.width = w;
		this.height = h;

		foodLayer = new MapLayer(this, 110,30);
		creatureLayer = new CreatureLayer(this, 350, 100);
	}

	public void Tick() {
		time++;
		foodLayer.Tick();
		creatureLayer.Tick();
	}

	public PGraphics drawMap(PGraphics graphics ) {

		float cellWidth = (float)graphics.width / width;
		float cellHeight = (float)graphics.height / height;

		graphics.beginDraw();
		graphics.background(0);
		graphics.colorMode(PConstants.HSB, 255);

		graphics.noStroke();
		for ( MapEntity e : foodLayer.entityList ) {
			graphics.fill(foodLayer.layerDrawingHue, foodLayer.getLayerDrawingSaturation, e.getDrawingValue() * 255);
			graphics.rect(e.positionX * cellWidth, e.positionY * cellHeight, cellWidth, cellHeight);
		}

		for ( MapEntity e : creatureLayer.entityList ) {
			graphics.stroke(creatureLayer.layerDrawingHue, creatureLayer.getLayerDrawingSaturation, e.getDrawingValue() * 255);
			graphics.fill(creatureLayer.layerDrawingHue, creatureLayer.getLayerDrawingSaturation, e.getDrawingValue() * 255);
			graphics.rect(e.positionX * cellWidth, e.positionY * cellHeight, cellWidth*2, cellHeight*2);
		}

		graphics.endDraw();

		return graphics;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public float eatAt(int posX, int posY) {

		posX = wrapX(posX);
		posY = wrapY(posY);

		float energy = 0;
		if(foodLayer.entities[posX][posY] != null) {
			FoodItem food = (FoodItem)foodLayer.entities[posX][posY];
			energy = food.getEnergy();
			foodLayer.removeEntity(food);
		}
		return energy;
	}

	public float getFoodAt(int x, int y) {

		x = wrapX(x);
		y = wrapY(y);

		if(foodLayer.entities[x][y] != null) {
			return ((FoodItem)foodLayer.entities[x][y]).getEnergy();
		}
		else return 0;
	}

	public int wrapX(int i) {

		int newX = i % width;
		// Handle wrapping for negative coordinates
		if (newX < 0) {
			newX += width;
		}
		return newX;
	}

	public int wrapY(int i) {

		int newY = i % height;
		// Handle wrapping for negative coordinates
		if (newY < 0) {
			newY += height;
		}
		return newY;
	}

	public int getTime() {
		return time;
	}
}
