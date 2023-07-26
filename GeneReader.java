import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class GeneReader {

	Genome genome;
	int depth = 0;
	ArrayList<String> callstack = new ArrayList<String>();

	// make a dictionary of all the geneMethods (as values) referenced by a byte as a key
	private GeneMethod[] geneMethods = new GeneMethod[] {

		new GmAdd(),
		new GmSubtract(),
		new GmInteger()
	};


	public void executeGenome(Genome genome) {
		this.depth = 0;
		this.genome = genome;
		try {
			while( genome.hasNext() ) {
				int result = getNexInt();
				System.out.println();
				System.out.println("END RESULT: " + result);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.depth = 0;
		this.genome = null;
	}

	// get the next byte from the genome as a raw byte
	public byte getNexByte() {
		if( !genome.hasNext() ) {
			System.out.println("X");
			return 0x0;
		}
		return genome.next();
	}

	// get the next byte from the genome and interpret it as a geneMethod
	public int getNexInt() throws Exception {
		depth++;
		int skips = 0;

		System.out.println();
		for( int i = 0; i < depth; i++ ) {
			System.out.print(".");
		}

		if( !genome.hasNext() ) {
			depth--;
			System.out.print("eog ");
			//System.out.println(" --> " + 0 );
			return 0;
		}

		byte b = genome.next();

		while( b < 0 || b >= geneMethods.length ) {
			if( !genome.hasNext() ) {

				System.out.print("eog ");
				//System.out.println(" --> " + 0 );
				depth--;
				return 0;
			}
			b = genome.next();
			skips++;
			//System.out.print(".");
		}
		//System.out.println("x");

		GeneMethod gm = geneMethods[b];
		System.out.print(gm.getName() + "(");
		int r = gm.execute(this);
		System.out.print(") ");
		System.out.print(" --> " + r );
		System.out.print(" (skips: " + skips + ")");
		depth--;
		return r;
	}




}
