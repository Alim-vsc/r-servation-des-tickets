
import com.example.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepotUtilisateursTest {
    private DepotUtilisateurs depot;

    @BeforeEach
    void preparer() {
        depot = new DepotUtilisateurs();
        depot.ajouter(new Utilisateur(3, "C", "Cc", "c@example.com"));
        depot.ajouter(new Utilisateur(1, "A", "Aa", "a@example.com"));
        depot.ajouter(new Utilisateur(2, "B", "Bb", "b@example.com"));
    }

    @Test
    void ajouterRefuseLesDoublonsDIdentifiant() {
        assertFalse(depot.ajouter(new Utilisateur(1, "Autre", "Nom", "autre@example.com")));
        assertEquals(3, depot.taille());
    }

    @Test
    void ajouterUnUtilisateurAbsentRefuse() {
        assertThrows(IllegalArgumentException.class, () -> depot.ajouter(null));
    }

    @Test
    void trouverParIdentifiant() {
        assertEquals("B", depot.trouver(2).getNom());
        assertNull(depot.trouver(99));
    }

    @Test
    void supprimerParIdentifiant() {
        assertTrue(depot.supprimer(2));
        assertFalse(depot.supprimer(2));
        assertEquals(2, depot.taille());
    }

    @Test
    void listerTrieParIdentifiantEtRenvoieUneCopie() {
        List<Utilisateur> liste = depot.lister();
        assertEquals(1, liste.get(0).getId());
        assertEquals(2, liste.get(1).getId());
        assertEquals(3, liste.get(2).getId());
        liste.clear();
        assertEquals(3, depot.taille());
    }
}
