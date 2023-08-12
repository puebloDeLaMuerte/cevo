public class GmMove extends GeneMethod{
	@Override
	String getName() {
		return "move";
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

		// divide x by maximum value for int
		float mx = (float)x / (float)(Integer.MAX_VALUE);
		float my = (float)y / (float)(Integer.MAX_VALUE);

		float penalty = geneReader.creature.addToMoveBuffer(mx, my);
		geneReader.creature.spendFitness(penalty);
		//System.out.println("move: " + mx + ", " + my + " penalty: " + penalty);
		return (int)penalty;
	}
}
