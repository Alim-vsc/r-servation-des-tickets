import java.util.List;

public class Main {
    public static void main(String[] args) {
        DepotUtilisateurs annuaire2026 = new DepotUtilisateurs();
        Utilisateur john = new Utilisateur(1, "Doe", "John", "john.doe@example.com");
        Utilisateur jane = new Utilisateur(2, "Smith", "Jane", "jane.smith@example.com");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("pour les utilisateurs ");
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("E01 ajout john : " + annuaire2026.ajouter(john));
        System.out.println("E02 ajout jane : " + annuaire2026.ajouter(jane));
        System.out.println("E03 doublon john : " + annuaire2026.ajouter(new Utilisateur(1, "Doe", "John", "autre@example.com")));
        System.out.println("E04 taille  de l année 2026 est: " + annuaire2026.taille());
        System.out.println("E05 trouver id 2 : " + annuaire2026.trouver(2));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("pour les tickets ");
        System.out.println("---------------------------------------------------------------------------------------------");
        DepotTickets depotTick = new DepotTickets();
        Ticket t1 = new Incident(1, "voyage bus", john);
        Ticket t2 = new Incident(2, "voyage train", jane);
        Ticket t3 = new DemandeService(3, "badge", john);
        Ticket t4 = new Incident(4, "panne reseau", jane);
        depotTick.ajouter(t1);
        depotTick.ajouter(t2);
        depotTick.ajouter(t3);
        depotTick.ajouter(t4);
        System.out.println("E06 doublon ticket : " + depotTick.ajouter(new DemandeService(3, "autre", john)));
        System.out.println("E07 taille des depot de tickets : " + depotTick.taille());
        t1.changerPriorite(Priorite.CRITIQUE);
        System.out.println("la priorite du ticket t1 est :"+t1.getPriorite());
        t2.changerPriorite(Priorite.NORMALE);
        System.out.println("la priorite du ticket t2 est :"+t2.getPriorite());
        t3.changerPriorite(Priorite.CRITIQUE);
        System.out.println("la priorite du ticket t3 est :"+t3.getPriorite());
        t4.changerPriorite(Priorite.HAUTE);
        System.out.println("la priorite est :"+t4.getPriorite());
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("E08 tri par priorite :");
        afficher(depotTick.trierParPriorite());
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("E09 tri par delai cible :");
        afficher(depotTick.trierParDelai());
        System.out.println("---------------------------------------------------------------------------------------------");
        t1.prendreEnCharge(john);
        t1.resoudre(john);
        t4.prendreEnCharge(jane);
        System.out.println("E10 tickets resolus :");
        afficher(depotTick.filtrerParEtat("Résolu"));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("E11 tickets de john :");
        afficher(depotTick.ticketsDe(john));
        System.out.println("---------------------------------------------------------------------------------------------");
        System.out.println("E12 repartition par etat : " + depotTick.repartitionParEtat());
        System.out.println("E13 notifications de john : " + john.getNotifications());
        System.out.println("E14 t1 archivable : " + t1.peutEtreArchive() + " | " + t1.resumeArchive());System.out.println("E15 archivage : " + depotTick.archiver().size() + " ticket(s), reste " + depotTick.taille());
        System.out.println("E16 suppression ticket 3 : " + depotTick.supprimer(3));
        System.out.println("E17 recherche ticket 3 : " + depotTick.trouver(3));
        System.out.println("---------------------------------------------------------------------------------------------");
        try {
            john.getNotifications().add("intrus");
        } catch (UnsupportedOperationException e) {
            System.out.println("E18 liste de notifications protegee");
        }
        System.out.println("---------------------------------------------------------------------------------------------");
    }
    private static void afficher(List<Ticket> tickets) {
        for (Ticket t : tickets) {
            System.out.println("Le numero:   " + t.getNumero() + "  le titre:  " + t.getTitre() + "  la priorite:   " + t.getPriorite() + "  l etat:   " + t.getEtat() + "  Le temps:  " + t.delaiCible());
        }
    }
}
