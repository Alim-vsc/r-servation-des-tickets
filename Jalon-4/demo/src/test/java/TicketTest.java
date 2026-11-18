import com.example.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TicketTest {
    private Utilisateur createur;
    private Utilisateur agent;
    private Ticket ticket;

    @BeforeEach
    void preparer() {
        createur = new Utilisateur(1, "Doe", "John", "john@example.com");
        agent = new Utilisateur(2, "Kabore", "Paul", "paul@example.com");
        ticket = new Incident(1, "panne", createur);
    }

    @Test
    void creationValideInitialiseEtatEtPriorite() {
        assertEquals(1, ticket.getNumero());
        assertEquals("panne", ticket.getTitre());
        assertSame(createur, ticket.getCreateur());
        assertEquals(Ticket.ETAT_NOUVEAU, ticket.getEtat());
        assertEquals(Priorite.BASSE, ticket.getPriorite());
        assertNull(ticket.getAgent());
    }

    @Test
    void numeroNulOuNegatifRefuse() {
        assertThrows(IllegalArgumentException.class, () -> new Incident(0, "x", createur));
        assertThrows(IllegalArgumentException.class, () -> new Incident(-3, "x", createur));
    }

    @Test
    void titreAbsentVideOuBlancRefuse() {
        assertThrows(IllegalArgumentException.class, () -> new Incident(1, null, createur));
        assertThrows(IllegalArgumentException.class, () -> new Incident(1, "", createur));
        assertThrows(IllegalArgumentException.class, () -> new DemandeService(1, "   ", createur));
    }

    @Test
    void createurAbsentRefuse() {
        assertThrows(IllegalArgumentException.class, () -> new DemandeService(1, "badge", null));
    }

    @Test
    void prendreEnChargeDepuisNouveau() {
        assertTrue(ticket.prendreEnCharge(agent));
        assertEquals(Ticket.ETAT_EN_COURS, ticket.getEtat());
        assertSame(agent, ticket.getAgent());
        assertEquals(1, createur.getNotifications().size());
        assertEquals("Ticket 1 pris en charge", createur.getNotifications().get(0));
    }

    @Test
    void prendreEnChargeDejaEnCoursNeChangeRien() {
        ticket.prendreEnCharge(agent);
        assertTrue(ticket.prendreEnCharge(agent));
        assertEquals(Ticket.ETAT_EN_COURS, ticket.getEtat());
        assertEquals(1, createur.getNotifications().size());
    }

    @Test
    void agentAbsentRefuseSansModifierLeTicket() {
        assertThrows(IllegalArgumentException.class, () -> ticket.prendreEnCharge(null));
        assertEquals(Ticket.ETAT_NOUVEAU, ticket.getEtat());
        assertNull(ticket.getAgent());
        assertEquals(0, createur.getNotifications().size());
    }

    @Test
    void resoudreAvecAgentAbsentLaisseLeTicketEnCours() {
        ticket.prendreEnCharge(agent);
        assertThrows(IllegalArgumentException.class, () -> ticket.resoudre(null));
        assertEquals(Ticket.ETAT_EN_COURS, ticket.getEtat());
        assertEquals(1, createur.getNotifications().size());
    }

    @Test
    void rouvrirAvecAgentAbsentLaisseLeTicketResolu() {
        ticket.prendreEnCharge(agent);
        ticket.resoudre(agent);
        assertThrows(IllegalArgumentException.class, () -> ticket.rouvrir(null));
        assertEquals(Ticket.ETAT_RESOLU, ticket.getEtat());
        assertEquals(2, createur.getNotifications().size());
    }

    @Test
    void ticketResoluNePeutPasEtreRepris() {
        ticket.changerPriorite(Priorite.CRITIQUE);
        ticket.prendreEnCharge(agent);
        ticket.resoudre(agent);
        int avant = createur.getNotifications().size();
        assertFalse(ticket.prendreEnCharge(agent));
        assertEquals(Ticket.ETAT_RESOLU, ticket.getEtat());
        assertEquals(avant, createur.getNotifications().size());
    }

    @Test
    void prioriteHauteNePermetPasNonPlusLaReprise() {
        ticket.changerPriorite(Priorite.HAUTE);
        ticket.prendreEnCharge(agent);
        ticket.resoudre(agent);
        assertFalse(ticket.prendreEnCharge(agent));
        assertEquals(Ticket.ETAT_RESOLU, ticket.getEtat());
    }

    @Test
    void resoudreSeulementDepuisEnCours() {
        assertFalse(ticket.resoudre(agent));
        assertEquals(Ticket.ETAT_NOUVEAU, ticket.getEtat());
        ticket.prendreEnCharge(agent);
        assertTrue(ticket.resoudre(agent));
        assertEquals(Ticket.ETAT_RESOLU, ticket.getEtat());
        assertEquals("Ticket 1 resolu", createur.getNotifications().get(1));
    }

    @Test
    void resoudreUnTicketDejaResoluEstRefuse() {
        ticket.prendreEnCharge(agent);
        ticket.resoudre(agent);
        assertFalse(ticket.resoudre(agent));
        assertEquals(2, createur.getNotifications().size());
    }

    @Test
    void rouvrirSeulementDepuisResolu() {
        assertFalse(ticket.rouvrir(agent));
        ticket.prendreEnCharge(agent);
        assertFalse(ticket.rouvrir(agent));
        assertEquals(Ticket.ETAT_EN_COURS, ticket.getEtat());
        ticket.resoudre(agent);
        assertTrue(ticket.rouvrir(agent));
        assertEquals(Ticket.ETAT_EN_COURS, ticket.getEtat());
        assertEquals("Ticket 1 rouvert", createur.getNotifications().get(2));
    }

    @Test
    void changerPrioriteNulleRefusee() {
        ticket.changerPriorite(Priorite.HAUTE);
        assertThrows(IllegalArgumentException.class, () -> ticket.changerPriorite(null));
        assertEquals(Priorite.HAUTE, ticket.getPriorite());
    }

    @Test
    void changerPrioriteNeRouvrePasUnTicketResolu() {
        ticket.prendreEnCharge(agent);
        ticket.resoudre(agent);
        ticket.changerPriorite(Priorite.CRITIQUE);
        assertEquals(Ticket.ETAT_RESOLU, ticket.getEtat());
    }

    @Test
    void archivableSeulementQuandResolu() {
        assertFalse(ticket.peutEtreArchive());
        ticket.prendreEnCharge(agent);
        assertFalse(ticket.peutEtreArchive());
        ticket.resoudre(agent);
        assertTrue(ticket.peutEtreArchive());
        assertEquals("Element archivable : true", ticket.resumeArchive());
    }

    @Test
    void ordreNaturelPrioriteDecroissantPuisNumero() {
        Ticket autre = new Incident(2, "autre", createur);
        ticket.changerPriorite(Priorite.NORMALE);
        autre.changerPriorite(Priorite.CRITIQUE);
        assertTrue(autre.compareTo(ticket) < 0);
        autre.changerPriorite(Priorite.NORMALE);
        assertTrue(ticket.compareTo(autre) < 0);
    }

    @Test
    void delaisCiblesDesIncidents() {
        assertEquals(72, ticket.delaiCible());
        ticket.changerPriorite(Priorite.NORMALE);
        assertEquals(24, ticket.delaiCible());
        ticket.changerPriorite(Priorite.HAUTE);
        assertEquals(8, ticket.delaiCible());
        ticket.changerPriorite(Priorite.CRITIQUE);
        assertEquals(4, ticket.delaiCible());
    }

    @Test
    void delaiCibleDUneDemandeDeServiceEstFixe() {
        Ticket demande = new DemandeService(3, "badge", createur);
        demande.changerPriorite(Priorite.CRITIQUE);
        assertEquals(48, demande.delaiCible());
    }
}
