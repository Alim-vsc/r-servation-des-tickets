DESCRIPTION DU CODE : JALON 3
Application de gestion des tickets de support informatique

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

Le Main crée deux utilisateurs, les range dans l'annuaire et vérifie qu'un doublon est refusé. Il crée ensuite quatre tickets (trois incidents et une demande de service), les ajoute au dépôt et vérifie qu'un numéro déjà utilisé est refusé. Il affiche les tickets triés par priorité, puis par délai cible : les deux ordres sont différents pour la demande « badge », de priorité CRITIQUE mais avec un délai de 48 heures. Le programme prend en charge puis résout un ticket, filtre les tickets par état et par créateur, affiche la répartition par état et les notifications reçues par John. Il archive enfin les tickets résolus, supprime un ticket et vérifie que la liste de notifications ne peut pas être modifiée de l'extérieur. Les essais sont numérotés de E01 à E18.

6. Conclusion

Ce jalon rend l'application plus complète : les tickets et les utilisateurs sont désormais stockés, retrouvés, triés et filtrés grâce aux collections, et les interfaces séparent clairement les rôles des classes. Le code reste évolutif, car on peut ajouter un nouveau type de dépôt ou une nouvelle capacité sans modifier les classes existantes.
