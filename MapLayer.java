import java.util.ArrayList;

import static java.lang.Math.cos;
import static java.lang.Math.random;

public class MapLayer {

	int layerDrawingHue = 0;
	int getLayerDrawingSaturation = 0;

	protected Map map;

	MapEntity[][] entities;
	ArrayList<MapEntity> entityList = new ArrayList<MapEntity>();

	public MapLayer( Map map, int hue, int brightness) {
		this.map = map;
		this.layerDrawingHue = hue;
		this.getLayerDrawingSaturation = brightness;
		entities = new MapEntity[map.getWidth()][map.getHeight()];
	}

	public void Tick() {
		MapEntity[] elist = entityList.toArray(new MapEntity[entityList.size()]);
		for ( MapEntity e : elist ) {
			e.Tick();
		}
	}



	public void addEntity( MapEntity entity ) {
		// add entity to a random location
		int x = (int) (random() * map.getWidth());
		int y = (int) (random() * map.getHeight());
		addEntity(entity, x, y);
	}

	public boolean addEntity( MapEntity entity, int x, int y ) {

		x = x % (map.getWidth()-1);
		y = y % (map.getHeight()-1);

		if( x < 0 ) x = map.getWidth() + x;
		if( y < 0 ) y = map.getHeight() + y;

		if( entities[x][y] != null ) {
			//System.out.println("Error: attempted to add entity to occupied location");
			return false;
		}

		entities[x][y] = entity;
		entityList.add(entity);
		entity.setPosition(x, y);
		entity.setMap( map, this);
		return true;
	}

	// move an entity to a new location (wrapping around map coordinates), return the resulting movement cost ()
	public void moveEntity( MapEntity e, int x, int y ) {

		// wrap around map coordinates
		int newX = map.wrapX(e.positionX + x);
		int newY = map.wrapY(e.positionY + y);

		// if the new location is occupied, add the occupied cost factor
		if( entities[newX][newY] != null && entities[newX][newY] != e) {
			return;
		}
		else {
			entities[e.positionX][e.positionY] = null;
			entities[newX][newY] = e;
			e.setPosition(newX, newY);
			return;
		}
	}

	public void removeEntity(MapEntity entity) {

		entities[entity.positionX][entity.positionY] = null;
		entityList.remove(entity);
	}
}
