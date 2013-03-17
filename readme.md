
#Projet Mowers##
Application serveur REST avec Spring MVC & MongoDB

Jean-Baptiste HUNTZINGER, 2013

##Présentation du projet Mowers##
Le projet "Mowers" (= tondeuses en français) est inspirée du test d'entretien technique d'une société de conseil, dans le cadre d'un recrutement pour un poste< d'architecte J2EE.
Pour la petite histoire j'ai réalisé le test très (trop) rapidement, et je n'ai pas pu continuer le processus d'entretien...
Mais le correcteur m'ayant fait des remarques précises sur ce qui n'allait pas, j'ai décidé de réécrire ce test en élargissant les spécifications d'origine et en profitant de l'occasion pour utiliser des outils open source actuels: Spring Source (le framework que j'avais déjà utilisé, mais en 2005 !), MongoDB (Base de donnée NoSQL orientée documents éditée par une société américaine: 10gen) et angular.js coté client (Google, framework javacript).
L'objectif pragmatique étant de préparer mes entretiens suivants avec une référence récente sur ces technologies.


##Les spécifications
Il s'agit de simuler le déplacement d'unités mobile (des tondeuses, dans un premier temps) sur une surface (un jardin rectangulaire,
toujours dans un premier temps).

Pour expliquer simplement le but du projet, on part du cas le plus simple qui est traité par l'application: une surface rectangulaire que l'on appelle le jardin,
et des tondeuses pour les unités mobiles.
La taille du jardin est définie par sa longeur et sa largeur, et chaque emplacement du jardin est représentée par une 'case' sous forme de coordonnée (x, y).
Chaque tondeuse est initialement placée sur une case (x1, y1), et orientée suivant une direction correspondant à un point cardinal: 'N'ord, 'S'ud, 'E'st, 'O'uest.
Par exemple une tondeuse sur la case (0,0) et orientée 'N' se trouve en bas à gauche du jardin, et tournée vers le nord.

Elle peut être déplacée selon des ordres, qui sont transmis sous forme de séries de mouvements unitaires:

	'D' pour pivoter la tondeuse à droite
	'G' pour pivoter la tondeuse à gauche
	'A' pour avancer sur la case en face de la tondeuse

Pour une situation ou les unités sont forcemment des tondeuses, et la surface un jardin, l'application peut prendre en entrée un fichier ou un flux de texte,
qu'il interprète afin de créer un jardin, une ou plusieurs tondeuses, puis pour chaque tondeuse de la placer dans le jardin et lui appliquer une série de mouvements
unitaires.
La structure du fichier est la suivante:

- Première ligne: largeur et longueur du jardin
- Puis 2 lignes pour chaque tondeuse (numérotées de 1 à n):
	1. Définition de la tondeuse n: position initiale de la tondeuse: x,y et orientation 'N', 'S', 'E' ou 'O'
	2. Séquence d'ordres de déplacement associée à la tondeuse n sous forme d'une série de lettres: 'D', 'G' ou 'A'


Exemple:  
    10 10  
    0 0 N  
    AADAA  
    5 5 S  
    AGAGAD  

La première ligne indique la création d'un jardin de taille 10x10.  
Les 2 lignes suivantes indiquent la création et le positionnement d'une tondeuse sur la case (0,0) du jardin, tournée vers le nord.  

La séquence correspond, sous forme de phrase: "Avance de 2 cases, pivote à droite, avance de 2 cases"
Idem pour les 2 dernières lignes qui définissent une tondeuse sur la case (5,5) du jardin, tournée vers le sud, et dont la
séquence correspond à "Avance d'une case, pivote à gauche, Avance d'une case, pivote à gauche, Avance d'une case, pivote à droite"

Après traitement de ce fichier par l'application, et exécution des ordres de déplacement, les positions des tondeuses sont:  
- Tondeuse 1: coordonnées=(2,2) orientation='E'  
- Tondeuse 2: coordonnées=(6,5) orientation='E'  

Les objets métiers / domaine
Le domaine est composé de 3 types d'entités (en italique, les classes abstraites):

- ***Surface*, *SurfaceRectangulaire*, Jardin**: représentation de la surface sur laquelle évoluent les unités
- ***Unit*, Mower**: les unités mobile, dont les tondeuses
- **RoundOrders**: entité regroupant une surfaces, les unités qui s'y trouvent et une série de mouvements unitaires sur chacune des unités


