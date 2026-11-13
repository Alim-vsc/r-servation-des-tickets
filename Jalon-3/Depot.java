import java.util.List;

public interface Depot<T> {
    public boolean ajouter(T element);
    public T trouver(int identifiant);
    public boolean supprimer(int identifiant);
    public List<T> lister();
    public int taille();
}