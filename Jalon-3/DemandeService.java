public class DemandeService extends Ticket {

    public DemandeService(int numero, String titre, Utilisateur createur) {
        super(numero, titre, createur);
    }

    @Override
    public int delaiCible() {
        return 48;
    }
}