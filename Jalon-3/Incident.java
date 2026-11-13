public class Incident extends Ticket {

    public Incident(int numero, String titre, Utilisateur createur) {
        super(numero, titre, createur);
    }

    @Override
    public int delaiCible() {
        switch (getPriorite()) {
            case CRITIQUE: return 4;
            case HAUTE:    return 8;
            case NORMALE:  return 24;
            default:       return 72;
        }
    }
}
