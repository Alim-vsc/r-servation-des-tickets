DESCRIPTION DU CODE : JALON 3 (version corrigée après le retour du professeur)
Application de gestion des tickets de support informatique

Groupe G11 : SAKO Abdul Alim Dawoud, YELKUNI T. Franck Onel, TOUGMA K Dorian Trevor
Dépôt GitHub : https://github.com/Alim-vsc/r-servation-des-tickets.git
Tag de ce jalon : jalon-3


1. Description générale

Ce troisième jalon enrichit l'application des jalons 1 et 2 avec deux notions importantes de la programmation orientée objet : les interfaces et les collections. Les classes Utilisateur, Ticket, Incident, DemandeService et l'énumération Priorite sont conservées. On ajoute des interfaces qui décrivent des capacités (être notifié, être archivé, être stocké dans un dépôt) et des classes de dépôt qui rangent les objets dans des collections Java (List, Map, Set).

2. Les interfaces

2.1. Interface Notifiable
Elle définit ce que sait faire un objet capable de recevoir des messages : notifier(message) pour enregistrer un message et getNotifications() pour consulter la liste reçue. La classe Utilisateur l'implémente. Ainsi, quand un ticket est pris en charge ou résolu, son créateur reçoit automatiquement un message.

2.2. Interface Archivable
Elle contient la méthode peutEtreArchive() et une méthode par défaut resumeArchive() qui affiche le résultat. La classe Ticket l'implémente : un ticket est archivable seulement quand son état est « Résolu ». La méthode par défaut montre qu'une interface peut aussi fournir un comportement déjà écrit.

2.3. Interface générique Depot<T>
Elle décrit les opérations communes à tous les dépôts : ajouter, trouver, supprimer, lister et taille. Le type T est précisé par chaque classe qui l'implémente (Ticket ou Utilisateur), ce qui évite d'écrire deux fois la même interface.

