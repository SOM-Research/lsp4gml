## LSP-based Graphical Language Server

LSP infrastructure for the generation of graphical language servers

## How to use

It's a maven project:

- add local dep for language: mvn install:install-file -Dfile=../Family/familymodel.jar -DgroupId=family -DartifactId=family -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true

- compile: mvn compile

- run: mvn exec:java -Dexec.mainClass="es.unex.quercusseg.graphicalserver.LanguageServerLauncher"