##Installation et lancement de l'application##
L'application Mowers est réalisé sous Maven2, elle peut donc être installée de manière simple par exemple et saisissant la ligne de commandes:
mvn install
dans le répertoire du projet.

Mowers utilise Spring MVC, avec des controlleurs pour les Surfaces, les Unités, les RoundOrders.
Le déployment sous un serveur Tomcat local peut se faire avec la commande:
mvn tomcat:deploy (ou mvn tomcat:redeploy)

Des tests unitaires sont écrits, que l'on peut lancer à l'aide d'eclipse ('Run configuration' de type JUnit) ou de maven (mvn test).
La lancement de l'application sous eclipse ou en ligne de commande prend en argument un nom de fichier qui doit se situer dans le dossier
mowers/src/main/config. Ce fichier contient les instructions d'entrées telles que définies ci-dessus.
Par défault il s'agit du fichier input.txt


Coté MCV, des méthodes REST sont disponibles pour les Surfaces et les Unités
Pour tester le controller Surface controller, à partir du plugin firefox Mozilla Rest Client, avec les paramètres suivants:

**Interface REST sur les surfaces**  
GET (Liste)
URL: http://localhost:8080/mowers/rest/surface
Header HTTP: -
Body HTTP: -

POST (création)
URL: http://localhost:8080/mowers/rest/surface
Header HTTP: "Content-Type: application/json", "charset: utf-8"
Body HTTP: {"type":"jardin","name":"Mon petit jardin","mWidth":5,"mHeight":6}

PUT (modification)
URL: http://localhost:8080/mowers/rest/surface/[objectID]
Header HTTP: "Content-Type: application/json", "charset: utf-8"
Body HTTP: {"type":"jardin","name":"Mon plus si petit Jardin","mWidth":7,"mHeight":6}

DELETE (suppression)
URL: http://localhost:8080/mowers/rest/surface/[objectID]
Header HTTP: "charset: utf-8"
Body HTTP: -

**Interface REST sur les unités**  
GET (Liste)
URL: http://localhost:8080/mowers/rest/unit
Header HTTP: -
Body HTTP: -

POST (création)
URL: http://localhost:8080/mowers/rest/unit
Header HTTP: "Content-Type: application/json", "charset: utf-8"
Body HTTP: {"type":"mower","name":"Ma tondeuse"}

PUT (modification)
URL: http://localhost:8080/mowers/rest/unit/[objectID]
Header HTTP: "Content-Type: application/json", "charset: utf-8"
Body HTTP: {"type":"mower","name":"Ma tondeuse nouveau nom"}

DELETE (suppression)
URL: http://localhost:8080/mowers/rest/unit/[objectID]
Header HTTP: "charset: utf-8"
Body HTTP: -





## Considérations techniques - difficultés rencontrées ##

### Tests unitaires des fonctions CRUD sur les entités Surface, Unit et RoundOrders ###
Ces tests sont regroupés dans la classe MowersDbTest. Afin de pouvoir les lancer sans avoir installé (ou lancé) le serveur MongoDb,
j'ai tenté d'utiliser le package de.flapdoodle.embedmongo, qui permet de charger et lancer une instance de mongodb directement en runtime.
Toutefois, lors du lancement sur une nouvelle machine, le temps de chargement de MongoDb peut être long.
De plus sur Windows, des alerte de sécurité sont lancée pour permettre au serveur mongodb d'écouter sur le port configuré.
Enfin la solution ne semble pas toujours très stable...
Du coup, le fonctionnement actuel consiste à vérifier que MongoDB est installé avant exécuter les tests, et de
désactiver ces dernier en utilisant org.junit.Assume.assumeTrue dans le Setup du test.

### Mapping / conversion entre les objets du modèle et les DBObjet MongoDB ###
J'ai rencontré des difficultés, des exceptions étant provoquées dans module de conversion de Spring Data (package org.springframework.data.mapping),
lorque je faisais un findById sur mon modèle Surface:  

    MongoOperations mongoOperation = getMongoOperations();
    Surface savedSurface = mongoOperation.findById(objectId, Surface.class, "surfaces");  

