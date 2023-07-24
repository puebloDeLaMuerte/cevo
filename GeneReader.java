import java.util.Dictionary;
import java.util.HashMap;

public class GeneReader {

	Genome genome;

	// make a dictionary of all the geneMethods (as values) referenced by a byte as a key
	private GeneMethod[] geneMethods = new GeneMethod[] {

		new GmAdd(),
		new GmSubtract(),
		new GmInteger()
	};


	public void executeGenome(Genome genome) {
		this.genome = genome;
		try {
			while( genome.hasNext() ) {
				int result = getNexInt();
				System.out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.genome = null;
	}

	// get the next byte from the genome as a raw byte
	public byte getNexByte() {
		if( !genome.hasNext() ) {
			return 0x0;
		}
		return genome.next();
	}

	// get the next byte from the genome and interpret it as a geneMethod
	public int getNexInt() throws Exception {

		if( !genome.hasNext() ) {
			return 0;
		}

		byte b = genome.next();

		while( b < 0 || b >= geneMethods.length ) {
			if( !genome.hasNext() ) {

				System.out.println("end of genome");
				return 0;
			}
			b = genome.next();
			System.out.print(". ");
		}

		GeneMethod gm = geneMethods[b];
		return gm.execute(this);
	}




}
