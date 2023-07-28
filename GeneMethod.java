public abstract class GeneMethod {

	private int useageCounter;

	public void reset() {
		useageCounter = 0;
	}
	public void incrementCount() {
		useageCounter++;
	}

	public void printExecutionStats() {
		System.out.println(getName() + ": " + useageCounter);
	}

	public abstract void printTotalStats();

	abstract String getName();
	abstract int getArity();

	public abstract int execute(GeneReader geneReader) throws Exception;

}