import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utilisateur implements Notifiable {
    private final int id;
    private final String nom;
    private final String prenom;
    private final String email;
    private final List<String> notifications;
    public Utilisateur(int id, String nom, String prenom, String email) {
        if (nom == null) {
            throw new IllegalArgumentException("Le nom ne peut pas etre vide.");
        }
        if (prenom == null) {
            throw new IllegalArgumentException("Le prenom ne peut pas etre vide.");
        }
        if (email == null) {
            throw new IllegalArgumentException("L'email ne peut pas etre vide.");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("L'id ne peut pas etre negatif ou nul.");
        }
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.notifications = new ArrayList<>();
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
    @Override
    public void notifier(String message) {
        if (message == null) {
            throw new IllegalArgumentException("Le message est obligatoire");
        }
        notifications.add(message);
    }
    @Override
    public List<String> getNotifications() {
        return Collections.unmodifiableList(notifications);
    }
    @Override
    public boolean equals(Object autre) {
        if (this == autre) {
            return true;
        }
        if (!(autre instanceof Utilisateur)) {
            return false;
        }
        return id == ((Utilisateur) autre).id;
    }
    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
    @Override
    public String toString() {
        return "Utilisateur{" + "nom='" + nom + '\'' + ", prenom='" + prenom + '\'' + ", email='" + email + '\'' + '}';
    }
}
