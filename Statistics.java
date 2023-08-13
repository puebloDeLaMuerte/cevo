import java.text.DecimalFormat;
import java.util.Hashtable;

public class Statistics {

	String separator = "     |  ";

	// make a hashtable that has a key that is a type that derives from GeneMethod and a value that's an integer
	Hashtable<Class<? extends GeneMethod>, Integer> geneMethodCounts = new Hashtable<Class<? extends GeneMethod>, Integer>();
	int totalSkippedGenes = 0;
	int totalDepth = 0;
	int uniqueCreaturesCounted = 0;

	public void reset() {
		uniqueCreaturesCounted = 0;
		geneMethodCounts.clear();
		totalSkippedGenes = 0;
		totalDepth = 0;
	}

	public  void countCreature() {
		uniqueCreaturesCounted++;
	}

	public void coutSkippedGenes(int skips ) {
		totalSkippedGenes += skips;
	}

	public void countDepth( int depth ) {
		totalDepth += depth;
	}

	public void countMethod(GeneMethod gm) {
		Class<? extends GeneMethod> c = gm.getClass();
		if( geneMethodCounts.containsKey(c) ) {
			int count = geneMethodCounts.get(c);
			geneMethodCounts.put(c, count+1);
		} else {
			geneMethodCounts.put(c, 1);
		}
	}

	public void printTotalStats() {
		System.out.println("Total stats:");
		for( Class<? extends GeneMethod> c : geneMethodCounts.keySet() ) {
			System.out.println(c.getName() + ": " + geneMethodCounts.get(c));
		}
	}

	public void printTotalAverages() {

		System.out.println();
		System.out.println();
		System.out.println("Total averages:");
		System.out.println();
		System.out.println("Skipped genes: " + (float)totalSkippedGenes / (float)uniqueCreaturesCounted);
		System.out.println("Depth: " + (float)totalDepth / (float)uniqueCreaturesCounted);

		// print all class names in gemeMethodCounts.keySet() make them take up three tabs of space each
		for( Class<? extends  GeneMethod> c : geneMethodCounts.keySet() ) {
			System.out.print(c.getName() + separator);
		}

		/*
		System.out.println();
		for( Class<? extends  GeneMethod> c : geneMethodCounts.keySet() ) {
			System.out.print("+");
			for( int i = 0; i < c.getName().length()-1; i++ ) {
				System.out.print("-");
			}
			System.out.print(separator);
		}
		*/


		System.out.println();

		// print average useage of methods per creatures
		for( Class<? extends GeneMethod> c : geneMethodCounts.keySet() ) {
			float n = (float)geneMethodCounts.get(c) / (float)uniqueCreaturesCounted;
			// format n to be 4 digits long and have 2 digits after the decimal point. store in a string

			String s = String.format("% 7.2f", n);

			System.out.print( s );
			int blanks = c.getName().length() - s.length() + separator.length();
			for( int i = 0; i < blanks; i++ ) {
				System.out.print(" ");
			}
		}
	}
}
