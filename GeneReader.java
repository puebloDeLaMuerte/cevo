import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;

public class GeneReader {

	public int verbose = 6;

	Main main;
	Map map;
	Creature creature;
	int depth = 0;
	int maxDepth = 0;
	int totalSkips = 0;

	public GeneReader(Main m, Map map) {
		this.map = map;
		this.main = m;
	}

	ArrayList<String> callstack = new ArrayList<String>();

	// make a dictionary of all the geneMethods (as values) referenced by a byte as a key
	private GeneMethod[] geneMethods = new GeneMethod[] {

		new GmAdd(),
		new GmSubtract(),
		new GmInteger(),
		new GmIfElse(),
		new GmMove(),
		new GmDivide(),
		new GmSenseFood()
	};


	public void reset() {
		this.depth = 0;
		this.maxDepth = 0;
		this.totalSkips = 0;
		//this.creature = creature;

		for( GeneMethod gm : geneMethods ) {
			gm.reset();
		}
	}


	public void executeGenome(Creature creature) {
		this.creature = creature;
		reset();


		// take a timestamp in milliseconds
		long startTime = System.currentTimeMillis();

		try {
			//while( genome.hasNext() ) {
				creature.genome.reset();
				int result = getNexInt();
				if(verbose > 10) System.out.println();
				if(verbose > 10) System.out.println("END RESULT: " + result);
			//}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// take a timestamp in milliseconds
		long endTime = System.currentTimeMillis();

		// calculate the time difference and print it
		double timeDiff = (endTime - startTime) / 1000.0;

		if( verbose > 5 ) {
			System.out.println("*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*");
			System.out.println("max depth: " + maxDepth);
			System.out.println("total skips: " + totalSkips);
			System.out.println("time to execute (s): " + timeDiff);
			for(GeneMethod gm : geneMethods) {
				gm.printStats();
			}
		}

		this.depth = 0;
		this.maxDepth = 0;
		this.totalSkips = 0;
		this.creature = null;
	}

	// get the next byte from the genome as a raw byte
	public byte getNexByte() {
		if( !creature.genome.hasNext() ) {
			if(verbose > 10) System.out.println("X");
			return 0x0;
		}
		return creature.genome.next();
	}

	// get the next byte from the genome and interpret it as a geneMethod
	public int getNexInt() throws Exception {
		depth++;
		this.maxDepth++;
		int skips = 0;

		if(verbose > 10) System.out.println();
		for( int i = 0; i < depth; i++ ) {
			if(verbose > 10) System.out.print(".");
		}

		if( !creature.genome.hasNext() ) {
			depth--;
			if(verbose > 10) System.out.print("eog ");
			//if(verbose) System.out.println(" --> " + 0 );
			return 0;
		}

		byte b = creature.genome.next();

		while( b < 0 || b >= geneMethods.length ) {
			if( !creature.genome.hasNext() ) {

				if(verbose > 10) System.out.print("eog ");
				//if(verbose) System.out.println(" --> " + 0 );
				depth--;
				return 0;
			}
			b = creature.genome.next();
			skips++;
			//if(verbose) System.out.print(".");
		}
		totalSkips += skips;
		//if(verbose) System.out.println("x");

		GeneMethod gm = geneMethods[b];
		if(verbose > 10) System.out.print(gm.getName() + "(");

		int r = gm.execute(this);
		gm.incrementCount();
		if(verbose > 10) System.out.print(") ");
		if(verbose > 10) System.out.print(" --> " + r );
		if(verbose > 10) System.out.print(" (skips: " + skips + ")");
		depth--;
		return r;
	}




}
