import java.util.ArrayList;

public class GeneReader {

	public int verbose = Settings.geneReaderVerbosity;

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
		new GmSenseFood(),
		new GmEat(),
		new GmFitness(),
		new GmTime(),
		new GmStore(),
		new GmLoad(),
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
				gm.printExecutionStats();
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

	public SplitGenesContainer getRandomSubString(Genome g) {

		SplitGenesContainer container = new SplitGenesContainer();
		container.original = g.getGenes();

		ArrayList<Byte> preBytes = new ArrayList<Byte>();

		// skip to random position in genome

		int r = (int)(Math.random()*g.length());
		byte gene = 0x0;
		g.reset();
		for( int i = 0; i < r; i++ ) {
			if(g.hasNext()) {
				gene = g.next();
				preBytes.add(gene);
			}
			else {
				return null;
			}
		}

		// start copying

		ArrayList<Byte> splitBytes = new ArrayList<Byte>();
		//splitBytes.add(gene);

		// find the next valid gene

		GeneMethod m = null;
		while( m == null && g.hasNext() ) {
			m = resolveGeneMethod(gene);
			if( m == null ) {
				gene = g.next();
				splitBytes.add(gene);
			}
		}

		if( m == null ) {
			return null;
		}

		int arity = m.getArity();
		int byteArity = m.getByteArity();


		while( (arity > 0 || byteArity > 0) && g.hasNext() ) {
			gene = g.next();
			splitBytes.add(gene);

			if ( byteArity > 0 ) {
				byteArity--;
			}
			else {
				m = resolveGeneMethod(gene);
				if( m != null ) {
					arity--;
					arity += m.getArity();
					byteArity += m.getByteArity();
				}
			}
		}

		// copy remaining bytes to postBytes
		ArrayList<Byte> postBytes = new ArrayList<Byte>();
		while( g.hasNext() ) {
			postBytes.add(g.next());
		}

		container.pre = container.toByteArray(preBytes);;
		container.split = container.toByteArray(splitBytes);
		container.post = container.toByteArray(postBytes);

		//container.printCompare();

		return container;
	}


	public byte[] crossOverGenes( SplitGenesContainer genes1, SplitGenesContainer genes2 ) {

		byte[] newBytes = new byte[ genes1.pre.length + genes2.split.length + genes1.post.length ];

		// make a new array with genes1.pre + genes2.split + genes1.post
		int i = 0;
		for( byte b : genes1.pre ) {
			newBytes[i] = returnOrMutate(b);
			i++;
		}
		for( byte b : genes2.split ) {
			newBytes[i] = b;
			i++;
		}
		for( byte b : genes1.post ) {
			newBytes[i] = b;
			i++;
		}
		return newBytes;
	}

	public byte returnOrMutate(byte b) {
		if( Math.random() < Settings.mutationProbability ) {
			byte nb = (byte)(Math.random()*256);
			System.out.println("mutating: " + b + " --> " + nb);
			return nb;
		}
		return b;
	}


	public GeneMethod resolveGeneMethod(byte b) {
		if( b < geneMethods.length && b >= 0 ) {
			return geneMethods[b];
		}
		return null;
	}

	public void printTotalStatistics() {
		for(GeneMethod gm : geneMethods) {
			gm.printTotalStats();
		}
	}

}


class SplitGenesContainer {

	public byte[] toByteArray( ArrayList<Byte> bytes ) {
		// turn bytes into byte array
		byte[] byteArray = new byte[bytes.size()];
		for (int i = 0; i < bytes.size(); i++) {
			byteArray[i] = bytes.get(i);
		}
		return byteArray;
	}

	public void printCompare() {

		int offset = pre.length;

		for(int i = 0; i < original.length; i++) {

			System.out.print( String.format("%010d",i) + ":		" );



			System.out.print( String.format("%+04d", original[i]) + "	" );

			if( i < pre.length ) {
				System.out.print( String.format("%+04d", pre[i]) );
			}
			else {
				System.out.print( "		" );
			}

			if( i >= offset && i < offset + split.length ) {

				System.out.print( String.format("%+04d", split[ i - offset ]) );
			}
			else {
				System.out.print( "		" );
			}

			if( i >= offset + split.length ) {
				System.out.print( "    " );
				System.out.print( String.format("%+04d", post[ i - offset - split.length ]) );
			}

			System.out.println();
		}
	}

	public byte[] original;
	public byte[] pre;
	public byte[] split;
	public byte[] post;
}