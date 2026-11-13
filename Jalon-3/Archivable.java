public interface Archivable {
    public boolean peutEtreArchive();

    default public String resumeArchive() {
        return "Element archivable : " + peutEtreArchive();
    }
}
