public abstract class Ticket implements Comparable<Ticket>, Archivable {
    public static final String ETAT_NOUVEAU = "Nouveau";
    public static final String ETAT_EN_COURS = "En cours";
    public static final String ETAT_RESOLU = "Résolu";

    private final int numero;
    private final String titre;
    private final Utilisateur createur;
    private Priorite priorite;
    private String etat;

    protected Ticket(int numero, String titre, Utilisateur createur) {
        if (numero <= 0)
            throw new IllegalArgumentException("Le numero doit etre positif et non nul");
        if (titre == null || titre.isBlank())
            throw new IllegalArgumentException("Le titre est obligatoire et ne peut pas etre vide");
        if (createur == null)
            throw new IllegalArgumentException("Le createur est obligatoire");
        this.numero = numero;
        this.titre = titre;
        this.createur = createur;
        this.priorite = Priorite.BASSE;
        this.etat = ETAT_NOUVEAU;
    }

    private void exigerAgent(Utilisateur agent) {
        if (agent == null) {
            throw new IllegalArgumentException("L'agent est obligatoire");
        }
    }

    public boolean prendreEnCharge(Utilisateur agent) {
        exigerAgent(agent);
        if (etat.equals(ETAT_NOUVEAU)) {
            etat = ETAT_EN_COURS;
            createur.notifier("Ticket " + numero + " pris en charge");
            return true;
        }
        return etat.equals(ETAT_EN_COURS);
    }

    public boolean resoudre(Utilisateur agent) {
        exigerAgent(agent);
        if (etat.equals(ETAT_EN_COURS)) {
            etat = ETAT_RESOLU;
            createur.notifier("Ticket " + numero + " resolu");
            return true;
        }
        return false;
    }

    public boolean rouvrir(Utilisateur agent) {
        exigerAgent(agent);
        if (etat.equals(ETAT_RESOLU)) {
            etat = ETAT_EN_COURS;
            createur.notifier("Ticket " + numero + " rouvert");
            return true;
        }
        return false;
    }

    public int getNumero() {
        return numero;
    }

    public String getTitre() {
        return titre;
    }

    public Utilisateur getCreateur() {
        return createur;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void changerPriorite(Priorite priorite) {
        if (priorite == null) {
            throw new IllegalArgumentException("La priorite est obligatoire");
        }
        this.priorite = priorite;
    }

    public String getEtat() {
        return etat;
    }

    public abstract int delaiCible();

    @Override
    public boolean peutEtreArchive() {
        return etat.equals(ETAT_RESOLU);
    }

    @Override
    public int compareTo(Ticket autre) {
        int difference = autre.priorite.compareTo(this.priorite);
        if (difference != 0) {
            return difference;
        }
        return Integer.compare(this.numero, autre.numero);
    }

    @Override
    public String toString() {
        return "Ticket{" + "numero=" + numero + ", titre='" + titre + '\'' + ", createur=" + createur + ", priorite=" + priorite + ", etat='" + etat + '\'' + '}';
    }
}
