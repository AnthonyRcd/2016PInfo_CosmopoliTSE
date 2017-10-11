# 2016PInfo_CosmopoliTSE

##Contexte du projet

Projet d'application informatique de 2ème année d'école d'ingénieurs ([Télécom Saint-Étienne](https://www.telecom-st-etienne.fr/)). Il s'agit d'un projet de groupe, réalisé par 4 collaborateurs: [Diane Bou-Zogheib](https://github.com/DianeBZ), [Sébastien Leang](https://github.com/leangsebastien), [Shule Li](https://github.com/Shuleee) et moi-même.

## Présentation du projet

>> 
* POUR les utilisateurs de Stack Overflow (http://stackoverflow.com/)
* QUI SOUHAITENT améliorer leur expérience
* NOTRE PRODUIT EST un outil de suggestion, présentation
personnalisée de l’information
* QUI permet d’avoir de nouvelles vues sur les données de
stackoverflow
* CONTRAIREMENT au site officiel qui n’est pas personnalisable
* NOTRE PRODUIT propose à chaque utilisateur des fonctionnalités
adaptées à son profil

Il s'agit donc d'un outil de personnalisation du site Stackoverflow selon 4 profils utilisateurs (Bob, Dave, Alice et Charlie), chacun ayant ses propres particularités.

### Personae Alice

Alice représente un utilisateur que l'on peut qualifier de développeur confirmé. En somme, elle contribue fréquemment sur stackOverflow, surtout pour faire des
réponses et elle collectionne les badges.

#### Users stories d'Alice
>> 
* Faire de nouvelles expertises
	+ Quelles sont les nouvelles questions dans mes
compétences ?
* Esprit de compétition
	+ Pour chacun de mes badges, quels autres utilisateurs
en ont plus ou autant que moi?
	+ Trier les questions auxquelles j’ai répondu en fonction
de leur taux de succès

### Personae Bob

Bob est un développeur débutant. Il cherche des réponses à ses problèmes, poste parfois des questions et de temps en temps note des réponses qu'il juge appropriées ou non (upvote/downvote).

#### Users stories de Bob
>>
* Rédaction de questions
	+ M’aider à trouver des questions existantes qui
correspondent à mon besoin
	+ Me suggérer des mots-clés à rajouter
* Follow experts
	+ Me proposer des contributeurs à suivre
* Follow topics
	+ M’indiquer les nouvelles questions (avec déjà des
réponses) dans mes champs d’intérêt

### Personae Charlie

Charlie est un chercheur. Il veut étudier le phénomène des sites de Q/A (Question Answer).

#### User stories de Charlie
>>
* Etudier les groupes d’utilisateurs
	+ Identifier des communautés d’intérêt
	+ Identifier des communautés géographiques (questions
et réponses d’un Q/A sont du même pays)
* Etudier les questions
	+ Grouper les questions à fort succès en fonction des
sujets traités

### Personae Dave

Dave est un chasseur de têtes. Il veut profiter des badges des contributeurs pour identifier des profils intéressants.

#### User stories de Dave
>>
* Trouver les personnes contribuant le plus à un
sujet donné
* Trouver la personne qui a le top tag dans un sujet
donné
* Trouver la personne qui contribue le plus à un
ensemble de tags donné

## Technologies et logiciels utilisés

* Langage de programmation: JAVA
	+ Librairies du package _java.net_ pour les connections HTTP
	+ Librairies des packages _java.awt_ et _javax.swing_ pour les interfaces graphiques
	+ Librairies du package _org.json_ pour la gestion des répones au format JSON
	+ Communication avec [l'API REST de stackexchange](https://api.stackexchange.com/docs)
* IDE: Eclipse
* Gestion de versions: Git & GitHub
* Gestion de projet Agile: Trello
* Communication et échange de fichiers: Facebook Messenger et Google Drive