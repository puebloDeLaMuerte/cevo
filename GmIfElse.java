public class GmIfElse extends GeneMethod{

	@Override
	String getName() {
		return "ifelse";
	}

	@Override
	int getArity() {
		return 5;
	}

	/// 1 byte from the geneReader. Take the modulo 6 of that byte. If it's 0, use > if it's 1, use <, if it's 2 use >=, if it's 3 use <=, if it's 4, use == if it's 5,  use !=
	/// after that, read 4 ints from the geneReader. compare the first and second of those ints. if the comparison is true, return the 3rd int taken, if it's false, return the 4th int taken.
	@Override
	public int execute(GeneReader geneReader) throws Exception {

		int mod = geneReader.getNexByte() % 6;
		if( mod < 0 ) mod += 6;

		int c1 = geneReader.getNexInt();
		int c2 = geneReader.getNexInt();
		int m3 = geneReader.getNexInt();
		int m4 = geneReader.getNexInt();

		switch (mod) {
			case 0:
				// >
				if(c1 > c2)
					return m3;
				else
					return m4;

			case 1:
				// <
				if(c1 < c2)
					return m3;
				else
					return m4;

			case 2:
				// >=
				if(c1 >= c2)
					return m3;
				else
					return m4;

			case 3:
				// <=
				if(c1 <= c2)
					return m3;
				else
					return m4;

			case 4:
				// ==
				if(c1 == c2)
					return m3;
				else
					return m4;

			case 5:
				// !=
				if(c1 != c2)
					return m3;
				else
					return m4;
		}

		return 0;
	}
}
