## LSP-based Graphical Language Server

LSP infrastructure for the generation of graphical language servers

## How to use

It's a maven project with two profiles: lsp4j and lsp4jcors

Steps for profile lsp4j:

- add local dep for language: mvn install:install-file -Dfile=../Family/familymodel.jar -DgroupId=family -DartifactId=family -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true

- compile: mvn compile -P lsp4j

- run: mvn exec:java -Dexec.mainClass="es.unex.quercusseg.graphicalserver.LanguageServerLauncher" -P lsp4j

Steps for profile lsp4jcors:

- add local dep for language: mvn install:install-file -Dfile=../Family/familymodel.jar -DgroupId=family -DartifactId=family -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true

- add lsp4jcors: mvn install:install-file -Dfile=lsp4jcors.jar -DgroupId=lsp4jcors -DartifactId=lsp4jcors -Dversion=0.4.0 -Dpackaging=jar -DgeneratePom=true

- compile: mvn compile -P lsp4jcors

- run: mvn exec:java -Dexec.mainClass="es.unex.quercusseg.graphicalserver.LanguageServerLauncher" -P lsp4jcors
