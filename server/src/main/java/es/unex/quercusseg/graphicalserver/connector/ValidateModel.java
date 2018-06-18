package es.unex.quercusseg.graphicalserver.connector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Diagnostician;

import es.unex.quercusseg.graphicalserver.model.*;
import es.unex.quercusseg.graphicalserver.util.*;

import family.model.family.NamedElement;


public class ValidateModel {

	//Logging instance
	private static Logger logger = Logging.getInstance().getLogger();

	
	public String getNodeOriginName(Node nodeOrigin) {
		
		String  nodeOriginName = "";
		
		for(int k = 0; k < nodeOrigin.getAbstractProperties().size(); k++) {
			
			Pair pair = nodeOrigin.getAbstractProperties().get(k);
			
			if(pair.getName().equals("name"))
				nodeOriginName = pair.getValue();
			
		}

		return nodeOriginName;
		
	}
	
	
	public String getNodeTargetName(Node nodeTarget) {
		
		String  nodeTargetName = "";
		
		for(int k = 0; k < nodeTarget.getAbstractProperties().size(); k++) {
			
			Pair pair = nodeTarget.getAbstractProperties().get(k);
			
			if(pair.getName().equals("name"))
				nodeTargetName = pair.getValue();
			
		}

		return nodeTargetName;
		
	}

	
	public String getRelationName(Edge thisEdge) {
		
		String relationName = "";
		
		for(int k = 0; k < thisEdge.getAbstractProperties().size(); k++) {
			
			Pair pair = thisEdge.getAbstractProperties().get(k);
			
			if(pair.getName().equals("name"))
				relationName = pair.getValue();
						
		}
		
		return relationName;

	}

	
	public String getMultiplicity(Edge thisEdge) {
	
		String multiplicity = "";
		
		for(int k = 0; k < thisEdge.getAbstractProperties().size(); k++) {
			
			Pair pair = thisEdge.getAbstractProperties().get(k);
					
			if(pair.getName().equals("multiplicity"))
				multiplicity = pair.getValue();
			
		}

		return multiplicity;
		
	}
	
	
	public Diagnostic validate(List <NamedElement> modelObjects, List <Node> nodes, List <Edge> edges, String rootElement, String implPackage) 
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		
		logger.info("Validating model...");

		//Create the content of the model
		Class <?> rootElementClass = Class.forName(new String(implPackage + rootElement + "Impl"));
		Object    myRootObject     = rootElementClass.newInstance();
		Boolean   instantiated     = false;

		//For every single edge 
		for (int i = 0; i < edges.size(); i++) {
			
			Edge    thisEdge       = edges.get(i);
			Node    nodeOrigin     = null;
			Node    nodeTarget     = null;
			String  nodeOriginName = "";
			String  nodeTargetName = "";
			Boolean gotEmNodes     = false;
			Boolean gotEmElements  = false;
			String  relationName   = "";
			String  multiplicity   = "";
			
			
			//Find them nodes belonging to origin and target
			for (int j = 0; j < nodes.size() && !gotEmNodes; j++) {
				
				if(nodes.get(j).getId() == Integer.parseInt(thisEdge.getOrigin()))
					nodeOrigin = nodes.get(j);
				
				if(nodes.get(j).getId() == Integer.parseInt(thisEdge.getTarget()))
					nodeTarget = nodes.get(j);
				
				if(nodeOrigin != null && nodeTarget != null)
					gotEmNodes = true;
				
			}
			
			//Find identifier of nodeOrigin
			nodeOriginName = getNodeOriginName(nodeOrigin);
			
			//Find identifier of nodeTarget
			nodeTargetName = getNodeTargetName(nodeTarget);
			
			
			//Find relation name and multiplicity
			relationName = getRelationName(thisEdge);
			multiplicity = getMultiplicity(thisEdge);
						
			
			//Find them namedElements
			NamedElement namedElement1 = null;
			NamedElement namedElement2 = null;
			
			for(int k = 0; k < modelObjects.size() && !gotEmElements; k++) {
				
				if(modelObjects.get(k).getName().equals(nodeOriginName))
					namedElement1 = modelObjects.get(k);
				
				if(modelObjects.get(k).getName().equals(nodeTargetName))
					namedElement2 = modelObjects.get(k);

				if(namedElement1 != null && namedElement2 != null)
					gotEmElements = true;
				
			}
			
						
			//Type of them NamedElements - Assign relationships from root element
			if(nodeOrigin.getType().equals(rootElement)) {
				
				if (instantiated == false) {
					
					myRootObject = namedElement1;
					instantiated = true;
					
				}
				
				String methodName = "";
				
				try {
					
					if(multiplicity.equals("1")) {
						
						methodName = "set";
						methodName = methodName + relationName.substring(0,1).toUpperCase();
						methodName = methodName + relationName.substring(1);
						
						Method [] allMethods = myRootObject.getClass().getDeclaredMethods();
						Boolean   bingo      = false;
						
						for(int index = 0; index < allMethods.length && bingo != true; index++) {
							
							Method m = allMethods[index];
							
							if(m.getName().equals(methodName)) {
								
								Class <?> [] parameterType = m.getParameterTypes();
								Class <?>    type = parameterType[0];
								
								Method method = myRootObject.getClass().getDeclaredMethod(methodName, type);
								method.invoke(myRootObject, namedElement2);
								bingo = true;
								
							}
							
						}

					}

					if(multiplicity.equals("n")) {
						
						methodName = "get";
						methodName = methodName + relationName.substring(0,1).toUpperCase();
						methodName = methodName + relationName.substring(1);
						
						Method [] allMethods = myRootObject.getClass().getDeclaredMethods();
						Boolean   bingo      = false;

						for(int index = 0; index < allMethods.length && bingo != true; index++) {
							
							Method m = allMethods[index];
							
							if(m.getName().equals(methodName)) {
							
								Method method = myRootObject.getClass().getDeclaredMethod(methodName);
								EList <NamedElement> eList = (EList<NamedElement>) method.invoke(myRootObject);
								eList.add(namedElement2);
								bingo = true;
								
							}
					
						}
						
					}

					
				} 
				catch (NoSuchMethodException e)     { System.out.println(e); logger.info(e.toString());}
				catch (SecurityException e)         { System.out.println(e); logger.info(e.toString());} 
				catch (IllegalAccessException e)    { System.out.println(e); logger.info(e.toString());} 
				catch (IllegalArgumentException e)  { System.out.println(e); logger.info(e.toString());} 
				catch (InvocationTargetException e) { System.out.println(e); logger.info(e.toString());}

			}
			
		}
		
		//Cast object to expected EObject in the validation service
		EObject object = (EObject) myRootObject;
		Diagnostic diagnostic = Diagnostician.INSTANCE.validate(object);
		
		logger.info("Diagnostic: " + diagnostic.toString());

		return diagnostic;
		
	}
	
}
