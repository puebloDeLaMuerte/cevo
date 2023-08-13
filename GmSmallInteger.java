import java.nio.ByteBuffer;

public class GmSmallInteger extends GeneMethod{
	@Override
	String getName() {
		return "smallInt";
	}

	@Override
	public void printTotalStats() {
	}

	@Override
	int getByteArity() { return 1 ; }
	@Override
	int getArity() {
		return 0;
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {

		byte b = geneReader.getNexByte();

		int num;
		//num = ByteBuffer.wrap(byteArray).getInt();
		num = b & 0xFF;

		//System.out.print("buildInt: " + num);
		return num;
	}
}

