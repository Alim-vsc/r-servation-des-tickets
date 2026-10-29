public class Utilisateur {
    private int id;
    private String nom;
    private String prenom;
    private String email;

    public Utilisateur(int id, String nom, String prenom, String email) {
        if (nom == null ) {
            throw new IllegalArgumentException("Le nom ne peut pas être vide.");
        }
        if (prenom == null ) {
            throw new IllegalArgumentException("Le prénom ne peut pas être vide.");
        }
        if (email == null ) {
            throw new IllegalArgumentException("L'email ne peut pas être vide.");
        }
        if(id<=0){
            throw new IllegalArgumentException("L'id ne peut pas être négatif.");
        }
        this.id = id; 
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }
    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public String toString() {
        return "Utilisateur{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}