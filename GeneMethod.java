public abstract class GeneMethod {

	private int useageCounter;

	public void reset() {
		useageCounter = 0;
	}
	public void incrementCount() {
		useageCounter++;
	}

	public void printStats() {
		System.out.println(getName() + ": " + useageCounter);
	}

	abstract String getName();
	abstract int getArity();

	public abstract int execute(GeneReader geneReader) throws Exception;

}