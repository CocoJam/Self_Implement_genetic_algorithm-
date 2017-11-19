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
        for (int i = 0; i<baseSize; i++){
            int randomBinary = random.nextInt(2);
            String binaryNumber = Integer.toBinaryString(randomBinary);
            baseSequence[i] = Byte.parseByte(binaryNumber);
        }
//        int randomBinary = random.nextInt();
//        String binaryNumber = Integer.toBinaryString(randomBinary);
//        random.nextBytes(baseSequence);
    }

    public Genes(byte[] gene) {
        this.baseSequence = gene;

    }

    public int geneMatchingHammingDistance (Genes target){
        byte[] targetByte = target.getBaseSequence();
        int fitnessAcc = 0;
        for (int i =0; i< targetByte.length;i++){
            if (targetByte[i] != baseSequence[i]){
                fitnessAcc++;
            }
        }
        return fitnessAcc;
    }

    public double geneMatchingBasedOnEuclideanDistance(Genes target) {
        byte[] targetByte = target.getBaseSequence();
        double fitnessAcc = 0.0;
        for (int i =0; i< targetByte.length;i++){
            if (targetByte[i] != baseSequence[i]){
                fitnessAcc++;
            }
        }
        fitness = fitnessAcc;
        return fitnessAcc;
//        double differencesSummation = 0.0;
//        for (int i = 0; i < targetByte.length; i++) {
//            differencesSummation += targetByte[i] - baseSequence[i];
//        }
//        differencesSummation = Math.abs(differencesSummation);
//        differencesSummation = Math.sqrt(differencesSummation);
//        fitness = differencesSummation;
//        return differencesSummation;
    }

    public Genes crossOver(Genes other) {
        int numberOfCross = 1;

        int randomPosition = (int) (Math.random() * (this.getBaseSequence().length - 1)) + 1;

        byte[] sampling = new byte[baseSequence.length];
        for (int j = 0; j < randomPosition; j++) {
            sampling[j] = this.getBaseSequence()[j];
        }
        for (int j = randomPosition; j < sampling.length; j++) {
            sampling[j] = other.getBaseSequence()[j];
        }
        return new Genes(sampling);

    }

    public String printGene() {
        String geneSequence = "";
        for (byte b : this.baseSequence) {
            System.out.print(b + " ");
            geneSequence += b;
        }
        System.out.println();
        return geneSequence;
    }

    public static void main(String[] args) {
        Genes genes = new Genes(10);
        genes.printGene();
        Genes genes2 = new Genes(10);
        genes2.printGene();
        Genes genes1 = genes.crossOver(genes2);
        genes1.printGene();
        Random random = new Random();
        int randomBinary = random.nextInt(2);
        String binary = Integer.toBinaryString(randomBinary);
        System.out.println(Integer.toBinaryString(randomBinary));
    }
}
