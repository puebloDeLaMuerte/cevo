public class GmAdd extends GeneMethod{
	@Override
	String getName() {
		return "add";
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
		int i1 = geneReader.getNexInt();
		int i2 = geneReader.getNexInt();
		//System.out.print("add: " + i1 + " + " + i2);
		// return geneReader.getNexInt() + geneReader.getNexInt(); // shorter is better
		return	i1 + i2;
	}
}
