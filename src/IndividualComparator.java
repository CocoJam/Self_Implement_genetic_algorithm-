import java.util.Comparator;

/**
 * Created by James lam on 16/11/2017.
 */
public class IndividualComparator implements Comparator<Individual> {
    @Override
    public int compare(Individual o1, Individual o2) {
        return Double.compare(o1.getFitness(),o2.getFitness());
    }
}
