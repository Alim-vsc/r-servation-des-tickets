
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.Priorite;
import com.example.ServiceSupport;
import com.example.Ticket;
import com.example.Utilisateur;

public class ServiceSupportTest {
    private ServiceSupport service;
    private Utilisateur john;
    private Utilisateur jane;

    @BeforeEach
    void preparer() {
        service = new ServiceSupport();
        john = service.enregistrer(1, "Doe", "John", "john@example.com");
        jane = service.enregistrer(2, "Smith", "Jane", "jane@example.com");
        service.enregistrer(3, "Kabore", "Paul", "paul@example.com");
    }

    @Test
    void enregistrerRefuseUnDoublon() {
        assertThrows(IllegalArgumentException.class, () -> service.enregistrer(1, "Autre", "Nom", "x@example.com"));
        assertEquals(3, service.nombreUtilisateurs());
    }

    @Test
    void enregistrerRefuseUneDonneeVide() {
        assertThrows(IllegalArgumentException.class, () -> service.enregistrer(9, "", "Nom", "x@example.com"));
        assertEquals(3, service.nombreUtilisateurs());
    }

    @Test
    void numerotationAutomatiqueCroissante() {
        assertEquals(1, service.ouvrirIncident("a", 1).getNumero());
        assertEquals(2, service.ouvrirDemande("b", 2).getNumero());
        assertEquals(2, service.nombreTickets());
    }

    @Test
    void ouvrirRefuseUnCreateurInconnu() {
        assertThrows(IllegalArgumentException.class, () -> service.ouvrirIncident("a", 99));
        assertEquals(0, service.nombreTickets());
    }

    @Test
    void unTitreVideNeConsommePasLeNumero() {
        assertThrows(IllegalArgumentException.class, () -> service.ouvrirIncident("", 1));
        assertEquals(1, service.ouvrirIncident("a", 1).getNumero());
    }

    @Test
    void agentInconnuRefuseSansModifierLeTicket() {
        Ticket t = service.ouvrirIncident("a", 2);
        assertThrows(IllegalArgumentException.class, () -> service.prendreEnCharge(1, 99));
        assertEquals(Ticket.ETAT_NOUVEAU, t.getEtat());
        assertNull(t.getAgent());
        assertTrue(jane.getNotifications().isEmpty());
    }

    @Test
    void ticketInconnuRefuse() {
        assertThrows(IllegalArgumentException.class, () -> service.prendreEnCharge(42, 3));
        assertThrows(IllegalArgumentException.class, () -> service.resoudre(42, 3));
        assertThrows(IllegalArgumentException.class, () -> service.rouvrir(42, 3));
    }

    @Test
    void cycleDeVieComplet() {
        Ticket t = service.ouvrirIncident("a", 1);
        assertTrue(service.prendreEnCharge(1, 3));
        assertEquals(3, t.getAgent().getId());
        assertTrue(service.resoudre(1, 3));
        assertFalse(service.prendreEnCharge(1, 3));
        assertTrue(service.rouvrir(1, 3));
        assertTrue(service.resoudre(1, 3));
        assertEquals(List.of("Ticket 1 pris en charge", "Ticket 1 resolu", "Ticket 1 rouvert", "Ticket 1 resolu"),
                john.getNotifications());
    }

    @Test
    void changerPrioriteSurUnTicketInconnuRefuse() {
        assertThrows(IllegalArgumentException.class, () -> service.changerPriorite(5, Priorite.HAUTE));
    }

    @Test
    void archiverDeplaceLesTicketsResolusVersLesArchives() {
        Ticket t1 = service.ouvrirIncident("a", 1);
        service.ouvrirIncident("b", 2);
        service.prendreEnCharge(1, 3);
        service.resoudre(1, 3);
        assertEquals(1, service.archiver());
        assertEquals(1, service.nombreTickets());
        assertNull(service.trouverTicket(1));
        assertEquals(1, service.getArchives().size());
        assertSame(t1, service.getArchives().get(0));
        assertEquals(0, service.archiver());
    }

    @Test
    void listeDesArchivesNonModifiable() {
        assertThrows(UnsupportedOperationException.class, () -> service.getArchives().add(null));
    }

    @Test
    void ticketsDUnCreateurInconnuRefuse() {
        assertThrows(IllegalArgumentException.class, () -> service.ticketsDe(99));
    }
}
