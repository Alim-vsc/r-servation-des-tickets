public class Main {
    public static void main(String[] args) {
        Utilisateur john = new Utilisateur(1, "Doe", "John", "john.doe@example.com");
        System.out.println(john);

        Incident ticket1 = new Incident(1, "voyage bus", john);
        System.out.println(ticket1);
        ticket1.prendreEnCharge(john);
        System.out.println(ticket1.getEtat());
        ticket1.changerPriorite(Priorite.CRITIQUE);
        ticket1.resoudre(john);
        System.out.println(ticket1.getEtat());
        System.out.println("Délai cible : " + ticket1.delaiCible() + " h");

        Incident ticket2 = new Incident(2, "voyage train", john);
        System.out.println(ticket2);

        Ticket t = new DemandeService(3, "badge", john);
        System.out.println("Délai cible : " + t.delaiCible() + " h");
        System.out.println(john.getEmail());
    }
}