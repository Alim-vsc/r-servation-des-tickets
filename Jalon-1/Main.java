public class Main{
    public static void main(){
        Utilisateur john = new Utilisateur(001,"Doe", "John", "john.doe@example.com");
        System.out.println(john);
        Ticket ticket1 = new Ticket(001, "voyage bus", john);
        System.out.println(ticket1);
        ticket1.prendreEnCharge(john);
        System.out.println(ticket1.getEtat());
        ticket1.changerPriorite(Priorite.CRITIQUE);
        ticket1.resoudre(john);
        System.out.println(ticket1.getEtat());
        //Utilisateur jane = new Utilisateur(000,"Smith", "Jane", "jane.smith@example.com");
        Ticket ticket2 = new Ticket(000, "voyage train", john);
        System.out.println(ticket2);
    }
}