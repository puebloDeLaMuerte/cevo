public class GmSenseFood extends  GeneMethod{
	@Override
	String getName() {
		return "senseFood";
	}

	@Override
	public void printTotalStats() {
	}

	@Override
	int getByteArity() { return 0 ; }
	@Override
	int getArity() {
		return 2;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {
		int x = geneReader.getNexInt();
		int y = geneReader.getNexInt();

		int i = (int)geneReader.map.getFoodAt(x + geneReader.creature.positionX, y + geneReader.creature.positionY);

		return i;
	}
}
