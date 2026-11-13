import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
public class DepotUtilisateurs implements Depot<Utilisateur> {
    private final Set<Utilisateur> utilisateurs = new HashSet<>();
    @Override
    public boolean ajouter(Utilisateur utilisateur) {
        if (utilisateur == null) {
            throw new IllegalArgumentException("L'utilisateur est obligatoire");
        }
        return utilisateurs.add(utilisateur);
    }
    @Override
    public Utilisateur trouver(int id) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id) {
                return u;
            }
        }
        return null;
    }
    @Override
    public boolean supprimer(int id) {
        Iterator<Utilisateur> it = utilisateurs.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == id) {
                it.remove();
                return true;
            }
        }
        return false;
    }
    @Override
    public List<Utilisateur> lister() {
        List<Utilisateur> resultat = new ArrayList<>(utilisateurs);
        resultat.sort((a, b) -> Integer.compare(a.getId(), b.getId()));
        return resultat;
    }
    @Override
    public int taille() {
        return utilisateurs.size();
    }
}
