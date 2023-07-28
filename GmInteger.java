import java.nio.ByteBuffer;

public class GmInteger extends GeneMethod{
    @Override
    String getName() {
        return "integer";
    }

    @Override
    public void printTotalStats() {
    }

    @Override
    int getArity() {
        return 4;
    }

    @Override
    public int execute(GeneReader geneReader) throws Exception {

        byte[] byteArray = new byte[] {
                geneReader.getNexByte(),
                geneReader.getNexByte(),
                geneReader.getNexByte(),
                geneReader.getNexByte()
        };

        int num;
        //num = ByteBuffer.wrap(byteArray).getInt();
        num =
            ((byteArray[0] & 0xFF) << 24) |
            ((byteArray[1] & 0xFF) << 16) |
            ((byteArray[2] & 0xFF) << 8) |
            (byteArray[3] & 0xFF);


        //System.out.print("buildInt: " + num);
        return num;
    }
}

