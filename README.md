# sas2security
Sert à parser les méta-données de SAS pour faire un rapport sur les accès dans SAS

# Services
### GRAPH
Ce service permet de visualiser la chaine d'héritage d'un objet.
Les modes disponible sont :
- **user2groups** : Il permet de voir l'appartenance d'un utilisateur à des groupes. Le premier niveau indique
que l'utilisateur est un membre de direct du groupe. A partir du niveau 2, l'appartenance est héritée.
- **group2members** : Pour un groupe donné affiche les autres groupes qui sont héritiés du dit groupe.
- **group2parents** : Pour un groupe donnée affiches les liens dont hérite le dit gorupe.

### LIST
- **user2libs**: Affiche toutes les librairies visibles par l'utilisateur
- **user2groups** : Affiche tous les groupes dont l'utisateur est membre direct et membre par héritage
- **group2members** : Affiche tous les groupes qui héritent du groupe.
- **group2parents** : Affiche tous les groupes dont hérite le groupe.
- **group2libs** : Affiche toutes librairies visibles par les membres de ce groupes.
- **lib2groups** : Affiche les groupes pouvant voir la librairie.
- **lib2users** : Affiche les utilisateurs pouvant voir la librairie

La sortie affiche une liste de tous les éléments sans distinction hiérarchique. \
Spécifités :
- LIBRAIRIES : Le mapping avec les librairies n'est autorisé si et seulement si l'ensemble des permissions READ METADATA est autorisée.
- GROUPES : Le mapping ne se base que sur l'appartenance simple.

### MATRIX
- **user2libs** : Génére un fichier csv mappant les utilisateurs aux librairies qu'ils peuvent voir.
- **user2groups** : Génére un fichier csv mappant les utilisateurs aux groupes dont ils sont membres.
- **group2libs** : Génére un fichier csv mappant les groupes aux librairies qu'ils peuvent accéder.

Le fichier généré ordonne les données en suivant le schéma suivant : 

| user   | librairie A | librairie B | librairie C | librairie ... |
| ------ | ----------- | ----------- | ----------- | ------------- |
| USER 1 | false       | true        | false       | false         |
| USER 2 | true        | true        | false       | true          |

Le tableau est entièrement dynamique, l'ajout d'une nouvelle librairie dans SAS sera detecté et le programme créera une une nouvelle colonne automatiquement.
# Paramètres d'éxecution 

execution : java -jar sas-security-assembly.jar SERVICE --src /somePath/ --out /somePath/myfile.csv --[MODE] valeurCible \
SERVICE : **graph** | **list** | **dataset** \
MODE : **user2groups** | **user2libs** | **group2members** | **group2parents** | **group2libs** | **group2users** | **lib2groups** | **lib2users** 

- --help : Affiche le message d'aide.

- -f, --src \<value\>: Doit indiquer un dossier contant 6 fichiers décrivant :
    - AccessControlEntry
    - IdentityGroup
    - SASLibrary
    - Permission
    - Person
    - Tree
    
- -o, --out :[specifique au service dataset] Le chemin du fichier de sortie.

- --MODE \<value\> : 
    - [specifique services graph, list] : la valeur "all" lance le traitement pour tous les objets sur mode;
    - [specifique mode user2...]: la valeur est le trigramme de la personne.
    - [specifique mode group2...]: la valeur est le nom du groupe.
    - [specifique mode lib2...]: la valeur est le nom d'affichage de la libraire (ex "VA - L - M - ..."). Il est nécessaire mettre entre quotes la valeur à cause des espaces des noms.

    