# ChatRoom
Description du projet :
Développement d'une salle de discussions (Chat room). Dans la salle, plusieurs utilisateurs peuvent se
connecter et mener des conversations, soit d’un à un soit en groupe. Dans la salle, les conversations sont
sauvegardées de manière persistante, afin que les utilisateurs trouvent toujours l’enregistrement de leurs
conversations précédentes


Membres du groupe et rôles :
- Brandon RAMMOHAN
  - Superviseur de toute la partie développement

- Zachary FRADE COSTA :
   - Responsable de la partie sauvegarde des messages et des utilisateurs de chaque groupe
   - Responsabe de la partie MVC
    
- Imane TAABDANTE :
    - Responsable test Unitaire
    - Reponsable de la rédaction de l'organisation du projet et du cahier des charges 
    
    
- Souleimane BOULANOUAR
    - Responsable du plan de test
    - Responsable du script Maven
    
    
Outils utilisé :
- IDE Eclipse
- GitHub
- Trello
- Plug-ins : JUnit - Maven - Windows Builder

Execution du projet : Du à un manque de temps nous n'avons pas pu développer le script Maven qui execute automatiquement les classes donc voici les démarches à suivre :
  1) Récupérer le projet avec git clone
  2) Lancer le fichier src/Serveur/ServeurLaunch.java pour lancer le serveur
  3) Lancer le fichier src/Client/ClientLaunch.java pour lancer l'interface ChatRoom
  4) Pour les classes tests clique-droit sur le package src/Client/Test et cliqué sur "Covergage As" => JUnit Test 
