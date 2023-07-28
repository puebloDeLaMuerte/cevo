public abstract class MapEntity {

	protected int positionX;
	protected int positionY;
	//protected Map map;
	protected MapLayer mapLayer;
	protected Map map;

	public abstract void Tick();

	public void setMap( Map map, MapLayer layer ) {
		this.map = map;
		this.mapLayer = layer;
	}

	public void setPosition( int x, int y ) {
		this.positionX = x;
		this.positionY = y;
	}

	public abstract float getDrawingValue();
}
