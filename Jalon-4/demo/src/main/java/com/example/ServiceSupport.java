package com.example;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
public class ServiceSupport {
    private final DepotUtilisateurs annuaire = new DepotUtilisateurs();
    private final DepotTickets depot = new DepotTickets();
    private final List<Ticket> archives = new ArrayList<>();
    private int prochainNumero = 1;
    public Utilisateur enregistrer(int id, String nom, String prenom, String email) {
        Utilisateur utilisateur = new Utilisateur(id, nom, prenom, email);
        if (!annuaire.ajouter(utilisateur)) {
            throw new IllegalArgumentException("Utilisateur deja enregistre : " + id);
        }
        return utilisateur;
    }
    public Ticket ouvrirIncident(String titre, int idCreateur) {
        Ticket ticket = new Incident(prochainNumero, titre, exigerUtilisateur(idCreateur));
        return enregistrerTicket(ticket);
    }
    public Ticket ouvrirDemande(String titre, int idCreateur) {
        Ticket ticket = new DemandeService(prochainNumero, titre, exigerUtilisateur(idCreateur));
        return enregistrerTicket(ticket);
    }
    private Ticket enregistrerTicket(Ticket ticket) {
        depot.ajouter(ticket);
        prochainNumero++;
        return ticket;
    }
    public boolean prendreEnCharge(int numero, int idAgent) {
        Ticket ticket = exigerTicket(numero);
        Utilisateur agent = exigerUtilisateur(idAgent);
        return ticket.prendreEnCharge(agent);
    }
    public boolean resoudre(int numero, int idAgent) {
        Ticket ticket = exigerTicket(numero);
        Utilisateur agent = exigerUtilisateur(idAgent);
        return ticket.resoudre(agent);
    }
    public boolean rouvrir(int numero, int idAgent) {
        Ticket ticket = exigerTicket(numero);
        Utilisateur agent = exigerUtilisateur(idAgent);
        return ticket.rouvrir(agent);
    }
    public void changerPriorite(int numero, Priorite priorite) {
        exigerTicket(numero).changerPriorite(priorite);
    }
    public int archiver() {
        List<Ticket> nouveaux = depot.archiver();
        archives.addAll(nouveaux);
        return nouveaux.size();
    }
    public Utilisateur trouverUtilisateur(int id) {
        return annuaire.trouver(id);
    }
    public Ticket trouverTicket(int numero) {
        return depot.trouver(numero);
    }
    public int nombreUtilisateurs() {
        return annuaire.taille();
    }
    public int nombreTickets() {
        return depot.taille();
    }
    public List<Ticket> trierParPriorite() {
        return depot.trierParPriorite();
    }
    public List<Ticket> trierParDelai() {
        return depot.trierParDelai();
    }
    public List<Ticket> filtrerParEtat(String etat) {
        return depot.filtrerParEtat(etat);
    }
    public List<Ticket> ticketsDe(int idCreateur) {
        return depot.ticketsDe(exigerUtilisateur(idCreateur));
    }
    public Map<String, Integer> repartitionParEtat() {
        return depot.repartitionParEtat();
    }
    public List<Ticket> getArchives() {
        return Collections.unmodifiableList(archives);
    }
    private Utilisateur exigerUtilisateur(int id) {
        Utilisateur utilisateur = annuaire.trouver(id);
        if (utilisateur == null) {
            throw new IllegalArgumentException("Utilisateur inconnu : " + id);
        }
        return utilisateur;
    }
    private Ticket exigerTicket(int numero) {
        Ticket ticket = depot.trouver(numero);
        if (ticket == null) {
            throw new IllegalArgumentException("Ticket inconnu : " + numero);
        }
        return ticket;
    }
}
