<div align="center">
  <h1 align="center">Tomuss API Wrapper for Java</h1>

### Obtenir ses notes de Tomuss
![Java](https://img.shields.io/badge/java-43853D?style=for-the-badge&logo=openjdk&logoColor=white)
![Gradle](https://img.shields.io/badge/gradle-007ACC?style=for-the-badge&logo=gradle&logoColor=white)
![Github stars](https://img.shields.io/github/stars/MohistMC/Website?style=for-the-badge)
</div>

## A propos

Ce projet est un wrapper Java pour Tomuss. Il permet de récupérer les semestres et les notes d'un étudiant (en se connectant via le CAS).       
Il existe aussi un [wrapper Tomuss en TypeScript](https://github.com/Shawiizz/tomuss-api-wrapper-js).

## Pour commencer
Pour installer en tant que dépendance Maven / Gradle, vous devez d'abord build le projet avec Gradle (voir instructions ci-dessous).

### Contribuer / Modifier le code source
Clonez le repository et installez les dépendances :

```bash
git clone https://github.com/Shawiizz/tomuss-api-wrapper-java.git
cd tomuss-api-wrapper-java
```

Setup le projet avec gradle (devrait se faire automatiquement sur IntelliJ IDEA) :
```bash
./gradlew.bat build
```

Pour obtenir le jar projet, exécutez :
```bash
./gradlew.bat shadowJar
```
Tout les fichiers compilés se trouvent dans le dossier `build/libs/` (le fichier -all devrait contenir toutes les dépendances du projet).

## Exemples

### Comment obtenir ses notes
```typescript
// Pour se connecter via le CAS
CASAuthService authService = new CASAuthService();
authService.login("username", "password");

// Pour obtenir les semestres disponibles
TomussService tomussService = TomussService.withAuth(authService);
List<Semester> semesters = tomussService.getAvailableSemesters();

// Obtenir les modules (matières) d'un ou plusieurs semestres
Semester firstSemester = semesters.get(0);
// La méthode getModules() peut prendre plusieurs semestres en argument si nécéssaire.
List<TomussModule> modules = tomussService.getModules(firstSemester); 
// Traitez vos modules (voir les propriétés de TomussModule)
```