public class Genome {

	private byte[] genes;

	private int genePointer;

	public Genome(byte[] genes) {
		this.genes = genes;
	}

	public byte next() {
		byte b = genes[genePointer];
		genePointer++;
		return b;
	}

	public boolean hasNext() {
		return genePointer < genes.length;
	}
}
