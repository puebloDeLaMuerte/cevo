public class GmSubtract extends GeneMethod{
	@Override
	String getName() {
		return "subtract";
	}

	@Override
	int getArity() {
		return 2;
	}

	@Override
	public int execute( GeneReader geneReader ) throws Exception {
		int i1 = geneReader.getNexInt();
		int i2 = geneReader.getNexInt();
		System.out.println("subtract: " + i1 + " - " + i2);
		// return geneReader.getNexInt() - geneReader.getNexInt(); // shorter is better
		return i1 - i2;
	}
}