il s'est avéré que c'était probablement dû au nom de certaines propriétés dans ma class Surface:

    protected int max_position_X;
le mapper de Spring Data interprétant le 'max' comme un mot clé... le nommage des propriétés en camel-case à réglé le problème: maxPositionX.

### Mapping / conversion entre les éléments en JSON transmis aux controlleurs et les objets du domaine(sérialisation / désérialisation) ###
La librairie utilisée pour parser le JSON et instancier les objets du domaines, dans un sens, et au contraire sérialiser les objets du domaine sous forme de flux JSon dans l'autre direction est Jackson.  
A priori pour que la désérialisation fonctionne, il faut définier un constructeur vide sur les objets de domaine (ce n'est pas idéal en terme d'architecture...).  

**Gestion du polymorphisme**: afin de ne garder qu'un seul point d'entrée dans les controlleurs pour la création / modification d'objets métiers en utilisant la classe abstraite (Unit et Surface), il faut préserver l'information sur la classe réelle d'une entité dans le flux JSON.  
Ceci est réalisé à l'aide d'annotations spécifiques à la librairie Jackson, associées à la classe abstraite:

    @JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="type")
    @JsonSubTypes({
    @JsonSubTypes.Type(value=Mower.class, name="mower")
    })
    public abstract class Unit {
    ...
    }
Le principe retenu ici consiste à ajouter une propriété dans la classe Unit, type, qui va spécifier le type de l'objet.
Les annotations ci-dessus permettent au parser Jackson d'utiliser la valeur de cette propriété pour connaitre la classe qui est associée à l'entité.  
Les constructeurs des classes héritées de Unit vont fournir la valeur adéquate (type="mower" pour la classe Mower).


### Supression d'un élément dans mongoDB ###
En réalisant mes tests unitaires, j'ai constaté que la méthode remove de mongoDB ne fonctionnait pas:

    mongoOperation.remove(surface, "surfaces");
Après avoir tout essayé, j'ai fini par découvrir qu'il s'agit bien d'un bug présent sur plusieurs releases de la librairie spring-data-mongodb 
> (https://jira.springsource.org/browse/DATAMONGO-346)

16/03/2013: n'ayant pas pu trouver une combinaison de releases ou le problème sur la méthode MongoTemplate.remove était corrigé, j'ai fini par trouver une alternative efficace en utilisant la méthode:


    MongoTemplate.findAndRemove

### Déployement avec le plugin tomcat-maven-plugin ###
Difficultés: url de deployement modifiée sur Tomcam 7 (http://localhost:8080/manager/text au lieu de http://localhost:8080/manager).  
Credential doivent être dans .m2/settings.xml (ceux dans le fichier config/settings.xml du répertoire d'installation de Maven ne semble pas pris en compte).  
Dans tomcat-users.xml, les droits sont: 


    <user username="admin" password="admin" roles="manager-script,manager-gui"/>
Attention a garder le meme nom de serveur entre settings.xml et le pom.xml.


### Test des méthodes REST avec l'extension Mozilla REST client: fonctionnement de la méthode POST ###
POST avec un enregistrement au format JSon a généré une erreur: 


    415 Type de Support Non Supporté", "405 - Request method 'POST' not supported"
il ne faut pas oublier de préciser les headers suivants:

    Content-Type: application/json
    charset: utf-8
No suitable constructor found for type [simple type, class ] (dans les logs): apparemment il est nécessaire de définir un constructeur par défaut, sans paramètres.


### Roadmap du projet ###
Travailler sur le projet 'client', avec angular.js. A priori ce sera un projet eclipse distinct, qui utilisera JSON pour communiquer avec l'application backend Spring MVC (interfaces spécifiées dans le tableau précédant).


### Références ###

- [Spring Data MongoDB (Documentation Springsource.org)](http://www.springsource.org/spring-data/mongodb)
- [Tutorial : REST with Spring MVC and Maven (16 mars 2012, David Gimelle)](http://getj2ee.over-blog.com/article-tutorial-rest-with-spring-mvc-and-maven-97283997.html)
- [REST in Spring 3: @MVC (Arjen Poutsma, 08/03/2009)](http://blog.springsource.com/2009/03/08/rest-in-spring-3-mvc)

