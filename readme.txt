Jean-Baptiste HUNTZINGER, 2013

Introduction
-------------
Cette application est inspirée du test d'entretien technique d'une société de conseil, dans le cadre d'un recrutement pour un poste
d'architecte J2EE.
Pour la petite histoire j'ai réalisé le test très (trop) rapidement, et je n'ai pas pu continuer le processus d'entretien...
Mais le correcteur m'ayant fait des remarques précises sur ce qui n'allait pas, j'ai décidé de réécrire ce test en élargissant
les spécifications d'origine et en profitant de l'occasion pour utiliser des outils open source actuels: Spring Source (le framework
que j'avais déjà utilisé, mais en 2005 !), MongoDB (Base de donnée NoSQL orientée documents éditée par une société américaine: 10gen)
et angular.js coté client (Google, framework javacript).
L'objectif pragmatique étant  de préparer mes entretiens suivants avec une référence récente sur ces technologies.


Les spécifications de l'application Mowers (= tondeuses en francais)
--------------------------------------------------------------------
Il s'agit de simuler le déplacement de d'unités mobile (des tondeuses, dans un premier temps) sur une surface (donc un jardin, rectangulaire 
dans un premier temps).

Pour expliquer simplement le but du projet, on part du cas le plus simple qui est traité par l'application: une surface rectangulaire que l'on appelle le jardin,
 et des tondeuses pour les unités mobiles. 
Le jardin à une longeur et une largeur, et chaque emplacement du jardin est représentée par une 'case' sous forme de coordonnée (x, y).
Chaque tondeuse est initialement placée sur une case (x1, y1), et orienté suivant une direction correspondant à un point cardinal: 'N'ord, 'S'ud, 'E'st, 'O'uest.
Par exemple une tondeuse sur la case (0,0) et orientée 'N' se trouve en bas à gauche du jardin, et tournée vers le nord.

Elle peut être déplacée selon des ordres, qui sont transmis sous forme de séries de mouvements unitaires:
'D' pour pivoter la tondeuse à droite
'G' pour pivoter la tondeuse à gauche
'A' pour avancer sur la case en face de la tondeuse

Pour une situation ou les unités sont forcemment des tondeuses, et la surface un jardin, l'application peut prendre en entrée un fichier ou un flux de texte,
qu'il interprète afin de créer un jardin, une ou plusieurs tondeuses, puis pour chaque tondeuse de la placer dans le jardin et lui appliquer une série de mouvements
unitaires.
La structure du fichier est la suivante:
En première ligne: largeur et longuer du jardin
Puis 2 lignes pour chaque tondeuse:
	ligne 1 tondeuse n: position initiale de la tondeuse: x,y et orientation 'N', 'S', 'E' ou 'O'
	ligne 2 tondeuse n: séquence d'ordres de déplacement associée à la tondeuse sous forme d'une série de lettres: 'D', 'G' ou 'A'

Exemple:
10 10
0 0 N
AADAA
5 5 S
AGAGAD

La première ligne indique la création d'un jardin de taille 10x10
Les 2 lignes suivantes indiquent la création et le positionnement d'une tondeuse sur la case (0,0) du jardin, tournée vers le nord.
La séquence correspond, sous forme de phrase: "Avance de 2 cases, pivote à droite, avance de 2 cases"
Idem pour les 2 dernières lignes qui définissent une tondeuse sur la case (5,5) du jardin, tournée vers le sud, et dont la
séquence correspond à "Avance d'une case, pivote à gauche, Avance d'une case, pivote à gauche, Avance d'une case, pivote à droite"

Après traitement de ce fichier par l'application, et exécution des ordres de déplacement,  les positions des tondeuses sont:
Tondeuse 1: coordonnées=(2,2) orientation='E'
Tondeuse 2: coordonnées=(6,5) orientation='E'

Les beans domaine
------------------
Surface (abstract), SurfaceRectangulaire, Jardin: représentation de la surface sur laquelle évoluent les unités
Unit (abstract), Mower: les unités mobile, dont les tondeuses
RoundOrders: entité regroupant une surfaces, les unités qui s'y trouvent et une série de mouvements unitaires sur chacune des unités
 

Installation et lancement de l'application
-------------------------------------------
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
Pour tester le controller Surface controller, à partir du plugin firefox Mozilla Rest Client, par exemple:
Test du GET: http://localhost:8080/mowers/rest/surface
Test du POST: http://localhost:8080/mowers/rest/surface avec le body {"type":"jardin","name":"Mon petit jardin","mWidth":5,"mHeight":6} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du PUT: http://localhost:8080/mowers/rest/surface/[objectID] avec le body {"type":"jardin","name":"Mon nouveau petit jardin","mWidth":7,"mHeight":6} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du DELETE: http://localhost:8080/mowers/rest/surface/[objectID]

Test du GET: http://localhost:8080/mowers/rest/unit
Test du POST: http://localhost:8080/mowers/rest/unit avec le body {"type":"mower","name":"Ma tondeuse"} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du PUT: http://localhost:8080/mowers/rest/unit/[objectID] avec le body {"type":"mower","name":"Ma tondeuse nouveau nom"} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du DELETE: http://localhost:8080/mowers/rest/unit/[objectID]

@TODO: écrire une application cliente, sous Angular.js


Considérations techniques - difficultés rencontrées
----------------------------------------------------

Tests unitaires des fonctions CRUD sur les entités Surface, Unit et RoundOrders:
ces tests sont regroupés dans la classe MowersDbTest. Afin de pouvoir les lancer sans avoir installé (ou lancé) le serveur MongoDb,
j'ai tenté d'utiliser le package de.flapdoodle.embedmongo, qui permet de charger et lancer une instance de mongodb directement en runtime.
Toutefois, lors du lancement sur une nouvelle machine, le temps de chargement de MongoDb peut être long.
De plus sur Windows, des alerte de sécurité sont lancée pour permettre au serveur mongodb d'écouter sur le port configuré.
Enfin la solution ne semble pas toujours très stable...
Du coup, le fonctionnement actuel consiste à vérifier que MongoDB est installé avant exécuter les tests, et de 
désactiver ces dernier en utilisant org.junit.Assume.assumeTrue dans le Setup du test. 

Conversion entre les objets du modèle et les DBObjet MongoDB: 
J'ai rencontré des difficultés, des exception étant provoquées dans module de conversion de Spring Data (package org.springframework.data.mapping),
lorque je faisait un findById sur mon modèle Surface:
	MongoOperations mongoOperation = getMongoOperations();
	Surface savedSurface = mongoOperation.findById(objectId, Surface.class, "surfaces");
il s'est avéré que c'était probablement dû au nom de certaines propriétés dans ma class Surface:
  protected int max_position_X;
le mapper de Spring Data interprétant le 'max' comme un mot clé... le nommage des propriétés en camel-case à réglé le problème: maxPositionX.

Supression d'un élément dans mongoDB:
en réalisant mes test unitaires, j'ai constaté que la méthode remove de mongoDB ne fonctionnait pas:
  mongoOperation.remove(surface, "surfaces");
Après avoir tout essayé, j'ai fini par découvrir qu'il s'agit bien d'un bug (https://jira.springsource.org/browse/DATAMONGO-346). Visiblement il sera corrigé dans la prochaine release de Spring Data Mongo... tant mieux !  
16/03/2013: Corrigé en utilisant la méthode MongoTemplate.findAndRemove

Déployement avec le plugin tomcat-maven-plugin:
difficultés: url de deployement modifiée sur Tomcam 7 (http://localhost:8080/manager/text au lieu de http://localhost:8080/manager)
Credential doivent être dans .m2/settings.xml (ceux dans le fichier config/settings.xml du répertoire d'installation de Maven ne semble pas pris en compte)
Dans tomcat-users.xml, les droits sont: <user username="admin" password="admin" roles="manager-script,manager-gui"/>
Attention a garder le meme nom de serveur entre settings.xml et le pom.xml  


Test des méthodes REST avec l'extension Mozilla REST client: fonctionnement de la méthode POST
POST avec un enregistrement au format JSon: "415 Type de Support Non Supporté", "405 - Request method 'POST' not supported": il ne faut pas oublier de précier les headers suivants:
  Content-Type: application/json
  charset: utf-8

No suitable constructor found for type [simple type, class ] (dans les logs): apparemment il faut absolument un constructeur par défaut, sans paramètres.

http://blog.springsource.com/2009/03/08/rest-in-spring-3-mvc/

Reste à faire
--------------
Travailler sur le projet 'client', avec angular.js. A priori ce sera un projet eclipse distinct, les vues du spring mvc renvoyant un 
flux JSon.



