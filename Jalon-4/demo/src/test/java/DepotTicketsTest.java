
import com.example.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepotTicketsTest {
    private Utilisateur john;
    private Utilisateur jane;
    private Ticket t1;
    private Ticket t2;
    private Ticket t3;
    private Ticket t4;
    private DepotTickets depot;

    @BeforeEach
    void preparer() {
        john = new Utilisateur(1, "Doe", "John", "john@example.com");
        jane = new Utilisateur(2, "Smith", "Jane", "jane@example.com");
        t1 = new Incident(1, "voyage bus", john);
        t2 = new Incident(2, "voyage train", jane);
        t3 = new DemandeService(3, "badge", john);
        t4 = new Incident(4, "panne reseau", jane);
        t1.changerPriorite(Priorite.CRITIQUE);
        t2.changerPriorite(Priorite.NORMALE);
        t3.changerPriorite(Priorite.CRITIQUE);
        t4.changerPriorite(Priorite.HAUTE);
        depot = new DepotTickets();
        depot.ajouter(t1);
        depot.ajouter(t2);
        depot.ajouter(t3);
        depot.ajouter(t4);
    }

    private List<Integer> numeros(List<Ticket> tickets) {
        List<Integer> resultat = new ArrayList<>();
        for (Ticket t : tickets) {
            resultat.add(t.getNumero());
        }
        return resultat;
    }

    @Test
    void ajouterRefuseLesDoublonsDeNumero() {
        assertFalse(depot.ajouter(new DemandeService(3, "autre", john)));
        assertEquals(4, depot.taille());
    }

    @Test
    void ajouterUnTicketAbsentRefuse() {
        assertThrows(IllegalArgumentException.class, () -> depot.ajouter(null));
    }

    @Test
    void trouverEtSupprimer() {
        assertSame(t2, depot.trouver(2));
        assertNull(depot.trouver(99));
        assertTrue(depot.supprimer(2));
        assertFalse(depot.supprimer(2));
        assertEquals(3, depot.taille());
    }

    @Test
    void listerRenvoieUneCopie() {
        List<Ticket> copie = depot.lister();
        copie.clear();
        assertEquals(4, depot.taille());
    }

    @Test
    void listerConserveLOrdreDAjout() {
        assertEquals(List.of(1, 2, 3, 4), numeros(depot.lister()));
    }

    @Test
    void triParPriorite() {
        assertEquals(List.of(1, 3, 4, 2), numeros(depot.trierParPriorite()));
    }

    @Test
    void triParDelaiCible() {
        assertEquals(List.of(1, 4, 2, 3), numeros(depot.trierParDelai()));
    }

    @Test
    void filtrerParEtat() {
        Utilisateur agent = new Utilisateur(3, "K", "P", "p@example.com");
        t1.prendreEnCharge(agent);
        t1.resoudre(agent);
        assertEquals(List.of(1), numeros(depot.filtrerParEtat(Ticket.ETAT_RESOLU)));
        assertEquals(List.of(2, 3, 4), numeros(depot.filtrerParEtat(Ticket.ETAT_NOUVEAU)));
        assertTrue(depot.filtrerParEtat(Ticket.ETAT_EN_COURS).isEmpty());
    }

    @Test
    void ticketsDUnCreateur() {
        assertEquals(List.of(1, 3), numeros(depot.ticketsDe(john)));
        assertEquals(List.of(2, 4), numeros(depot.ticketsDe(jane)));
    }

    @Test
    void repartitionParEtat() {
        Utilisateur agent = new Utilisateur(3, "K", "P", "p@example.com");
        t1.prendreEnCharge(agent);
        t1.resoudre(agent);
        t4.prendreEnCharge(agent);
        Map<String, Integer> repartition = depot.repartitionParEtat();
        assertEquals(1, repartition.get(Ticket.ETAT_RESOLU));
        assertEquals(1, repartition.get(Ticket.ETAT_EN_COURS));
        assertEquals(2, repartition.get(Ticket.ETAT_NOUVEAU));
    }

    @Test
    void archiverRetireSeulementLesTicketsResolus() {
        Utilisateur agent = new Utilisateur(3, "K", "P", "p@example.com");
        t1.prendreEnCharge(agent);
        t1.resoudre(agent);
        t4.prendreEnCharge(agent);
        List<Ticket> archives = depot.archiver();
        assertEquals(List.of(1), numeros(archives));
        assertEquals(3, depot.taille());
        assertNull(depot.trouver(1));
        assertTrue(depot.archiver().isEmpty());
    }
}
