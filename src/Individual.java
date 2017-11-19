import java.util.Comparator;
import java.util.Random;

/**
 * Created by ljam763 on 16/11/2017.
 */
public class Individual {
    private double mutationRate = 0.01;
    private Genes[] gene;
    private double fitness;
    private Genes targetGene;
    private Genes currentBestGene;
    private Random random= new Random();

    public Genes getTargetGene() {
        return targetGene;
    }

    public void setTargetGene(Genes targetGene) {
        this.targetGene = targetGene;
    }

    public Genes getCurrentBestGene() {
        return currentBestGene;
    }

    public void setCurrentBestGene(Genes currentBestGene) {
        this.currentBestGene = currentBestGene;
    }

    public Individual(Genes targetGene) {
        //Everyone has 10 genes, and each genes consist of 20 bases/ byte size
        gene = new Genes[10];
        this.targetGene = targetGene;
        int baseSizenumber = targetGene.getBaseSequence().length;
        for (int i = 0; i < 10; i++) {
            Genes genes1 = new Genes(baseSizenumber);
            gene[i] = genes1;
        }
        fitness = Math.random();

//        mutations();
//        fitnessAssessment(targetGene);

        this.currentBestGene = gene[0];
//        this.run();
    }

    public double getMutationRate() {
        return mutationRate;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public Genes[] getGene() {
        return gene;
    }

    public void setGene(Genes[] gene) {
        this.gene = gene;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void fitnessAssessment(Genes targetGene) {
        // get eval by using the given gene which is 10 for example, then using the number of bases to check for hamming distances.
        Genes lowestNumber = gene[0];
        for (Genes genes : gene) {
            double fitnessNumber = genes.geneMatchingBasedOnEuclideanDistance(targetGene);
            if (isLessInFitness(lowestNumber, genes)) {
                lowestNumber = genes;
//                System.out.println("Changed");
            }
//            System.err.println("Fitness altered and recompute fitness: " + fitnessNumber);
        }
        currentBestGene = lowestNumber;
        fitness = lowestNumber.getFitness();
    }

    public boolean isLessInFitness(Genes current, Genes genes) {
        return current.getFitness() < genes.getFitness();
    }

    public void mutations() {
        double randomChances = (int) (Math.random() * 100) + 1;
//        if (randomChances >= 90) {
        int randomSelectionOfGene = (int) (Math.random() * gene.length);
//            int plusOrMinus = (int) Math.round(Math.random());
//            int radomAmountOfMutation = (int) (Math.random() *10 +1);
        //Could be biopolar or bidirectional
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
//            if (plusOrMinus > 0) {
//                plusOrMinus = radomAmountOfMutation;
//            } else {
//                plusOrMinus = -1 * radomAmountOfMutation;
//            }
        // Selecting a gene from this individual
        byte[] sequenceForMutation = gene[randomSelectionOfGene].getBaseSequence();
        // Selecting the base required to be mutated based on the given gene selected.
        int randomSelectionOfBase = (int) (Math.random() * sequenceForMutation.length);
        sequenceForMutation[randomSelectionOfBase] = bytes[0];
//        }

//        System.err.println("Mutation occured at gene: " + randomSelectionOfBase + " at base number of " + randomSelectionOfBase + " by " + plusOrMinus);
    }

    public Individual crossOver(Individual other) {
        Individual individual = new Individual(targetGene);
        Genes[] genes = new Genes[gene.length];
        for (int i = 0; i < this.getGene().length; i++) {
            genes[i] = this.getGene()[i].crossOver(other.getGene()[i]);
        }
        individual.setGene(genes);
        return individual;
    }

    public void printOutGene (){
        this.fitnessAssessment(targetGene);
        for (Genes genes : this.getGene()) {
            genes.printGene();
        }
        System.out.println();
    }
//    @Override
//    public void run() {
//        // Threaded to view the constant mutations
//        while (true) {
//            double randomChances = (int) (Math.random() * 100) + 1;
//            if (randomChances >= 90) {
//                mutations();
//                fitnessAssessment(targetGene);
////                System.err.println("Mutation occured");
//            }
//        }
//    }


    public static void main(String[] args) {

        Genes targetGene = new Genes(20);
        Individual individual = new Individual(targetGene);
        Individual individual1 = new Individual(targetGene);
        System.out.println("Target Gene");
        targetGene.printGene();
        System.out.println("Individual one");
        individual.printOutGene();
        System.out.println("Individual two");
        individual1.printOutGene();
        Individual individual2 = individual.crossOver(individual1);
        System.out.println("Crossed over");
        individual2.printOutGene();

//        individual.fitnessAssessment(targetGene);
    }


}
