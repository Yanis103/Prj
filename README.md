# Prj

## Équipe de Développement
- Yani LHADJ
- Yanis MEHMEL

## Compilation et Exécution du Projet

### Utilisation de la Base de Données Hébergée
Si vous souhaitez utiliser la base de données hébergée sur un serveur gratuit, assurez-vous d'avoir une connexion Internet : 
Si l'outil make est installé sur votre système : 
    make run
Sinon : 
    java -jar LHADJ-MAHMEL.jar

### Utilisation de la Base de Données Locale
Si vous souhaiter utiliser une base de données en local il suffit de suivre les instrcutions suivante :
1. Mettre en commentaire la partie qui gère la connexion vers la base de données hébergée et décommenter la partie qui gère la connexion avec la base de données locale.

2. Créer la base de données en utilisant les commandes SQL présentes dans les fichiers :
   - Pro\src\main\resources\Création_tables.sql
   - Pro\src\main\resources\Insertions_tables.sql
   Commandes :
   - ```sql source Création_tables.sql
   - ```sql source Insertions_tables.sql  

3. Modifier les identifiants de connexion à la base de données dans :
   - Pro\src\main\java\dao\Connexion.java

   ```java
   static final String JDBC_URL = "jdbc:mysql://localhost:3306/Projet";
   static final String DB_USER = "root";
   static final String DB_PASSWORD = "";

4. Ouvrir un terminal et se positionner dans le répertoire "Pro" 

5. Exécuter la commande Maven suivante pour compiler le projet et générer le JAR :
mvn clean package

6. Exécuter le JAR généré (présent dans le répertoire "target") en utilisant la commande :
java -jar Pro-0.0.1-SNAPSHOT-jar-with-dependencies.jar

## Remerciements

Nous tenons à exprimer nos sincères remerciements à nos professeurs, Madame Mathlouthi Emna et Madame Zallama Khaddouja, pour leur précieux encadrement, leurs conseils avisés et leur soutien tout au long du développement de ce projet. Leurs enseignements et leur dévouement ont grandement contribué à notre réussite.
Nous sommes reconnaissants pour l'opportunité d'avoir bénéficié de leur expertise et de leur guidance, qui ont été essentielles pour la réalisation de ce projet. Merci pour votre inspiration et votre engagement envers notre apprentissage.

---
