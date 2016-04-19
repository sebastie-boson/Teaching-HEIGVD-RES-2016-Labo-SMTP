# Teaching-HEIGVD-RES-2016-Labo-SMTP

## Brève description du projet

Ce projet permet d'envoyer des mails de plaisanterie à une liste définie de victimes (adresses mail) depuis des adresses mail qui font partie également de la liste des victimes.
Un nombre de groupes spécifié par l'utilisateur est créé à partir de la liste de victimes. Chacun de ces groupes est constitué d'au moins trois personnes : une personne qui envoie
le mail et les deux autres qui le recoivent. Ces deux personnes qui recoivent le mail auront l'impression que ce dernier provient de la première personne. De plus, celle-ci ne saura pas
que son adresse mail a été utilisée pour envoyer un mail de plaisanterie.
Pour chaque groupe, un mail est donc envoyé. Son contenu est défini aléatoirement à partir d'une liste de mails écrits par l'utilisateur.
La communication avec un serveur SMTP (adresse du serveur et numéro de port) est également effectué selon les paramètres définis par l'utilisateur.
Le code source du projet se trouve dans le répertoire nommé "laboSMTP".

## Instructions pour configuer le projet et lancer une campagne de mails de plaisanterie

Dans le répertoire "laboSMTP", on trouve un dossier nommé "config" qui contient déjà tous les fichiers nécessaires pour exécuter le projet.
Pour pouvoir lancer une campagne, il suffit juste de modifier les trois fichiers contenus dans ce dossier avec ses propres configurations.
Le fichier "config.properties" permet de définir l'adresse et le numéro de port du serveur SMTP à contacter pour l'envoi des mails. Il permet également de définir le nombre de groupes à créer
à partir de la liste de victimes ainsi que l'adresse mail de la personne qui sera témoin des mails envoyés (mise en copie).
Le fichier "victims.utf8" permet de définir la liste des adresses mails des victimes <br /> qui seront placées dans les différents groupes voulus.
Il est important de noter que le nombre minimal de victimes est de 3 personnes (addresses mail) par groupe et qu'il faut au moins demander la création d'un groupe pour ne pas avoir d'erreur lors
du lancement du projet et de la campagne.
Le dernier fichier "mail.utf8" permet simplement de définir le contenu des différents mails qui pourront être envoyés par un groupe. Il faut faire attention à séparer chaque mail par les caractères "=="
(même la dernier) et à ajouter pour la première ligne de chaque mail la balise "Subject: " avec le contenu du sujet du mail correspondant à la suite de cette balise.

Lorsque ces différents fichiers sont correctement remplis, il suffit de lancer le projet en compilant et en exécutant le code source fourni (classes). Normalement, si le serveur SMTP est atteignable, l'envoi
des mails de plaisanterie se fera sans problème. Les réponses du serveur SMTP s'afficheront sur la console. Par contre, si une erreur devait survenir, les informations sur cette erreur (exception) seront également
affichées sur la console pour l'indentifier et la corriger rapidement.

## Description de l'implémentation

Ci-dessous, nous retrouvons un diagramme de classe partiel qui se concentre sur les classes les plus importantes du programme.

![image](./figures/uml.png)

