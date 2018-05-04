
# lsp4gml

This is the repository of the proof-of-concept prototype to test our LSP infrastructure. 

The prototype includes:
* A distributable generic client, which has been developed as a website
* A graphical language server, which relies on the Eclipse platform. 

Both client editor and language server use LSP  and the Intermediate Representation Format (IRF), a JSON-based intermediate format to specify graphical models in a textual format, to communicate each other and represent graphical modeling language instances, respectively.

## The client

### Description
The website developed for the client side part has been implemented using standard Web technologies (i.e., HTML, CSS, SVG and JavaScript). The diagramming support of the client editor relies on [JointJS](https://www.jointjs.com), a free diagramming library developed in JavaScript.  

The client keeps an instance of the graphical modeling language expressed in IRF, which is synchronized with the server by using LSP. Thus, events and editing actions in the diagram update the IRF definition of the instance.

To enable genericity in the client, the symbols of its diagram palette are retrieved from the server. Server publishes a set of SVG-based templates for the language concepts via a specific command for LSP, while client renders language symbols by injecting the information represented in the IRF definition into the SVG templates. These templates are used to configure the JointJS-based diagram editor, thus the user can drag&drop them to create instances of the graphical language.

### Installation and Use
The implementation of the client is located at the ```client``` folder. 

## The server

### Description

The language server relies on the Eclipse Modeling Framework (EMF) and the Graphical Modeling Framework (GMF) to provide model management, validation and storage of graphical languages.
While EMF offers the core support to manage and validate models and metamodels, GMF provides the required support to define the concrete syntax of graphical languages.

### Instalation and Use
The server has been implemented as a Java application in the Eclipse platform. It therefore relies on the [Eclipse Modeling Framework](http://www.eclipse.org/modeling/emf/) and also in the [LSP4J library](https://github.com/eclipse/lsp4j). 
