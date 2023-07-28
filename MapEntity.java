public abstract class MapEntity {

	protected int positionX;
	protected int positionY;
	//protected Map map;
	protected MapLayer mapLayer;

	public abstract void Tick();

	public void setMapLayer( MapLayer layer ) {
		this.mapLayer = layer;
	}

	public void setPosition( int x, int y ) {
		this.positionX = x;
		this.positionY = y;
	}

	public abstract float getDrawingValue();
}
