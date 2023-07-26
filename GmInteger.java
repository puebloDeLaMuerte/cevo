import java.nio.ByteBuffer;

public class GmInteger extends GeneMethod{
    @Override
    String getName() {
        return "integer";
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
        int num = ByteBuffer.wrap(byteArray).getInt();
        //System.out.print("buildInt: " + num);
        return num;
    }
}

