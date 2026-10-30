# r-servation-des-tickets
Groupe G11 : SAKO Abdul Alim Dawoud , YELKUNI T. Franck Onel , TOUGMA Trevor

Dépôt GitHub :https://github.com/Alim-vsc/r-servation-des-tickets.git
Tag : jalon-1

Synthèse du jalon 1 : modèle et invariants

Classes réalisées et responsabilités :
- Priorite (énumération BASSE(1), NORMALE(2), HAUTE(3) ,CRITIQUE(4)) : méthode deNiveau qui refuse tout entier hors de 1 à 3.
- Utilisateur (immuable) : identité (id, nom,prénom, email) :les méthodes getNom getPrenom, getId, getEmail et  ToString.
- Ticket : ( numéro, titre, créateur, état , priorité ); porte les methodes prendreEnCharge et resoudre, qui renvoient un booléen, getnumero , gettitre , getcreateur , getpriorite , getEtat et changerPriorite .
#----------------------------------------------------------------------#
- Main : exécute les 10 essais avec
Invariants vérifiés dans les constructeurs :
- Ticket : numéro > 0 et immuable, titre non vide , créateur non null, état initial Nouveau, et priorité initial basse sont des attributs privés.
- Utilisateur : id > 0, nom non vide , prénom non vide  et email non vide , immuable.
- Priorite : niveau compris entre 1 et 4.

Tests manuels réalisés (E01 à E11, tous OK) :
-E01 : Création d’un utilisateur .
-E02 : Affichage d’un l’utilisateur.
-E03 : Création d’un ticket.
-E04 : Affichage d’un ticket.
-E05 : Prendre en charge un ticket.
-E06 :Affichage de l’état du ticket .
-E07 :Changement de la priorité du ticket .
-E08 : Résolution du ticket
-E09 : Affichage de l état du ticket.
-E10 : Utilisateur avec un id "000"  refusé..
-E11 :Ticket avec numero "000" refusé.. 


