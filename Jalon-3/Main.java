import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private static int total = 0;
    private static int reussis = 0;

    public static void main(String[] args) {
        DepotUtilisateurs annuaire2026 = new DepotUtilisateurs();
        Utilisateur john = new Utilisateur(1, "Doe", "John", "john.doe@example.com");
        Utilisateur jane = new Utilisateur(2, "Smith", "Jane", "jane.smith@example.com");
        separateur();
        System.out.println("pour les utilisateurs");
        separateur();
        verdict("E01", "ajout john", annuaire2026.ajouter(john));
        verdict("E02", "ajout jane", annuaire2026.ajouter(jane));
        verdict("E03", "doublon john refuse", !annuaire2026.ajouter(new Utilisateur(1, "Doe", "John", "autre@example.com")));
        verdict("E04", "taille de l'annuaire 2026 egale a 2", annuaire2026.taille() == 2);
        verdict("E05", "trouver id 2 donne jane", annuaire2026.trouver(2) == jane);
        separateur();
        System.out.println("pour les tickets");
        separateur();
        DepotTickets depotTick = new DepotTickets();
        Ticket t1 = new Incident(1, "voyage bus", john);
        Ticket t2 = new Incident(2, "voyage train", jane);
        Ticket t3 = new DemandeService(3, "badge", john);
        Ticket t4 = new Incident(4, "panne reseau", jane);
        depotTick.ajouter(t1);
        depotTick.ajouter(t2);
        depotTick.ajouter(t3);
        depotTick.ajouter(t4);
        verdict("E06", "doublon de ticket refuse", !depotTick.ajouter(new DemandeService(3, "autre", john)));
        verdict("E07", "taille du depot de tickets egale a 4", depotTick.taille() == 4);
        t1.changerPriorite(Priorite.CRITIQUE);
        t2.changerPriorite(Priorite.NORMALE);
        t3.changerPriorite(Priorite.CRITIQUE);
        t4.changerPriorite(Priorite.HAUTE);
        separateur();
        System.out.println("tri par priorite :");
        List<Ticket> parPriorite = depotTick.trierParPriorite();
        afficher(parPriorite);
        verdict("E08", "ordre par priorite 1, 3, 4, 2", numeros(parPriorite).equals(List.of(1, 3, 4, 2)));
        separateur();
        System.out.println("tri par delai cible :");
        List<Ticket> parDelai = depotTick.trierParDelai();
        afficher(parDelai);
        verdict("E09", "ordre par delai cible 1, 4, 2, 3", numeros(parDelai).equals(List.of(1, 4, 2, 3)));
        separateur();
        t1.prendreEnCharge(john);
        t1.resoudre(john);
        t4.prendreEnCharge(jane);
        List<Ticket> resolus = depotTick.filtrerParEtat(Ticket.ETAT_RESOLU);
        afficher(resolus);
        verdict("E10", "tickets resolus : seulement le 1", numeros(resolus).equals(List.of(1)));
        separateur();
        List<Ticket> deJohn = depotTick.ticketsDe(john);
        afficher(deJohn);
        verdict("E11", "tickets de john : 1 et 3", numeros(deJohn).equals(List.of(1, 3)));
        separateur();
        Map<String, Integer> attendue = new TreeMap<>();
        attendue.put("En cours", 1);
        attendue.put("Nouveau", 2);
        attendue.put("Résolu", 1);
        System.out.println("repartition par etat : " + depotTick.repartitionParEtat());
        verdict("E12", "repartition par etat", depotTick.repartitionParEtat().equals(attendue));
        System.out.println("notifications de john : " + john.getNotifications());
        verdict("E13", "notifications de john : prise en charge puis resolution",
                john.getNotifications().equals(List.of("Ticket 1 pris en charge", "Ticket 1 resolu")));
        verdict("E14", "t1 archivable et resume coherent",
                t1.peutEtreArchive() && t1.resumeArchive().equals("Element archivable : true"));
        List<Ticket> archives = depotTick.archiver();
        verdict("E15", "archivage de 1 ticket, il en reste 3", archives.size() == 1 && depotTick.taille() == 3);
        verdict("E16", "suppression du ticket 3", depotTick.supprimer(3));
        verdict("E17", "recherche du ticket 3 donne null", depotTick.trouver(3) == null);
        boolean protegee = false;
        try {
            john.getNotifications().add("intrus");
        } catch (UnsupportedOperationException e) {
            protegee = true;
        }
        verdict("E18", "liste de notifications protegee", protegee);
        separateur();
        System.out.println("scenarios ajoutes apres le retour du professeur");
        separateur();
        int notificationsJohn = john.getNotifications().size();
        boolean reprise = t1.prendreEnCharge(john);
        verdict("E19", "reprise d'un ticket resolu refusee malgre la priorite CRITIQUE",
                !reprise && t1.getEtat().equals(Ticket.ETAT_RESOLU) && john.getNotifications().size() == notificationsJohn);
        String etatAvant = t2.getEtat();
        int notificationsJane = jane.getNotifications().size();
        boolean agentRefuse = false;
        try {
            t2.prendreEnCharge(null);
        } catch (IllegalArgumentException e) {
            agentRefuse = true;
        }
        verdict("E20", "agent absent refuse, etat et notifications inchanges",
                agentRefuse && t2.getEtat().equals(etatAvant) && jane.getNotifications().size() == notificationsJane);
        boolean titreRefuse = false;
        try {
            new Incident(5, "   ", john);
        } catch (IllegalArgumentException e) {
            titreRefuse = true;
        }
        verdict("E21", "titre vide refuse a la creation", titreRefuse);
        boolean rouvert = t1.rouvrir(john);
        verdict("E22", "reouverture explicite d'un ticket resolu",
                rouvert && t1.getEtat().equals(Ticket.ETAT_EN_COURS)
                        && john.getNotifications().get(john.getNotifications().size() - 1).equals("Ticket 1 rouvert"));
        verdict("E23", "reouverture refusee sur un ticket non resolu",
                !t2.rouvrir(john) && t2.getEtat().equals(Ticket.ETAT_NOUVEAU));
        boolean donneesRefusees = false;
        try {
            new Utilisateur(9, "", "Test", "test@example.com");
        } catch (IllegalArgumentException e) {
            donneesRefusees = true;
        }
        verdict("E24", "nom vide refuse pour un utilisateur", donneesRefusees);
        separateur();
        System.out.println("Bilan : " + reussis + " scenarios OK sur " + total);
        separateur();
    }

    private static void verdict(String id, String description, boolean ok) {
        total++;
        if (ok) {
            reussis++;
        }
        System.out.println(id + " " + description + " : " + (ok ? "OK" : "ECHEC"));
    }

    private static void separateur() {
        System.out.println("---------------------------------------------------------------------------------------------");
    }

    private static List<Integer> numeros(List<Ticket> tickets) {
        List<Integer> resultat = new ArrayList<>();
        for (Ticket t : tickets) {
            resultat.add(t.getNumero());
        }
        return resultat;
    }

    private static void afficher(List<Ticket> tickets) {
        for (Ticket t : tickets) {
            System.out.println("Le numero:   " + t.getNumero() + "  le titre:  " + t.getTitre() + "  la priorite:   " + t.getPriorite() + "  l etat:   " + t.getEtat() + "  Le temps:  " + t.delaiCible());
        }
    }
}
