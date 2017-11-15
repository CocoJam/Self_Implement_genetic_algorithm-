import java.util.Random;

/**
 * Created by ljam763 on 16/11/2017.
 */
public class Genes {

    private byte[] baseSequence;
    private Random random = new Random();


    public byte[] getBaseSequence() {
        return baseSequence;
    }

    public void setBaseSequence(byte[] baseSequence) {
        this.baseSequence = baseSequence;
    }

    public Genes(int baseSize) {
        this.baseSequence = new byte[baseSize];
        random.nextBytes(baseSequence);
        for (byte b : baseSequence) {
            System.out.println(b);
        }
    }

    public double geneMatchingBasedOnEuclideanDistance(Genes target){
        byte[] targetByte = target.getBaseSequence();
        double differencesSummation = 0.0;
        for (int i = 0; i < targetByte.length; i++) {
            differencesSummation += targetByte[i] - baseSequence[i];

        }
        differencesSummation = Math.abs(differencesSummation);
        differencesSummation = Math.sqrt(differencesSummation);
        return differencesSummation;
    }

    public static void main(String[] args) {
        Genes genes = new Genes(20);

    }
}
