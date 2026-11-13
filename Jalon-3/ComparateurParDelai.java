import java.util.Comparator;

public class ComparateurParDelai implements Comparator<Ticket> {
    @Override
    public int compare(Ticket a, Ticket b) {
        int difference = Integer.compare(a.delaiCible(), b.delaiCible());
        if (difference != 0) {
            return difference;
        }
        return Integer.compare(a.getNumero(), b.getNumero());
    }
}
