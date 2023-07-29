public class GmStore extends GeneMethod{
	@Override
	public void printTotalStats() {

	}

	@Override
	String getName() {
		return "store";
	}

	@Override
	int getByteArity() { return 0 ; }
	@Override
	int getArity() {
		return 2;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {
		int adress = geneReader.getNexInt();
		int value = geneReader.getNexInt();
		return geneReader.creature.storeAt(adress, value);
	}
}
