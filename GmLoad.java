public class GmLoad extends GeneMethod{
	@Override
	public void printTotalStats() {

	}

	@Override
	String getName() {
		return "load";
	}

	@Override
	int getByteArity() { return 0 ; }
	@Override
	int getArity() {
		return 1;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {
		int adress = geneReader.getNexInt();
		return geneReader.creature.loadAt(adress);
	}
}
