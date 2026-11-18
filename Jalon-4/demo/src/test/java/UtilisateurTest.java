import com.example.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class UtilisateurTest {
    @Test
    void creationValide() {
        Utilisateur u = new Utilisateur(1, "Doe", "John", "john@example.com");
        assertEquals(1, u.getId());
        assertEquals("Doe", u.getNom());
        assertEquals("John", u.getPrenom());
        assertEquals("john@example.com", u.getEmail());
        assertTrue(u.getNotifications().isEmpty());
    }

    @Test
    void identifiantNulOuNegatifRefuse() {
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(0, "A", "B", "c@d.e"));
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(-1, "A", "B", "c@d.e"));
    }

    @Test
    void donneesObligatoiresAbsentesOuVidesRefusees() {
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(1, null, "B", "c@d.e"));
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(1, "", "B", "c@d.e"));
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(1, "A", null, "c@d.e"));
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(1, "A", "  ", "c@d.e"));
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(1, "A", "B", null));
        assertThrows(IllegalArgumentException.class, () -> new Utilisateur(1, "A", "B", ""));
    }

    @Test
    void notifierEnregistreLesMessagesDansLOrdre() {
        Utilisateur u = new Utilisateur(1, "A", "B", "c@d.e");
        u.notifier("premier");
        u.notifier("second");
        assertEquals(2, u.getNotifications().size());
        assertEquals("premier", u.getNotifications().get(0));
        assertEquals("second", u.getNotifications().get(1));
    }

    @Test
    void messageAbsentOuVideRefuse() {
        Utilisateur u = new Utilisateur(1, "A", "B", "c@d.e");
        assertThrows(IllegalArgumentException.class, () -> u.notifier(null));
        assertThrows(IllegalArgumentException.class, () -> u.notifier(" "));
        assertTrue(u.getNotifications().isEmpty());
    }

    @Test
    void listeDeNotificationsNonModifiable() {
        Utilisateur u = new Utilisateur(1, "A", "B", "c@d.e");
        assertThrows(UnsupportedOperationException.class, () -> u.getNotifications().add("intrus"));
    }

    @Test
    void egaliteSurLIdentifiantSeulement() {
        Utilisateur a = new Utilisateur(1, "A", "B", "c@d.e");
        Utilisateur b = new Utilisateur(1, "X", "Y", "z@z.z");
        Utilisateur c = new Utilisateur(2, "A", "B", "c@d.e");
        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
        assertNotEquals(a, null);
    }
}
