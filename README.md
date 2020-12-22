# SUshark



## Main.java
Ce fichier sert uniquement de point d'entrée du programme.
Il peut être remplacé/modifié pour lancer ce dernier avec une interface graphique.


## Analyzer.java
Ce fichier est responsable d'ouvrir la trace, l'analyser, la formater et sauvegardé le résultat dans un fichier texte.


## Rawframe.java
Cet objet est une liste d'octet issue du formatage de l'analyseur.


## Frame.java
Correspond à la couche Link.
Possède en attribut les valeurs de l'entête ainsi qu'un objet Datagram.


## Datagram.java
Correspond à la couche Network.
Possède en attribut les valeurs de l'entête ainsi qu'un objet Segment.


## Segment.java
Correspond à la couche Transport.
Possède en attribut les valeurs de l'entête ainsi qu'un objet Segment.


## Message.java
Correspond à la couche Application.
Dans notre cas possède les données du protocol HTTP.


## Les exceptions
- Les 2 fichiers suivants correspondent à des exceptions créées pour les besoins spécifique du projet:
- `BadFrameFormatException.java`
- `BadOffsetException.java`
