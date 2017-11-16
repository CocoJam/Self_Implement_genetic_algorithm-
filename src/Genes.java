import java.util.Arrays;
import java.util.Random;

/**
 * Created by ljam763 on 16/11/2017.
 */
public class Genes {

    private byte[] baseSequence;
    private Random random = new Random();
    private double fitness = 0;

    public double getFitness() {
        return fitness;
    }

    public byte[] getBaseSequence() {
        return baseSequence;
    }

    public void setBaseSequence(byte[] baseSequence) {
        this.baseSequence = baseSequence;
    }

    public Genes(int baseSize) {
        this.baseSequence = new byte[baseSize];
        random.nextBytes(baseSequence);
    }

    public Genes(byte[] gene){
        this.baseSequence = gene;

    }

    public double geneMatchingBasedOnEuclideanDistance(Genes target) {
        byte[] targetByte = target.getBaseSequence();
        double differencesSummation = 0.0;
        for (int i = 0; i < targetByte.length; i++) {
            differencesSummation += targetByte[i] - baseSequence[i];

        }
        differencesSummation = Math.abs(differencesSummation);
        differencesSummation = Math.sqrt(differencesSummation);
        fitness = differencesSummation;
        return differencesSummation;
    }

    public Genes crossOver(Genes other) {
        int numberOfCross = (int) (Math.random() * 3 + 1);
        int[] randomPosition = new int[numberOfCross];
        for (int i : randomPosition) {
            i = (int) (Math.random() * this.getBaseSequence().length);
        }
        Arrays.sort(randomPosition);
        boolean turnOver = true;
        byte[] sampling = new byte[baseSequence.length];
        for (int j = 0; j < randomPosition[0]; j++) {
            sampling[j] = this.getBaseSequence()[j];
        }
        for (int i = 1; i < numberOfCross-1; i++) {
                for (int j = randomPosition[i]; j < randomPosition[i+1]; j++) {
                    if (turnOver) {
                    sampling[j] = this.getBaseSequence()[j];}
                    else {
                        sampling[j] =other.getBaseSequence()[j];
                    }
                }
            turnOver = !turnOver;
        }
        return new Genes(sampling);
    }

    public static void main(String[] args) {
        Genes genes = new Genes(20);

    }
}
