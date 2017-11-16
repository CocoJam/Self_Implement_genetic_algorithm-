import java.util.ArrayList;

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

    public Popluation(int popNumber) {
        this.popNumber = popNumber;
    }

    public void generatingPopluation() {
        for (int i = 0; i < popNumber; i++) {
            Individual individual = new Individual(targetGene);
            individualList.add(individual);
        }
    }

    public static void main(String[] args) {

    }
}
