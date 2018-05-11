
# lsp4gml

This is the repository of the proof-of-concept prototype to test our LSP infrastructure. 

The prototype includes:
* A distributable generic client, which has been developed as a website
* A graphical language server, which relies on the Eclipse platform. 

Both client editor and language server use LSP  and the Intermediate Representation Format (IRF), a JSON-based intermediate format to specify graphical models in a textual format, to communicate each other and represent graphical modeling language instances, respectively.

An online version of the client/server can be found [here](http://som-research.uoc.edu/tools/lsp4gml/index.html). Please note that it is the online testing platform of the tool. You can always clone this repo and try it locally.

## The client

### Description
The website developed for the client side part has been implemented using standard Web technologies (i.e., HTML, CSS, SVG and JavaScript). The diagramming support of the client editor relies on [JointJS](https://www.jointjs.com), a free diagramming library developed in JavaScript.  

The client keeps an instance of the graphical modeling language expressed in IRF, which is synchronized with the server by using LSP. Thus, events and editing actions in the diagram update the IRF definition of the instance.

To enable genericity in the client, the symbols of its diagram palette are retrieved from the server. Server publishes a set of SVG-based templates for the language concepts via a specific command for LSP, while client renders language symbols by injecting the information represented in the IRF definition into the SVG templates. These templates are used to configure the JointJS-based diagram editor, thus the user can drag&drop them to create instances of the graphical language.

### Installation and Use

To use the client, you have to deploy the website, we recommend to use the simple HTTP server provided in Python 2.7 installation (you can install it [here](https://www.python.org/download/releases/2.7/)). Please follow these steps:

* Go to the ```client``` folder
* Execute ```python -m SimpleHTTPServer```, which will create a simple HTTP server at the 8080 port
* Open a web browser and go to http://localhost:8080

The default implementation of the client will connect to the server located at localhost:8000


## The server

### Description

The language server relies on the Eclipse Modeling Framework (EMF) and the Graphical Modeling Framework (GMF) to provide model management, validation and storage of graphical languages.
While EMF offers the core support to manage and validate models and metamodels, GMF provides the required support to define the concrete syntax of graphical languages.

### Instalation and Use
The server has been developed as a maven project (you can install it [here](https://maven.apache.org)), to compile and execute the server, follow these steps:

* add local dep for language: mvn install:install-file -Dfile=../examples/family/familymodel.jar -DgroupId=family -DartifactId=family -Dversion=1.0 -Dpackaging=jar -DgeneratePom=true
* add lsp4jcors: mvn install:install-file -Dfile=lsp4jcors.jar -DgroupId=lsp4jcors -DartifactId=lsp4jcors -Dversion=0.4.0 -Dpackaging=jar -DgeneratePom=true
* compile: mvn compile -P lsp4jcors
* run: mvn exec:java -Dexec.mainClass="es.unex.quercusseg.graphicalserver.LanguageServerLauncher" -P lsp4jcors
