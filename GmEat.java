public class GmEat extends GeneMethod
{

	int totalEatPenalty = 0;

	@Override
	String getName() {
		return "eat";
	}

	@Override
	int getByteArity() { return 0 ; }
	@Override
	int getArity() {
		return 0;
	}


	@Override
	public void printTotalStats() {
		System.out.println("total eat penalty: " + totalEatPenalty);
	}

	@Override
	public int execute(GeneReader geneReader) throws Exception {

		float penalty = geneReader.creature.eat();

		totalEatPenalty += penalty;
		return (int)penalty;
	}
}
