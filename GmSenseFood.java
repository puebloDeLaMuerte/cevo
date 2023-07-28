public class GmSenseFood extends  GeneMethod{
	@Override
	String getName() {
		return "senseFood";
	}

	@Override
	int getArity() {
		return 2;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {
		int x = geneReader.getNexInt();
		int y = geneReader.getNexInt();

		int i = (int)geneReader.map.getFoodAt(x + geneReader.creature.positionX, y + geneReader.creature.positionY);
		if( i > 0 ) {
			System.out.println("food seen: " + i);
		}
		return i;
	}
}
