package com.example;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    private interface Action {
        void executer();
    }

    private static int total = 0;
    private static int reussis = 0;

    public static void main(String[] args) {
        ServiceSupport service = new ServiceSupport();

        titre("Demonstration 1 : utilisateurs et annuaire");
        Utilisateur john = service.enregistrer(1, "Doe", "John", "john.doe@example.com");
        Utilisateur jane = service.enregistrer(2, "Smith", "Jane", "jane.smith@example.com");
        service.enregistrer(3, "Kabore", "Paul", "paul.kabore@example.com");
        verdict("E01", "trois utilisateurs enregistres", service.nombreUtilisateurs() == 3);
        verdict("E02", "doublon d'identifiant refuse", refuse(() -> service.enregistrer(1, "Autre", "Nom", "autre@example.com")));
        verdict("E03", "email vide refuse", refuse(() -> service.enregistrer(9, "Test", "Test", "  ")));
        verdict("E04", "recherche de l'id 2 donne jane", service.trouverUtilisateur(2) == jane);
        verdict("E05", "recherche d'un id inconnu donne null", service.trouverUtilisateur(99) == null);

        titre("Demonstration 2 : ouverture des tickets");
        Ticket t1 = service.ouvrirIncident("voyage bus", 1);
        Ticket t2 = service.ouvrirIncident("voyage train", 2);
        Ticket t3 = service.ouvrirDemande("badge", 1);
        Ticket t4 = service.ouvrirIncident("panne reseau", 2);
        verdict("E06", "numerotation automatique de 1 a 4",
                t1.getNumero() == 1 && t2.getNumero() == 2 && t3.getNumero() == 3 && t4.getNumero() == 4);
        verdict("E07", "etat initial Nouveau et priorite BASSE",
                t1.getEtat().equals(Ticket.ETAT_NOUVEAU) && t1.getPriorite() == Priorite.BASSE);
        verdict("E08", "titre vide refuse, aucun ticket cree",
                refuse(() -> service.ouvrirIncident("", 1)) && service.nombreTickets() == 4);
        verdict("E09", "createur inconnu refuse, aucun ticket cree",
                refuse(() -> service.ouvrirDemande("acces", 99)) && service.nombreTickets() == 4);

        titre("Demonstration 3 : priorites et tris");
        service.changerPriorite(1, Priorite.CRITIQUE);
        service.changerPriorite(2, Priorite.NORMALE);
        service.changerPriorite(3, Priorite.CRITIQUE);
        service.changerPriorite(4, Priorite.HAUTE);
        System.out.println("tri par priorite :");
        List<Ticket> parPriorite = service.trierParPriorite();
        afficher(parPriorite);
        verdict("E10", "ordre par priorite 1, 3, 4, 2", numeros(parPriorite).equals(List.of(1, 3, 4, 2)));
        System.out.println("tri par delai cible :");
        List<Ticket> parDelai = service.trierParDelai();
        afficher(parDelai);
        verdict("E11", "ordre par delai cible 1, 4, 2, 3", numeros(parDelai).equals(List.of(1, 4, 2, 3)));
        verdict("E12", "priorite null refusee, priorite inchangee",
                refuse(() -> service.changerPriorite(2, null)) && t2.getPriorite() == Priorite.NORMALE);

        titre("Demonstration 4 : cycle de vie d'un ticket");
        verdict("E13", "prise en charge du ticket 1 par l'agent 3",
                service.prendreEnCharge(1, 3) && t1.getEtat().equals(Ticket.ETAT_EN_COURS) && t1.getAgent().getId() == 3);
        verdict("E14", "resolution du ticket 1",
                service.resoudre(1, 3) && t1.getEtat().equals(Ticket.ETAT_RESOLU));
        int notificationsJohn = john.getNotifications().size();
        verdict("E15", "reprise d'un ticket resolu refusee malgre la priorite CRITIQUE",
                !service.prendreEnCharge(1, 3) && t1.getEtat().equals(Ticket.ETAT_RESOLU)
                        && john.getNotifications().size() == notificationsJohn);
        int notificationsJane = jane.getNotifications().size();
        verdict("E16", "agent inconnu refuse, etat et notifications inchanges",
                refuse(() -> service.prendreEnCharge(2, 99)) && t2.getEtat().equals(Ticket.ETAT_NOUVEAU)
                        && t2.getAgent() == null && jane.getNotifications().size() == notificationsJane);
        verdict("E17", "agent absent (null) refuse sur le ticket, rien ne change",
                refuse(() -> t2.prendreEnCharge(null)) && t2.getEtat().equals(Ticket.ETAT_NOUVEAU)
                        && jane.getNotifications().size() == notificationsJane);
        verdict("E18", "ticket inconnu refuse", refuse(() -> service.resoudre(77, 3)));
        verdict("E19", "resolution d'un ticket Nouveau refusee",
                !service.resoudre(3, 3) && t3.getEtat().equals(Ticket.ETAT_NOUVEAU));
        verdict("E20", "prise en charge du ticket 4, jane notifiee",
                service.prendreEnCharge(4, 3) && jane.getNotifications().size() == notificationsJane + 1);
        verdict("E21", "prise en charge repetee sans nouvelle notification",
                service.prendreEnCharge(4, 3) && jane.getNotifications().size() == notificationsJane + 1);
        verdict("E22", "reouverture explicite du ticket 1",
                service.rouvrir(1, 3) && t1.getEtat().equals(Ticket.ETAT_EN_COURS)
                        && john.getNotifications().get(john.getNotifications().size() - 1).equals("Ticket 1 rouvert"));
        verdict("E23", "reouverture refusee sur un ticket non resolu",
                !service.rouvrir(2, 3) && t2.getEtat().equals(Ticket.ETAT_NOUVEAU));
        verdict("E24", "nouvelle resolution du ticket 1", service.resoudre(1, 3) && t1.getEtat().equals(Ticket.ETAT_RESOLU));

        titre("Demonstration 5 : filtres, repartition et notifications");
        List<Ticket> resolus = service.filtrerParEtat(Ticket.ETAT_RESOLU);
        afficher(resolus);
        verdict("E25", "tickets resolus : seulement le 1", numeros(resolus).equals(List.of(1)));
        List<Ticket> deJohn = service.ticketsDe(1);
        afficher(deJohn);
        verdict("E26", "tickets de john : 1 et 3", numeros(deJohn).equals(List.of(1, 3)));
        Map<String, Integer> attendue = new TreeMap<>();
        attendue.put("En cours", 1);
        attendue.put("Nouveau", 2);
        attendue.put("Résolu", 1);
        System.out.println("repartition par etat : " + service.repartitionParEtat());
        verdict("E27", "repartition par etat", service.repartitionParEtat().equals(attendue));
        System.out.println("notifications de john : " + john.getNotifications());
        verdict("E28", "notifications de john dans l'ordre des evenements",
                john.getNotifications().equals(List.of("Ticket 1 pris en charge", "Ticket 1 resolu", "Ticket 1 rouvert", "Ticket 1 resolu")));
        boolean protegee = refuse(() -> john.getNotifications().add("intrus"));
        verdict("E29", "liste de notifications protegee", protegee);

        titre("Demonstration 6 : archivage");
        verdict("E30", "seul le ticket resolu est archivable",
                t1.peutEtreArchive() && !t4.peutEtreArchive() && t1.resumeArchive().equals("Element archivable : true"));
        verdict("E31", "archivage de 1 ticket, il en reste 3", service.archiver() == 1 && service.nombreTickets() == 3);
        verdict("E32", "le ticket archive est sorti du depot mais garde dans les archives",
                service.trouverTicket(1) == null && service.getArchives().size() == 1 && service.getArchives().get(0) == t1);

        titre("Bilan : " + reussis + " scenarios OK sur " + total);
    }

    private static boolean refuse(Action action) {
        try {
            action.executer();
            return false;
        } catch (IllegalArgumentException | UnsupportedOperationException e) {
            return true;
        }
    }

    private static void verdict(String id, String description, boolean ok) {
        total++;
        if (ok) {
            reussis++;
        }
        System.out.println(id + " " + description + " : " + (ok ? "OK" : "ECHEC"));
    }

    private static void titre(String texte) {
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println(texte);
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
