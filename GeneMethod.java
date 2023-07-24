public abstract class GeneMethod {

	abstract String getName();
	abstract int getArity();

	public abstract int execute(GeneReader geneReader) throws Exception;

}