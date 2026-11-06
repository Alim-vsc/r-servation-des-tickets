DESCRIPTION DU CODE – JALON 2
Application de gestion des tickets de support informatique
1. Description générale
Ce projet consiste à développer une application Java de gestion des tickets de support informatique. Elle permet de créer des tickets, de gérer leur priorité, de suivre leur état et de définir un délai cible de résolution selon leur catégorie. Le programme est organisé autour de plusieurs classes qui mettent en œuvre les principaux concepts de la programmation orientée objet.
2. Description des classes et de leur rôle
2.1. Classe Utilisateur
Cette classe représente les personnes qui utilisent l’application. Elle contient les attributs id, nom, prenom et email, qui servent à identifier chaque utilisateur. Le constructeur initialise ces informations et vérifie que l’identifiant est positif et que les autres champs ne sont pas nuls. Les méthodes getters permettent de consulter les informations, tandis que toString() facilite leur affichage.
2.2. Classe abstraite Ticket
Cette classe constitue le modèle commun à tous les tickets. Elle regroupe le numéro, le titre, le créateur, la priorité et l’état du ticket. Son constructeur initialise ces données et attribue par défaut la priorité BASSE et l’état « Nouveau ».
Elle contient également les méthodes de gestion du ticket : prendreEnCharge() permet de le faire passer à l’état « En cours », resoudre() permet de le faire passer à l’état « Résolu » lorsqu’il est en cours, et changerPriorite() permet de modifier sa priorité. Les getters donnent accès aux informations, toString() permet de les afficher et delaiCible() est déclarée abstraite pour être définie dans les classes filles.
2.3. Énumération Priorite
Cette énumération définit les quatre niveaux de priorité possibles : BASSE, NORMALE, HAUTE et CRITIQUE. Elle est utilisée dans Ticket pour attribuer une priorité à chaque ticket et dans Incident pour déterminer le délai cible correspondant.
2.4. Classe Incident
Cette classe hérite de Ticket et représente un problème ou un dysfonctionnement signalé par un utilisateur. Son constructeur appelle celui de la classe mère avec super(). Elle redéfinit la méthode delaiCible() à l’aide d’une structure switch : un incident critique dispose de 4 heures, un incident haut de 8 heures, un incident normal de 24 heures et un incident bas de 72 heures.
2.5. Classe DemandeService
Cette classe hérite également de Ticket et représente une demande de service, par exemple une demande de badge ou d’accès à une application. Elle utilise super() pour initialiser les informations communes et redéfinit delaiCible() afin de retourner un délai fixe de 48 heures.
2.6. Classe Main
Cette classe contient la méthode main(), qui sert de point de départ à l’exécution du programme. Elle crée un utilisateur, deux incidents et une demande de service. Elle teste ensuite l’affichage des objets, la prise en charge d’un incident, le changement de priorité, sa résolution et le calcul des délais cibles.
3. Concepts de programmation orientée objet utilisés
•	Encapsulation : les attributs sont déclarés private afin de protéger les données et de contrôler leur accès.
•	Héritage : Incident et DemandeService héritent de Ticket et réutilisent ses caractéristiques et ses méthodes.
•	Abstraction : Ticket est une classe abstraite qui définit une structure commune et la méthode abstraite delaiCible().
•	Polymorphisme : delaiCible() produit un résultat différent selon que l’objet est un Incident ou une DemandeService.
•	Redéfinition : @Override indique que les classes filles fournissent leur propre implémentation de delaiCible().
•	Énumération : Priorite limite les niveaux de priorité aux quatre valeurs prévues.
4. Description du fonctionnement et résultats attendus
Lors de son exécution, le programme crée d’abord l’utilisateur John Doe, puis un incident intitulé « voyage bus ». Cet incident est pris en charge, sa priorité est changée en CRITIQUE, puis il est résolu. Son délai cible est alors de 4 heures. Le programme crée ensuite un deuxième incident intitulé « voyage train » et une demande de service intitulée « badge ». Pour cette dernière, le délai cible affiché est de 48 heures.
Les résultats permettent ainsi de vérifier la création des objets, le changement d’état d’un ticket et le fonctionnement de la méthode delaiCible() selon le type de ticket.
5. Conclusion
Ce deuxième jalon met en place la structure principale de l’application de gestion des tickets. Chaque classe possède un rôle précis : représenter les utilisateurs, définir les tickets, distinguer les incidents des demandes de service et tester les fonctionnalités. L’encapsulation, l’héritage, l’abstraction et le polymorphisme permettent d’obtenir un programme organisé et évolutif, qui pourra être enrichi lors des prochains jalons.