2.4. Interfaces de la bibliothèque Java
Ticket implémente Comparable<Ticket> : la méthode compareTo classe les tickets par priorité décroissante (CRITIQUE d'abord), puis par numéro croissant. La classe ComparateurParDelai implémente Comparator<Ticket> pour proposer un second ordre de tri, selon le délai cible.

3. Les collections et les classes de dépôt

3.1. DepotTickets (implémente Depot<Ticket>)
Les tickets sont rangés dans une Map<Integer, Ticket> de type LinkedHashMap. Le numéro du ticket sert de clé, ce qui permet de retrouver un ticket directement et d'empêcher les doublons. L'ordre d'ajout est conservé. La classe propose aussi :
• trierParPriorite() qui utilise Collections.sort avec l'ordre naturel de Ticket ;
• trierParDelai() qui utilise Collections.sort avec ComparateurParDelai ;
• filtrerParEtat(etat) et ticketsDe(createur) qui renvoient une List de tickets ;
• repartitionParEtat() qui renvoie une Map (TreeMap) avec le nombre de tickets par état ;
• archiver() qui parcourt la collection avec un Iterator et retire les tickets archivables.

3.2. DepotUtilisateurs (implémente Depot<Utilisateur>)
Les utilisateurs sont rangés dans un Set<Utilisateur> de type HashSet. Un ensemble refuse les doublons, ce qui demande de redéfinir equals() et hashCode() dans Utilisateur : deux utilisateurs ayant le même id sont considérés comme identiques. La méthode lister() renvoie une List triée par id.

3.3. Protection des données
La liste de notifications d'un utilisateur est renvoyée avec Collections.unmodifiableList, et lister() renvoie toujours une copie. Les collections internes ne peuvent donc pas être modifiées de l'extérieur, ce qui respecte l'encapsulation.


4. Concepts de programmation orientée objet utilisés

• Interface : contrat que les classes s'engagent à respecter (Notifiable, Archivable, Depot).
• Implémentation multiple : Ticket implémente deux interfaces à la fois.
• Généricité : Depot<T> s'adapte au type d'objet stocké.
• Méthode par défaut : resumeArchive() dans Archivable.
• Collections : List pour les résultats, Map pour l'accès par numéro, Set pour l'unicité.
• Tri : ordre naturel avec Comparable, ordre personnalisé avec Comparator.
• Itérateur : suppression sûre pendant le parcours dans archiver().
• Polymorphisme : trierParDelai() appelle delaiCible() sans connaître le type exact de chaque ticket.

5. Description du fonctionnement et résultats attendus

Le Main crée deux utilisateurs, les range dans l'annuaire et vérifie qu'un doublon est refusé. Chaque essai affiche maintenant un verdict explicite, OK ou ECHEC, calculé par le programme lui même en comparant le résultat obtenu au résultat attendu. Il crée ensuite quatre tickets (trois incidents et une demande de service), les ajoute au dépôt et vérifie qu'un numéro déjà utilisé est refusé. Il affiche les tickets triés par priorité, puis par délai cible : les deux ordres sont différents pour la demande « badge », de priorité CRITIQUE mais avec un délai de 48 heures. Le programme prend en charge puis résout un ticket, filtre les tickets par état et par créateur, affiche la répartition par état et les notifications reçues par John. Il archive enfin les tickets résolus, supprime un ticket et vérifie que la liste de notifications ne peut pas être modifiée de l'extérieur. Les essais sont numérotés de E01 à E18, puis de E19 à E24 pour les corrections demandées par le professeur. Le programme termine par un bilan, par exemple 24 scénarios OK sur 24.

6. Corrections apportées après le retour du professeur

6.1. Les transitions dépendent maintenant de l'état du ticket
Avant, prendreEnCharge() acceptait un ticket de priorité HAUTE ou CRITIQUE quel que soit son état, donc un ticket déjà résolu pouvait repasser « En cours » à cause de sa seule priorité. La priorité n'intervient plus du tout dans les transitions. Les règles sont désormais :
1. prendreEnCharge : Nouveau devient En cours. Si le ticket est déjà En cours, rien ne change et la méthode renvoie true sans nouvelle notification. Si le ticket est Résolu, la méthode renvoie false.
2. resoudre : En cours devient Résolu. Dans tous les autres états, la méthode renvoie false.
3. rouvrir (nouvelle opération) : Résolu devient En cours, avec une notification « Ticket n rouvert » envoyée au créateur. Dans tous les autres états, la méthode renvoie false. La réouverture est donc voulue, explicite et séparée de la prise en charge.
Les trois états sont aussi déclarés comme constantes (ETAT_NOUVEAU, ETAT_EN_COURS, ETAT_RESOLU) pour éviter les fautes de frappe dans les comparaisons.

6.2. Refus de l'agent absent et des données obligatoires vides, avant toute modification
1. Les trois méthodes prendreEnCharge, resoudre et rouvrir vérifient l'agent en premier. Si l'agent est null, elles lancent une IllegalArgumentException avant de toucher à l'état ou aux notifications.
2. Le titre d'un ticket ne peut plus être null, vide ou fait uniquement d'espaces.
3. Le nom, le prénom et l'email d'un utilisateur ne peuvent plus être vides ou faits uniquement d'espaces. Le message d'une notification non plus.
4. L'essai E20 vérifie que l'agent absent est refusé et que l'état du ticket et la liste des notifications restent identiques après le refus. Les essais E21 et E24 vérifient le refus des données vides.

6.3. Petite correction dans toString() de Ticket
Il manquait des apostrophes fermantes dans l'affichage du titre et de l'état. C'est corrigé.

7. Verdicts des dix huit scénarios et des six nouveaux

Voici la sortie réelle du programme (lignes de verdict seulement) :

E01 ajout john : OK
E02 ajout jane : OK
E03 doublon john refuse : OK
E04 taille de l'annuaire 2026 egale a 2 : OK
E05 trouver id 2 donne jane : OK
E06 doublon de ticket refuse : OK
E07 taille du depot de tickets egale a 4 : OK
E08 ordre par priorite 1, 3, 4, 2 : OK
E09 ordre par delai cible 1, 4, 2, 3 : OK
E10 tickets resolus : seulement le 1 : OK
E11 tickets de john : 1 et 3 : OK
E12 repartition par etat : OK
E13 notifications de john : prise en charge puis resolution : OK
E14 t1 archivable et resume coherent : OK
E15 archivage de 1 ticket, il en reste 3 : OK
E16 suppression du ticket 3 : OK
E17 recherche du ticket 3 donne null : OK
E18 liste de notifications protegee : OK
E19 reprise d'un ticket resolu refusee malgre la priorite CRITIQUE : OK
E20 agent absent refuse, etat et notifications inchanges : OK
E21 titre vide refuse a la creation : OK
E22 reouverture explicite d'un ticket resolu : OK
E23 reouverture refusee sur un ticket non resolu : OK
E24 nom vide refuse pour un utilisateur : OK
Bilan : 24 scenarios OK sur 24

Lecture : OK signifie que le résultat obtenu est égal au résultat attendu, ECHEC signifie le contraire. Les scénarios E19 à E24 sont les nouveaux : reprise refusée sur un ticket résolu (E19), agent absent refusé sans changement (E20), titre vide refusé (E21), réouverture explicite (E22), réouverture refusée hors état Résolu (E23), nom vide refusé (E24).

8. Compilation, exécution et tag

Ce dossier se compile et s'exécute seul. Il ne faut surtout pas le compiler avec les dossiers Jalon-1 et Jalon-2, car ils contiennent des classes qui portent les mêmes noms (Ticket, Utilisateur, Main, etc.).

Depuis la racine du dépôt :

    cd Jalon-3
    javac -encoding UTF-8 *.java
    java -Dstdout.encoding=UTF-8 Main

Sous Windows, si les caractères accentués s'affichent mal, le programme reste correct : seul l'affichage de la console change. L'option -Dstdout.encoding=UTF-8 demande à Java 18 ou plus d'écrire en UTF-8. La version de Java conseillée est 17 ou plus.

Tag exact de ce jalon : jalon-3
Les tags des autres jalons sont jalon-1, jalon-2 et jalon-4. La remise finale portera le tag version-1.0.

Pour que le tag jalon-3 désigne bien la version corrigée, après le commit des corrections :

    git tag -f jalon-3
    git push origin jalon-3 --force

Préparation du rendu final : les tests JUnit 5 sont préparés dans le dossier Jalon-4 (dossier test), avec un fichier pom.xml pour les lancer.

9. Conclusion

Ce jalon rend l'application plus complète : les tickets et les utilisateurs sont désormais stockés, retrouvés, triés et filtrés grâce aux collections, et les interfaces séparent clairement les rôles des classes. Le code reste évolutif, car on peut ajouter un nouveau type de dépôt ou une nouvelle capacité sans modifier les classes existantes.
