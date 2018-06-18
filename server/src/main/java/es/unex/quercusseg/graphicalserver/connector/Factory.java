package es.unex.quercusseg.graphicalserver.connector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.Logger;

import es.unex.quercusseg.graphicalserver.model.*;
import es.unex.quercusseg.graphicalserver.util.*;

import family.model.family.*;


public class Factory {
	
	//Logging instance
	private static Logger logger = Logging.getInstance().getLogger();

	public Factory() {}

	
	public List <NamedElement> instantiateObjets(List <Node> nodes, String namedElementPackage, String implPackage, String rootElement) 
			throws NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		
		List <NamedElement> modelObjects = new ArrayList <NamedElement> ();
		NamedElement        object       = null;
		
		rootElement = rootElement + "Factory";
		String factoryClassName = implPackage + rootElement + "Impl";
		
		try {
			
			Class <?> factoryClassDefinition = Class.forName(factoryClassName);
			Object    factoryObject          = factoryClassDefinition.newInstance();
			
			//Nodes
			for (int i = 0; i < nodes.size(); i++) {
				
				Method [] factoryMethods        = factoryClassDefinition.getDeclaredMethods();
				Boolean   instanceFound          = false;	
				String    createInstanceOf       = "create" + nodes.get(i).getType();
				
				for(int index = 0; index < factoryMethods.length && instanceFound != true; index++) {
					
					Method m = factoryMethods[index];
					
					if(m.getName().equals(createInstanceOf)) {
						
						Method method = factoryClassDefinition.getDeclaredMethod(createInstanceOf);
						object = (NamedElement) method.invoke(factoryObject);
						instanceFound = true;

					}
					
				}

				
				try {
					
					String    className		     = implPackage + nodes.get(i).getType() + "Impl";
					String    classNamedElement      = namedElementPackage;
					
					Class <?> classDefinition    = Class.forName(className);
					Class <?> classDefinitionAux = Class.forName(classNamedElement);
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
									
									if(type.equals(Integer.class) || type.equals(int.class)) {
										
										Method method = classDefinition.getDeclaredMethod(methodName, int.class);
										method.invoke(object, Integer.parseInt(nodes.get(i).getAbstractProperties().get(j).getValue()));
										gotIt = true;
										
									}
									
									if(type.equals(Float.class) || type.equals(float.class)) {
										
										Method method = classDefinition.getDeclaredMethod(methodName, int.class);
										method.invoke(object, Float.valueOf(nodes.get(i).getAbstractProperties().get(j).getValue()));
										gotIt = true;
										
									}

									if(type.equals(Boolean.class) || type.equals(boolean.class)) {
										
										Method method = classDefinition.getDeclaredMethod(methodName, Boolean.class);
										method.invoke(object, Boolean.valueOf(nodes.get(i).getAbstractProperties().get(j).getValue()));
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
				catch (ClassNotFoundException e) { System.out.println(e); logger.info(e.toString());}
				catch (IllegalAccessException e) { System.out.println(e); logger.info(e.toString());} 
			
			}

		} 
		catch (ClassNotFoundException e1) {e1.printStackTrace(); logger.info(e1.toString());} 
		catch (InstantiationException e1) {e1.printStackTrace(); logger.info(e1.toString());} 
		catch (IllegalAccessException e1) {e1.printStackTrace(); logger.info(e1.toString());}
		

		return modelObjects;
		
	}
	
	
}
