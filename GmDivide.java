public class GmDivide extends GeneMethod{

	@Override
	String getName() {
		return "divide";
	}

	@Override
	public void printTotalStats() {
	}

	@Override
	int getArity() {
		return 2;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {
		int a = geneReader.getNexInt();
		int b = geneReader.getNexInt();
		if( b == 0 ) return 0;
		return a / b;
	}
}