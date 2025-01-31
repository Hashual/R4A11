# R4A.11
Module de développement d'application mobile - IUT

## TP 1
### Question 1 : Où se trouve le fichier .xml décrivant l'interface graphique. Donner son nom et son emplacement dans l'arborescence du projet
Le fichier se trouve dans `MyApplication4\app\src\main\res\layout\activity_main.xml`.

### Question 2 : Qu'avez-vous modifier pour changer le texte *Hello world* en *Coucou j'ai trouvé comment faire*
L'attribut `text` du composant en question.

### Question 3 : Quel fichier et ligne modifier pour change l'icone de l'application ?
J'ai rajouté le fichier contenant le nouveau logo dans `MyApplication4\app\src\main\res\mipmap\logo.png` puis j'ai modifié les chemins de `android:icon` et `android:roundIcon` dans le fichier `MyApplication4/app/src/main/AndroidManifest.xml`.

### Question 4 : Est-ce nécessaire de cliquer sur le bouton valider pour afficher le texte saisi sur la seconde activité ? Pourquoi ?
Actuellement non car nous n'avons pas fait de vérification sur le bouton `next` qui vérifie que le bouton valider a bien été cliqué au préalable.

### Question 5 : Le comportement de la question 4 vous semble-t-il normal ?
Non, il faudrait pouvoir dans un premier temps valider notre saisie avant de pouvoir passer à la page suivante.

### Question 6 : Comment faire pour ne pas afficher le nouveau texte sur la deuxième activité tant que le bouton valider n'a pas été cliqué ?
Il faut déclarer une nouvelle variable à `false` par défaut, la mettre à `true` lorsque le bouton valider est cliqué puis, dans le listener du bouton `next`, vérifier avant de changer de page que notre variable est bien à `true`.
