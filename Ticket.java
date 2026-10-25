public abstract class Ticket{
    private int numero;
    private String titre;
    private  String etat;
    public Ticket(int numero, String titre, String etat){
        this.numero = numero;
        this.titre = titre;
        this.etat = "ouvert";
    }
    public boolean prendreEnCharge(){
        return
    }
    public boolean resoudre(){
        return 
    }
    public String toString(){
        return "Ticket numero: " + numero + ", titre: " + titre + ", etat: " + etat;
    }
}