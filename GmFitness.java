public class GmFitness extends GeneMethod{
	@Override
	public void printTotalStats() {

	}

	@Override
	String getName() {
		return "fitness";
	}


	@Override
	int getByteArity() { return 0 ; }
	@Override
	int getArity() {
		return 0;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {
		return geneReader.creature.getFitness();
	}
}
