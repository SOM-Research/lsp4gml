package es.unex.quercusseg.graphicalserver.connector;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.lsp4j.Diagnostic;
import org.eclipse.lsp4j.DiagnosticSeverity;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import es.unex.quercusseg.graphicalserver.model.*;
import es.unex.quercusseg.graphicalserver.util.*;

import family.model.family.NamedElement;


public class DiagnosticConnector {

	//Logging instance
	private static Logger logger = Logging.getInstance().getLogger();

	private int    severity;
	private String resource;
	
	
	public void setSeverity(int _severity) {
		
		this.severity = _severity;
		
	}

	
	public void setResource(String _resource) {
	
		this.resource = _resource;
		
	}

	
	public int getSeverity() {
		
		return severity;
	
	}

	
	public String getResource() {
		
		return this.resource;
		
	}

	
	public Boolean didError() {
	
		return (this.severity == org.eclipse.emf.common.util.Diagnostic.ERROR);
		
	}
	
	
	public List <Diagnostic> getDiagnostics(org.eclipse.emf.common.util.Diagnostic emfDiagnostic) {
		
		Diagnostic diagnostic = new Diagnostic();
		
		diagnostic.setCode(String.valueOf(emfDiagnostic.getCode()));
		diagnostic.setSource(emfDiagnostic.getSource());
		diagnostic.setRange(new Range(new Position(0,0), new Position(110, 0)));
		diagnostic.setMessage(emfDiagnostic.toString());
		
		/**
		 * EMF SEVERITY CODES: 				CANCEL, ERROR, INFO, OK, WARNING 
		 * LSP4J DIAGNOSTIC SEVERITY CODES: Error, Warning, Information, Hint
		 */	
		switch(emfDiagnostic.getSeverity()) {
		
			case org.eclipse.emf.common.util.Diagnostic.CANCEL:
			
				diagnostic.setSeverity(DiagnosticSeverity.Hint);
				break;

			case org.eclipse.emf.common.util.Diagnostic.ERROR:
				
				diagnostic.setSeverity(DiagnosticSeverity.Error);
				break;
			
			case org.eclipse.emf.common.util.Diagnostic.INFO:
			case org.eclipse.emf.common.util.Diagnostic.OK:
			
				diagnostic.setSeverity(DiagnosticSeverity.Information);
				break;

			case org.eclipse.emf.common.util.Diagnostic.WARNING:
				
				diagnostic.setSeverity(DiagnosticSeverity.Warning);
				break;

			
		}
		
		
		List <Diagnostic> diagnostics = new ArrayList <Diagnostic> ();
		diagnostics.add(diagnostic);
		
		logger.info(diagnostic.toString());
		
		return diagnostics;
		
	}
	
	
	public org.eclipse.emf.common.util.Diagnostic validateModel(String jsonResource) {
		
		JsonParser parser     = new JsonParser();
		Gson gson 		      = new Gson();
		
		org.eclipse.emf.common.util.Diagnostic diagnostic = null;
		
		try {
			
			logger.info("Parsing IRF file '" + jsonResource + "'...");
			
			//Root object of the json file
			JsonObject rootObject = parser.parse(new FileReader(jsonResource)).getAsJsonObject();
				
			//Getting Json objects regarding model information
			String rootElement 		   = rootObject.get("rootElement").getAsString();
			String namedElementPackage = rootObject.get("namedElementPackage").getAsString();
			String implPackage 		   = rootObject.get("implPackage").getAsString();

			//Getting elements array
			JsonArray nodes = rootObject.getAsJsonArray("nodes");
			JsonArray edges = rootObject.getAsJsonArray("edges");
			
	        //List of nodes and edges we've got
	        List <Node> listOfNodes = new ArrayList <Node> ();
	        List <Edge> listOfEdges = new ArrayList <Edge> ();
        	        
	        //Parse jsonFile for them nodes
	        for(JsonElement jsonElement : nodes) {
	        	
	        	JsonObject object = jsonElement.getAsJsonObject();
	        	Node       node   = new Node();
	        	
	        	node.setId(object.get("id").getAsInt());
	        	node.setType(object.get("type").getAsString());
	        	
	        	//Abstract properties
	        	JsonArray   abstractArray      = object.get("abstract").getAsJsonArray();
	        	List <Pair> abstractProperties = new ArrayList <Pair> ();
	        	
	        	for(JsonElement pairs : abstractArray) {
	        		
	        		JsonObject pairObject = pairs.getAsJsonObject();
	        		Pair pair = gson.fromJson(pairObject, Pair.class);
	        		abstractProperties.add(pair);
	        		
	        	}
	        	
	        	node.setAbstractProperties(abstractProperties);
	        		
	        	//Diagram
	        	JsonObject diagramObject = object.get("diagram").getAsJsonObject();
	        	int x = diagramObject.get("x").getAsInt();
	        	int y = diagramObject.get("y").getAsInt();
	        	int z = diagramObject.get("z").getAsInt();
	        	
	        	Diagram diagram = new Diagram(x, y, z);
	        	
	        	node.setDiagram(diagram);

	        	listOfNodes.add(node);
	        	
	        }
	        
			logger.info("Nodes list...");
			logger.info(listOfNodes.toString());

	        //Parse jsonFile for them edges
	        for(JsonElement jsonElement : edges) {
	        	
	        	JsonObject object = jsonElement.getAsJsonObject();
	        	Edge       edge   = new Edge();
	        	
	        	edge.setId(object.get("id").getAsInt());
	        	edge.setType(object.get("type").getAsString());
	        	edge.setOrigin(object.get("origin").getAsString());
	        	edge.setTarget(object.get("target").getAsString());
	        	
	        	//Abstract properties
	        	JsonArray   abstractArray      = object.get("abstract").getAsJsonArray();
	        	List <Pair> abstractProperties = new ArrayList <Pair> ();
	        	
	        	for(JsonElement pairs : abstractArray) {
	        		
	        		JsonObject memberEndObject = pairs.getAsJsonObject();
	        		Pair       pair             = gson.fromJson(memberEndObject, Pair.class);
	        		abstractProperties.add(pair);
	        		
	        	}
	        	
	        	edge.setAbstractProperties(abstractProperties);
	        	
	        	listOfEdges.add(edge);
	        	
	        }

			logger.info("Edges list...");
			logger.info(listOfEdges.toString());

	        //Instantiate appropriate objects
	        Factory factory 		         = new Factory();
	        List <NamedElement> modelObjects = factory.instantiateObjets(listOfNodes, namedElementPackage, implPackage);
	        	     
			logger.info("Objects list...");
			logger.info(modelObjects.toString());

	        ValidateModel model = new ValidateModel();
	        diagnostic = model.validateMyModel(modelObjects, listOfNodes, listOfEdges, rootElement, implPackage);
	        
	        setSeverity(diagnostic.getSeverity());
      	  
		}
        catch(Exception e){e.printStackTrace();}
		
		return diagnostic;
		
	}

}
