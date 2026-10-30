public  class Ticket {
    private final int numero;
    private final String titre;
    private final Utilisateur createur;
    private Priorite priorite;
    private String etat;

    protected Ticket(int numero, String titre, Utilisateur createur) {
        if (numero <= 0)
            throw new IllegalArgumentException("Le numero doit etre positif et non nul");
        if (titre == null )
            throw new IllegalArgumentException("Le titre est obligatoire");
        if (createur == null)
            throw new IllegalArgumentException("Le createur est obligatoire");
        this.numero = numero;
        this.titre = titre;
        this.createur = createur;
        this.priorite = Priorite.BASSE;
        this.etat = "Nouveau";
    }

    public boolean prendreEnCharge(Utilisateur createur) {
        if (etat == "Nouveau"|| priorite== Priorite.CRITIQUE || priorite== Priorite.HAUTE) {
            etat = "En cours";

            return true;
        }
        if (etat=="En cours"){
            etat = "En cours";
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
        this.priorite = priorite;
    }

    public boolean resoudre(Utilisateur createur){
        if (etat == "En cours" ) {
            etat = "Résolu";
            return true;
        }
        return false;
    }
    public String getEtat() {
        return etat;
    }public String toString() {
        return "Ticket{" +
                "numero=" + numero +", titre='" + titre +", createur=" + createur +", priorite=" + priorite +", etat='" + etat +'}';
    }
        
    
}