Jean-Baptiste HUNTZINGER, 2013

Introduction
-------------
Cette application est inspir�e du test d'entretien technique d'une soci�t� de conseil, dans le cadre d'un recrutement pour un poste
d'architecte J2EE.
Pour la petite histoire j'ai r�alis� le test tr�s (trop) rapidement, et je n'ai pas pu continuer le processus d'entretien...
Mais le correcteur m'ayant fait des remarques pr�cises sur ce qui n'allait pas, j'ai d�cid� de r��crire ce test en �largissant
les sp�cifications d'origine et en profitant de l'occasion pour utiliser des outils open source actuels: Spring Source (le framework
que j'avais d�j� utilis�, mais en 2005 !), MongoDB (Base de donn�e NoSQL orient�e documents �dit�e par une soci�t� am�ricaine: 10gen)
et angular.js cot� client (Google, framework javacript).
L'objectif pragmatique �tant  de pr�parer mes entretiens suivants avec une r�f�rence r�cente sur ces technologies.


Les sp�cifications de l'application Mowers (= tondeuses en francais)
--------------------------------------------------------------------
Il s'agit de simuler le d�placement de d'unit�s mobile (des tondeuses, dans un premier temps) sur une surface (donc un jardin, rectangulaire 
dans un premier temps).

Pour expliquer simplement le but du projet, on part du cas le plus simple qui est trait� par l'application: une surface rectangulaire que l'on appelle le jardin,
 et des tondeuses pour les unit�s mobiles. 
Le jardin � une longeur et une largeur, et chaque emplacement du jardin est repr�sent�e par une 'case' sous forme de coordonn�e (x, y).
Chaque tondeuse est initialement plac�e sur une case (x1, y1), et orient� suivant une direction correspondant � un point cardinal: 'N'ord, 'S'ud, 'E'st, 'O'uest.
Par exemple une tondeuse sur la case (0,0) et orient�e 'N' se trouve en bas � gauche du jardin, et tourn�e vers le nord.

Elle peut �tre d�plac�e selon des ordres, qui sont transmis sous forme de s�ries de mouvements unitaires:
'D' pour pivoter la tondeuse � droite
'G' pour pivoter la tondeuse � gauche
'A' pour avancer sur la case en face de la tondeuse

Pour une situation ou les unit�s sont forcemment des tondeuses, et la surface un jardin, l'application peut prendre en entr�e un fichier ou un flux de texte,
qu'il interpr�te afin de cr�er un jardin, une ou plusieurs tondeuses, puis pour chaque tondeuse de la placer dans le jardin et lui appliquer une s�rie de mouvements
unitaires.
La structure du fichier est la suivante:
En premi�re ligne: largeur et longuer du jardin
Puis 2 lignes pour chaque tondeuse:
	ligne 1 tondeuse n: position initiale de la tondeuse: x,y et orientation 'N', 'S', 'E' ou 'O'
	ligne 2 tondeuse n: s�quence d'ordres de d�placement associ�e � la tondeuse sous forme d'une s�rie de lettres: 'D', 'G' ou 'A'

Exemple:
10 10
0 0 N
AADAA
5 5 S
AGAGAD

La premi�re ligne indique la cr�ation d'un jardin de taille 10x10
Les 2 lignes suivantes indiquent la cr�ation et le positionnement d'une tondeuse sur la case (0,0) du jardin, tourn�e vers le nord.
La s�quence correspond, sous forme de phrase: "Avance de 2 cases, pivote � droite, avance de 2 cases"
Idem pour les 2 derni�res lignes qui d�finissent une tondeuse sur la case (5,5) du jardin, tourn�e vers le sud, et dont la
s�quence correspond � "Avance d'une case, pivote � gauche, Avance d'une case, pivote � gauche, Avance d'une case, pivote � droite"

Apr�s traitement de ce fichier par l'application, et ex�cution des ordres de d�placement,  les positions des tondeuses sont:
Tondeuse 1: coordonn�es=(2,2) orientation='E'
Tondeuse 2: coordonn�es=(6,5) orientation='E'

Les beans domaine
------------------
Surface (abstract), SurfaceRectangulaire, Jardin: repr�sentation de la surface sur laquelle �voluent les unit�s
Unit (abstract), Mower: les unit�s mobile, dont les tondeuses
RoundOrders: entit� regroupant une surfaces, les unit�s qui s'y trouvent et une s�rie de mouvements unitaires sur chacune des unit�s
 

Installation et lancement de l'application
-------------------------------------------
L'application Mowers est r�alis� sous Maven2, elle peut donc �tre install�e de mani�re simple par exemple et saisissant la ligne de commandes:
	mvn install 
dans le r�pertoire du projet.

Mowers utilise Spring MVC, avec des controlleurs pour les Surfaces, les Unit�s, les RoundOrders.
Le d�ployment sous un serveur Tomcat local peut se faire avec la commande:
	mvn tomcat:deploy (ou mvn tomcat:redeploy)

Des tests unitaires sont �crits, que l'on peut lancer � l'aide d'eclipse ('Run configuration' de type JUnit) ou de maven (mvn test).
La lancement de l'application sous eclipse ou en ligne de commande prend en argument un nom de fichier qui doit se situer dans le dossier
mowers/src/main/config. Ce fichier contient les instructions d'entr�es telles que d�finies ci-dessus.
Par d�fault il s'agit du fichier input.txt
 

Cot� MCV, des m�thodes REST sont disponibles pour les Surfaces et les Unit�s
Pour tester le controller Surface controller, � partir du plugin firefox Mozilla Rest Client, par exemple:
Test du GET: http://localhost:8080/mowers/rest/surface
Test du POST: http://localhost:8080/mowers/rest/surface avec le body {"type":"jardin","name":"Mon petit jardin","mWidth":5,"mHeight":6} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du PUT: http://localhost:8080/mowers/rest/surface/[objectID] avec le body {"type":"jardin","name":"Mon nouveau petit jardin","mWidth":7,"mHeight":6} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du DELETE: http://localhost:8080/mowers/rest/surface/[objectID]

Test du GET: http://localhost:8080/mowers/rest/unit
Test du POST: http://localhost:8080/mowers/rest/unit avec le body {"type":"mower","name":"Ma tondeuse"} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du PUT: http://localhost:8080/mowers/rest/unit/[objectID] avec le body {"type":"mower","name":"Ma tondeuse nouveau nom"} et les 2 headers "Content-Type: application/json", "charset: utf-8"
Test du DELETE: http://localhost:8080/mowers/rest/unit/[objectID]

@TODO: �crire une application cliente, sous Angular.js


Consid�rations techniques - difficult�s rencontr�es
----------------------------------------------------

Tests unitaires des fonctions CRUD sur les entit�s Surface, Unit et RoundOrders:
ces tests sont regroup�s dans la classe MowersDbTest. Afin de pouvoir les lancer sans avoir install� (ou lanc�) le serveur MongoDb,
j'ai tent� d'utiliser le package de.flapdoodle.embedmongo, qui permet de charger et lancer une instance de mongodb directement en runtime.
Toutefois, lors du lancement sur une nouvelle machine, le temps de chargement de MongoDb peut �tre long.
De plus sur Windows, des alerte de s�curit� sont lanc�e pour permettre au serveur mongodb d'�couter sur le port configur�.
Enfin la solution ne semble pas toujours tr�s stable...
Du coup, le fonctionnement actuel consiste � v�rifier que MongoDB est install� avant ex�cuter les tests, et de 
d�sactiver ces dernier en utilisant org.junit.Assume.assumeTrue dans le Setup du test. 

Conversion entre les objets du mod�le et les DBObjet MongoDB: 
J'ai rencontr� des difficult�s, des exception �tant provoqu�es dans module de conversion de Spring Data (package org.springframework.data.mapping),
lorque je faisait un findById sur mon mod�le Surface:
	MongoOperations mongoOperation = getMongoOperations();
	Surface savedSurface = mongoOperation.findById(objectId, Surface.class, "surfaces");
il s'est av�r� que c'�tait probablement d� au nom de certaines propri�t�s dans ma class Surface:
  protected int max_position_X;
le mapper de Spring Data interpr�tant le 'max' comme un mot cl�... le nommage des propri�t�s en camel-case � r�gl� le probl�me: maxPositionX.

Supression d'un �l�ment dans mongoDB:
en r�alisant mes test unitaires, j'ai constat� que la m�thode remove de mongoDB ne fonctionnait pas:
  mongoOperation.remove(surface, "surfaces");
Apr�s avoir tout essay�, j'ai fini par d�couvrir qu'il s'agit bien d'un bug (https://jira.springsource.org/browse/DATAMONGO-346). Visiblement il sera corrig� dans la prochaine release de Spring Data Mongo... tant mieux !  
16/03/2013: Corrig� en utilisant la m�thode MongoTemplate.findAndRemove

D�ployement avec le plugin tomcat-maven-plugin:
difficult�s: url de deployement modifi�e sur Tomcam 7 (http://localhost:8080/manager/text au lieu de http://localhost:8080/manager)
Credential doivent �tre dans .m2/settings.xml (ceux dans le fichier config/settings.xml du r�pertoire d'installation de Maven ne semble pas pris en compte)
Dans tomcat-users.xml, les droits sont: <user username="admin" password="admin" roles="manager-script,manager-gui"/>
Attention a garder le meme nom de serveur entre settings.xml et le pom.xml  


Test des m�thodes REST avec l'extension Mozilla REST client: fonctionnement de la m�thode POST
POST avec un enregistrement au format JSon: "415 Type de Support Non Support�", "405 - Request method 'POST' not supported": il ne faut pas oublier de pr�cier les headers suivants:
  Content-Type: application/json
  charset: utf-8

No suitable constructor found for type [simple type, class ] (dans les logs): apparemment il faut absolument un constructeur par d�faut, sans param�tres.

http://blog.springsource.com/2009/03/08/rest-in-spring-3-mvc/

Reste � faire
--------------
Travailler sur le projet 'client', avec angular.js. A priori ce sera un projet eclipse distinct, les vues du spring mvc renvoyant un 
flux JSon.



