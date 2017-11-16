import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by ljam763 on 16/11/2017.
 */
public class Popluation {
    private int popNumber;
    private ArrayList<Individual> individualList = new ArrayList<>();
    private Genes targetGene;

    public int getPopNumber() {
        return popNumber;
    }

    public void setPopNumber(int popNumber) {
        this.popNumber = popNumber;
    }

    public ArrayList<Individual> getIndividualList() {
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
//        mutationThread(targetGene);
//        ComparsionThread();
        MutationAndComparsionThread(targetGene);
    }

    private void MutationAndComparsionThread(Genes targetGene) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
//                    System.out.println("Mutation");
                    for (Individual individual : individualList) {
                        individual.mutations();
                        individual.fitnessAssessment(targetGene);
                    }
//                    System.out.println("Comparsion");
                    IndividualComparator individualComparator = new IndividualComparator();
                    Collections.sort(individualList, individualComparator);
                    int currentListSize = individualList.size();
//                    System.out.println("size "+ currentListSize);
                    for (int i = 0; i < currentListSize-1; i++) {
                        Individual individual = individualList.get(i).crossOver(individualList.get(i+1));
                        individualList.add(individual);
                    }
                    Individual individual = individualList.get(currentListSize-1).crossOver(individualList.get(0));
                    individualList.add(individual);
                    Collections.sort(individualList, individualComparator);
                    for (int i = individualList.size()-1; i >= currentListSize; i--) {
                        System.out.println(i);
                        individualList.remove(i);
                    }
//                    System.out.println("size "+ individualList.size());
                    for (Individual individuals : individualList) {
                        System.out.println(individual.getFitness());
                    }
                }
            }
        });
        thread.start();
    }

    private void ComparsionThread() {
        Thread threadComparsion = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Comparsion");
                    Collections.sort(individualList, new IndividualComparator());
                    for (Individual individual : individualList) {

                        System.out.println(individual.getFitness());
                    }
                }
            }
        });
        threadComparsion.start();
    }

    private void mutationThread(Genes targetGene) {
        Thread threadMutation = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Mutation");
                    for (Individual individual : individualList) {
                        individual.mutations();
                        individual.fitnessAssessment(targetGene);
                    }
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

    public static void main(String[] args) {
        Genes targetGene = new Genes(20);
        Popluation popluation = new Popluation(5, targetGene);

    }

//    @Override
//    public void run() {
//        System.out.println("Start comparing");
//
//
//
//        while (true) {
//            for (Individual individual : individualList) {
//                individual.mutations();
//                individual.fitnessAssessment(targetGene);
//            }
//            Collections.sort(individualList, new IndividualComparator());
//            for (Individual individual : individualList) {
//                System.out.println(individual.getFitness());
//            }
//        }
//    }

//    @Override
//    public int compare(Individual o1, Individual o2) {
//        System.out.println("Comparing");
//        System.out.println(o1.getFitness() + " "+ o2.getFitness());
//        return (int) Math.round(o1.getFitness()-o2.getFitness());
//    }
}
