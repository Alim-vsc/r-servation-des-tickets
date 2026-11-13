public abstract class Ticket implements Comparable<Ticket>, Archivable {
    private final int numero;
    private final String titre;
    private final Utilisateur createur;
    private Priorite priorite;
    private String etat;
    protected Ticket(int numero, String titre, Utilisateur createur) {
        if (numero <= 0)
            throw new IllegalArgumentException("Le numero doit etre positif et non nul");
        if (titre == null)
            throw new IllegalArgumentException("Le titre est obligatoire");
        if (createur == null)
            throw new IllegalArgumentException("Le createur est obligatoire");
        this.numero = numero;
        this.titre = titre;
        this.createur = createur;
        this.priorite = Priorite.BASSE;
        this.etat = "Nouveau";
    }
    public boolean prendreEnCharge(Utilisateur agent) {
        if (etat.equals("Nouveau")  priorite == Priorite.CRITIQUE  priorite == Priorite.HAUTE) {
            etat = "En cours";
            createur.notifier("Ticket " + numero + " pris en charge");
            return true;
        }
        if(etat.equals("En cours")) {
            return true;
        }
        return false;
    }
    public boolean resoudre(Utilisateur agent) {
        if (etat.equals("En cours")) {
            etat = "Résolu";
            createur.notifier("Ticket " + numero + " resolu");
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
        return etat.equals("Résolu");
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
        return "Ticket{" +"numero=" + numero +", titre='" + titre  +", createur=" + createur +", priorite=" + priorite +", etat='" + etat +'}';
    }
}
