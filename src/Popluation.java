import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ljam763 on 16/11/2017.
 */
public class Popluation {
    private int popNumber;
    //    private synchronized ArrayList<Individual> individualList = new ArrayList<>();
    private List<Individual> individualList = Collections.synchronizedList(new ArrayList<Individual>());
    private Genes targetGene;
    private IndividualComparator individualComparator = new IndividualComparator();
    private Thread MutationAndComparionsthread;

    public int getPopNumber() {
        return popNumber;
    }

    public void setPopNumber(int popNumber) {
        this.popNumber = popNumber;
    }

    public List<Individual> getIndividualList() {
        return individualList;
    }

    public void setIndividualList(ArrayList<Individual> individualList) {
        this.individualList = individualList;
    }


    public Popluation(int popNumber, Genes targetGene) {
        this.popNumber = popNumber;
        this.targetGene = targetGene;
        generatingPopluation();
        threadStarting(targetGene);
    }

    private void threadStarting(Genes targetGene) {
//        CrossOverThread();
//        mutationThread(targetGene);
        MutationAndComparsionThread(targetGene);
    }

    private void Checker(Genes targetGene) {

        Individual individual = individualList.get(0);


        for (Individual individual1 : individualList) {
            if (identicalGene(individual1.getCurrentBestGene())) {

                individual = individual1;

                System.out.println(individual.getFitness());
                for (byte b : individual.getCurrentBestGene().getBaseSequence()) {
                    System.out.print(b + " ");
                }
                System.out.println();
                for (byte b : targetGene.getBaseSequence()) {
                    System.out.print(b + " ");
                }
                try {
                    if (MutationAndComparionsthread.isAlive()) {
                        System.out.println("interrupt");
                        MutationAndComparionsthread.interrupt();
                        break;
                    }
                } catch (NullPointerException e) {
//                    System.out.println(e);
//                    MutationAndComparionsthread.stop();
                    System.exit(0);
                }

            }
        }

    }

    private boolean identicalGene (Genes genes){
        for (int i = 0; i < genes.getBaseSequence().length; i++) {
            if (genes.getBaseSequence()[i] != targetGene.getBaseSequence()[i]){
                return false;
            }
        }
        return true;
    }

    private void MutationAndComparsionThread(Genes targetGene) {
        Thread MutationAndComparionsthread = new Thread(new Runnable() {
            @Override
            public void run() {
                int turnOverRate = 0;
                while (!Thread.currentThread().isInterrupted()) {
                    turnOverRate++;
                    //Mutation
//                    System.out.println("Mutation");
                    for (Individual individual : individualList) {
                        individual.mutations();
                        individual.fitnessAssessment(targetGene);
                    }
                    //CrossOver
//                    IndividualComparator individualComparator = new IndividualComparator();
                    Collections.sort(individualList, individualComparator);
                    Checker(targetGene);
                    int currentListSize = individualList.size();
//                    System.out.println("size "+ currentListSize);
                    for (int i = 0; i < currentListSize - 1; i++) {
                        Individual individual = individualList.get(i).crossOver(individualList.get(i + 1));
                        individualList.add(individual);
                    }
                    Individual individual = individualList.get(currentListSize - 1).crossOver(individualList.get(0));
                    individualList.add(individual);
                    Collections.sort(individualList, individualComparator);
                    Checker(targetGene);
                    for (int i = individualList.size() - 1; i >= currentListSize; i--) {
//                        System.out.println(i);
                        individualList.remove(i);
                    }
                    printPoplationFitness();
                    Checker(targetGene);
                    System.out.println("The given turnover rate: "+turnOverRate);
//                    System.out.println("size "+ individualList.size());
                }
            }
        });
        MutationAndComparionsthread.start();
    }

    private void CrossOverThread() {
        Thread threadComparsion = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("CrossOver");
//                    Collections.sort(individualList, new IndividualComparator());
                    int currentListSize = individualList.size();
//                    System.out.println("size "+ currentListSize);
                    for (int i = 0; i < currentListSize - 1; i++) {
                        Individual individual = individualList.get(i).crossOver(individualList.get(i + 1));
                        individualList.add(individual);
                    }
                    Individual individual = individualList.get(currentListSize - 1).crossOver(individualList.get(0));
                    individualList.add(individual);
                    Collections.sort(individualList, individualComparator);
                    for (int i = individualList.size() - 1; i >= currentListSize; i--) {
//                        System.out.println(i);
                        individualList.remove(i);
                    }
                    Collections.sort(individualList, individualComparator);
                    printPoplationFitness ();
                }
            }
        });
        threadComparsion.start();
    }

    private void mutationThread(Genes targetGene) {
        Thread threadMutation = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Mutation run");
                while (true) {
                    System.err.println("Mutation");
                    for (Individual individual : individualList) {
                        individual.mutations();
                        individual.fitnessAssessment(targetGene);
                    }
                    Collections.sort(individualList, individualComparator);
                }
            }
        });
        threadMutation.start();
    }

    public void generatingPopluation() {
        for (int i = 0; i < popNumber; i++) {
            Individual individual = new Individual(targetGene);
            individualList.add(individual);
        }
    }

    public void printPoplationFitness() {
        System.out.println("printing");
        for (Individual individuals : individualList) {
            System.out.println(individuals.getFitness());
        }
    }

    public static void main(String[] args) {
        Genes targetGene = new Genes(5);
        Popluation popluation = new Popluation(5, targetGene);
    }

}
