# TP JAXRS / Jersey — Spring Boot (Banque/Comptes)

Ce TP illustre l’exposition d’une API REST JAX-RS (Jersey) embarquée dans une application Spring Boot, avec négociation de contenu JSON et XML via JAXB.


## Prérequis
- JDK 21
- Maven 3.9+
- Outil de test d’API (Postman, SoapUI) 


## Pile technique
- Spring Boot 3.x
- Jersey (`spring-boot-starter-jersey`)
- JPA/Hibernate + H2 en mémoire
- JAXB (marshalling JSON/XML côté Jersey via `jersey-media-jaxb`)


## Lancement
- Démarrage direct:
  ```bash
  ./mvnw spring-boot:run
  ```
- Ou build + exécution du JAR:
  ```bash
  ./mvnw clean package
  java -jar target/ma.ws-0.0.1-SNAPSHOT.jar
  ```
- Port: `8082` (cf. `application.properties`)

Application démarre avec des données de démonstration (3 comptes) injectées par `CommandLineRunner`.


## Configuration clé
- Enregistrement Jersey: `MyConfig#resourceConfig()` qui enregistre la ressource `CompteRestJaxRSAPI`.
- Base H2 en mémoire: `jdbc:h2:mem:banque`.
- Entité JAXB/JSON: `ma.ws.entities.Compte` annotée `@XmlRootElement` (pour XML) + Lombok.


## Ressources exposées
Base path: `http://localhost:8082/banque`

- GET `/comptes`
  - Produces: `application/json`, `application/xml`
  - Exemples:
    ```bash
    curl -H "Accept: application/json" http://localhost:8082/banque/comptes
    curl -H "Accept: application/xml"  http://localhost:8082/banque/comptes
    ```

- GET `/comptes/{id}`
  - Produces: `application/json`, `application/xml`
  - Exemples:
    ```bash
    curl -H "Accept: application/json" http://localhost:8082/banque/comptes/1
    curl -H "Accept: application/xml"  http://localhost:8082/banque/comptes/1
    ```

- POST `/comptes`
  - Consumes/Produces: `application/json`, `application/xml`
  - JSON:
    ```bash
    curl -X POST \
      -H "Content-Type: application/json" \
      -H "Accept: application/json" \
      -d '{
            "solde": 1500.0,
            "dateCreation": "2025-11-05",
            "type": "EPARGNE"
          }' \
      http://localhost:8082/banque/comptes
    ```
  - XML:
    ```bash
    curl -X POST \
      -H "Content-Type: application/xml" \
      -H "Accept: application/xml" \
      -d '<compte><solde>1500.0</solde><dateCreation>2025-11-05</dateCreation><type>EPARGNE</type></compte>' \
      http://localhost:8082/banque/comptes
    ```

- PUT `/comptes/{id}`
  - Consumes/Produces: `application/json`, `application/xml`
  - JSON:
    ```bash
    curl -X PUT \
      -H "Content-Type: application/json" \
      -H "Accept: application/json" \
      -d '{
            "solde": 1999.99,
            "dateCreation": "2025-11-05",
            "type": "COURANT"
          }' \
      http://localhost:8082/banque/comptes/1
    ```
  - XML:
    ```bash
    curl -X PUT \
      -H "Content-Type: application/xml" \
      -H "Accept: application/xml" \
      -d '<compte><solde>1999.99</solde><dateCreation>2025-11-05</dateCreation><type>COURANT</type></compte>' \
      http://localhost:8082/banque/comptes/1
    ```

- DELETE `/comptes/{id}`
  - Produces: `application/json`, `application/xml`
  - Exemples:
    ```bash
    curl -X DELETE -H "Accept: application/json" http://localhost:8082/banque/comptes/1
    curl -X DELETE -H "Accept: application/xml"  http://localhost:8082/banque/comptes/1
    ```


## Tests avec Postman / SoapUI
- Pour recevoir du XML: définir l’en-tête `Accept: application/xml`.
- Pour envoyer du XML: définir `Content-Type: application/xml` et fournir un body conforme à la structure `<compte>...</compte>`.
- Pour JSON: utiliser `Accept: application/json` et `Content-Type: application/json`.

Les captures fournies montrent:
- Postman: `GET http://localhost:8082/banque/comptes` avec `Accept=application/xml` en query params ou header (préférer le header) et réponse 200 OK.
- SoapUI: `GET http://localhost:8082/banque/comptes` avec header `Accept: application/xml` et payload XML formaté.


## Captures d’écran

- **Postman — GET /banque/comptes (Accept: application/xml)**

  ![1](https://github.com/user-attachments/assets/a65ee381-590b-41b8-95bc-aa40e4b50673)


- **SoapUI — GET /banque/comptes (Accept: application/xml)**

 
![2](https://github.com/user-attachments/assets/8774fe87-9686-4b51-8a72-35f3f527b2ad)


## Structure du projet (extrait)
- `ma.ws.Application` — point d’entrée Spring Boot + données de démo.
- `ma.ws.MyConfig` — configuration Jersey (`ResourceConfig`).
- `ma.ws.controller.CompteRestJaxRSAPI` — ressources JAX-RS (`@Path("/banque")`).
- `ma.ws.entities.Compte`, `TypeCompte` — modèle de domaine.
- `ma.ws.repositories.CompteRepository` — accès JPA.


## Notes et conseils
- Pour XML, l’annotation `@XmlRootElement` sur `Compte` est nécessaire pour le marshalling/demarshalling.
- Les dates sont sérialisées au format `yyyy-MM-dd` côté JSON/XML.
- H2 en mémoire: les données sont recréées à chaque démarrage.


## Troubleshooting
- 406 Not Acceptable: vérifier l’en-tête `Accept`.
- 415 Unsupported Media Type: vérifier `Content-Type` pour POST/PUT.
- 404: vérifier l’URL exacte (`/banque/comptes`), le port (`8082`) et l’ID.


## A propos de ce TP:
- Nom Complet : Ettouyjer Yasmine.
- Encadré par : Pr.Mohammed Lechegar.
- Etablissement : L'école normale supérieure de Marrakech ENS.
- Filiére : 2éme année master option ingénirie informatique.
