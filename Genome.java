public class Genome {

	private byte[] genes;

	private int genePointer;

	public Genome(byte[] genes) {
		this.genes = genes;
	}

	public void reset() {
		genePointer = 0;
	}

	public byte first() {
		genePointer = 0;
		byte b =  genes[genePointer];
		genePointer++;
		return b;
	}

	public byte next() {
		byte b = genes[genePointer];
		genePointer++;
		return b;
	}

	public int length() {
		return genes.length;
	}

	public boolean hasNext() {
		return genePointer < genes.length;
	}
}
