package es.unex.quercusseg.graphicalserver.connector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import es.unex.quercusseg.graphicalserver.model.*;
import es.unex.quercusseg.graphicalserver.util.*;

import family.model.family.NamedElement;


public class Factory {
	
	public Factory() {}
	

	public List <NamedElement> instantiateObjets(List <Node> nodes, String namedElementPackage, String implPackage) 
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		
		List <NamedElement> modelObjects = new ArrayList <NamedElement> ();
		NamedElement        object       = null;
		
		//Nodes
		for (int i = 0; i < nodes.size(); i++) {
			
			String className 	     = implPackage + nodes.get(i).getType() + "Impl";
			String classNamedElement = namedElementPackage;
			
			try {
				
				Class <?> classDefinition    = Class.forName(className);
				Class <?> classDefinitionAux = Class.forName(classNamedElement);
				
				object = (NamedElement) classDefinition.newInstance();
				modelObjects.add(object);
				
				
				for(int j = 0; j < nodes.get(i).getAbstractProperties().size(); j++) {
					
					Boolean gotIt = false;
					
					String methodName = "set";
					methodName = methodName + nodes.get(i).getAbstractProperties().get(j).getName().substring(0,1).toUpperCase();
					methodName = methodName + nodes.get(i).getAbstractProperties().get(j).getName().substring(1);
					
					if(methodName.equals("setName")) {
						
						Method method = classDefinitionAux.getDeclaredMethod(methodName, String.class);
						method.invoke(object, nodes.get(i).getAbstractProperties().get(j).getValue());
						
					}
					else {
						
						Method [] allMethods = classDefinition.getDeclaredMethods();
						
						for(int k = 0; k < allMethods.length && gotIt != true; k++) {
							
							Method m = allMethods[k];
							
							if(m.getName().equals(methodName)) {
								
								Class <?> [] parameterType = m.getParameterTypes();
								Class <?>    type = parameterType[0];
								
								if(type.equals(Integer.class)) {
									
									Method method = classDefinition.getDeclaredMethod(methodName, int.class);
									method.invoke(object, Integer.parseInt(nodes.get(i).getAbstractProperties().get(j).getValue()));
									gotIt = true;
									
								}
								
								if(type.equals(Float.class)) {
									
									Method method = classDefinition.getDeclaredMethod(methodName, int.class);
									method.invoke(object, Integer.parseInt(nodes.get(i).getAbstractProperties().get(j).getValue()));
									gotIt = true;
									
								}

								if(type.equals(String.class)) {
									
									Method method = classDefinition.getDeclaredMethod(methodName, String.class);
									method.invoke(object, nodes.get(i).getAbstractProperties().get(j).getValue());
									gotIt = true;
								}

								if(type.isEnum()) {
									
									Field []  fields    = type.getDeclaredFields();
									String    fieldName = nodes.get(i).getAbstractProperties().get(j).getValue().toUpperCase();
									
									for(Field f : fields) {
										
										if(f.getName().equals(fieldName)) {
											
											Method method = classDefinition.getDeclaredMethod(methodName, type);
											method.invoke(object, Enum.valueOf((Class <? extends Enum>) type, fieldName));
											gotIt = true;
											
										}
										
									}
									
								}
								
							}

						}
										
					}
					
				}
				
			} 
			catch (ClassNotFoundException e) { System.out.println(e); }
			catch (InstantiationException e) { System.out.println(e); } 
			catch (IllegalAccessException e) { System.out.println(e); } 
		
		}

		return modelObjects;
		
	}
	
	
}
