import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
public class DepotTickets implements Depot<Ticket> {
    private final Map<Integer, Ticket> tickets = new LinkedHashMap<>();
    @Override
    public boolean ajouter(Ticket ticket) {
        if (ticket == null) {
            throw new IllegalArgumentException("Le ticket est obligatoire");
        }
        if (tickets.containsKey(ticket.getNumero())) {
            return false;
        }
        tickets.put(ticket.getNumero(), ticket);
        return true;
    }
    @Override
    public Ticket trouver(int numero) {
        return tickets.get(numero);
    }
    @Override
    public boolean supprimer(int numero) {
        return tickets.remove(numero) != null;
    }
    @Override
    public List<Ticket> lister() {
        return new ArrayList<>(tickets.values());
    }
    @Override
    public int taille() {
        return tickets.size();
    }
    public List<Ticket> trierParPriorite() {
        List<Ticket> resultat = lister();
        Collections.sort(resultat);
        return resultat;
    }
    public List<Ticket> trierParDelai() {
        List<Ticket> resultat = lister();
        Collections.sort(resultat, new ComparateurParDelai());
        return resultat;
    }
    public List<Ticket> filtrerParEtat(String etat) {
        List<Ticket> resultat = new ArrayList<>();
        for (Ticket t : tickets.values()) {
            if (t.getEtat().equals(etat)) {
                resultat.add(t);
            }
        }
        return resultat;
    }
    public List<Ticket> ticketsDe(Utilisateur createur) {
        List<Ticket> resultat = new ArrayList<>();
        for (Ticket t : tickets.values()) {
            if (t.getCreateur().equals(createur)) {
                resultat.add(t);
            }
        }
        return resultat;
    }
    public Map<String, Integer> repartitionParEtat() {
        Map<String, Integer> repartition = new TreeMap<>();
        for (Ticket t : tickets.values()) {
            Integer actuel = repartition.get(t.getEtat());
            repartition.put(t.getEtat(), actuel == null ? 1 : actuel + 1);
        }
        return repartition;
    }
    public List<Ticket> archiver() {
        List<Ticket> archives = new ArrayList<>();
        Iterator<Ticket> it = tickets.values().iterator();
        while (it.hasNext()) {
            Ticket t = it.next();
            if (t.peutEtreArchive()) {
                archives.add(t);
                it.remove();
            }
        }
        return archives;
    }
}
