import com.example.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ComparateurParDelaiTest {
    private final Utilisateur u = new Utilisateur(1, "A", "B", "c@d.e");

    @Test
    void ordreParDelaiCroissant() {
        Ticket critique = new Incident(1, "a", u);
        critique.changerPriorite(Priorite.CRITIQUE);
        Ticket demande = new DemandeService(2, "b", u);
        assertTrue(new ComparateurParDelai().compare(critique, demande) < 0);
        assertTrue(new ComparateurParDelai().compare(demande, critique) > 0);
    }

    @Test
    void departageParNumeroEnCasDEgalite() {
        Ticket a = new DemandeService(1, "a", u);
        Ticket b = new DemandeService(2, "b", u);
        assertTrue(new ComparateurParDelai().compare(a, b) < 0);
        assertEquals(0, new ComparateurParDelai().compare(a, a));
    }
}
