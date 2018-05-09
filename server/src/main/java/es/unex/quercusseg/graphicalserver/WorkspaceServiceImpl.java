package es.unex.quercusseg.graphicalserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.log4j.Logger;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.DidChangeConfigurationParams;
import org.eclipse.lsp4j.DidChangeWatchedFilesParams;
import org.eclipse.lsp4j.DidChangeWorkspaceFoldersParams;
import org.eclipse.lsp4j.ExecuteCommandOptions;
import org.eclipse.lsp4j.ExecuteCommandParams;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceSymbolParams;
import org.eclipse.lsp4j.services.WorkspaceService;
import es.unex.quercusseg.graphicalserver.connector.DiagnosticConnector;
import es.unex.quercusseg.graphicalserver.util.Logging;


public class WorkspaceServiceImpl implements WorkspaceService {

	//Logging instance
	private static Logger logger = Logging.getInstance().getLogger();

	/**
	 * The workspace symbol request is sent from the client to the server to
	 * list project-wide symbols matching the query string.
	 *
	 * Registration Options: void
	 */
	@Override
	public CompletableFuture <List<? extends SymbolInformation>> symbol(WorkspaceSymbolParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		SymbolInformation symbolInformation1 = new SymbolInformation(
				"C1", 
				SymbolKind.Class, 
				new Location("This workspace - myLanguage.txt", new Range(new Position(7, 1), new Position(7, 2))));

	
		List <SymbolInformation> informations = new ArrayList<>();
		informations.add(symbolInformation1);
	
		return CompletableFuture.completedFuture(informations);

	
	}

	
	/**
	 * A notification sent from the client to the server to signal the change of
	 * configuration settings.
	 */
	@Override
	public void didChangeConfiguration(DidChangeConfigurationParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
	}

	/**
	 * The watched files notification is sent from the client to the server when
	 * the client detects changes to file watched by the language client.
	 */
	@Override
	public void didChangeWatchedFiles(DidChangeWatchedFilesParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
	}
	
	
	/**
	 * The workspace/executeCommand request is sent from the client to the
	 * server to trigger command execution on the server. In most cases the
	 * server creates a WorkspaceEdit structure and applies the changes to the
	 * workspace using the request workspace/applyEdit which is sent from the
	 * server to the client.
	 *
	 * Registration Options: ExecuteCommandRegistrationOptions
	 */
	@Override
	public CompletableFuture <Object> executeCommand(ExecuteCommandParams params) {
		
		System.out.println("Workspace executeCommand invocation from the client");
		System.out.println(params.toString());
		
		logger.info("executeCommand notification received from the client side...");
		logger.info(params.toString());

		LanguageServerImpl languageServerImpl = LanguageServerImpl.getInstance();
		MessageParams      messageParams 	  = new MessageParams();

		if(languageServerImpl.getInitialized() == true) {
		
			ServerCapabilities    serverCapabilities    = languageServerImpl.getServerCapabilities();
			List <String>         serverCommands 		= new ArrayList <String> ();
			ExecuteCommandOptions executeCommandOptions = serverCapabilities.getExecuteCommandProvider();
			Boolean 			  gotIt  				= false;
			serverCommands 								= executeCommandOptions.getCommands();
			
			//Check if invoked command is among the ones provided by the server 
			for (int i = 0; i < serverCommands.size() && !gotIt; i++) {
				
				if(serverCommands.get(i).equals(params.getCommand()))
					gotIt = true;
				
			}
			
			if(!gotIt) {
				
				 String notification = new String("Command '" + params.getCommand() + "' which you are trying to invoke is not supported by this server");
				 logger.info("Command '" + params.getCommand() + "' invoked by the client is not supported by this server");

				 return CompletableFuture.completedFuture(notification);
			
			}
			else {
				
				String 		  resourceURI = "";
				String 	      svgLanguage = "";
				List <Object> arguments	  = params.getArguments();
				
				if(params.getCommand().equals("validate")) {
					
					//We do consider clients added URI resource as an argument. Client might want to validate multiple files at a time
					for(int j = 0; j < arguments.size(); j++) {
						
						resourceURI = new String((String) arguments.get(j));
						
						File file = new File(resourceURI);
						
						if(!file.exists()) {
							
							messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
							messageParams.setMessage("No such file " + resourceURI + " on the server side. Open it in first place and try again");
	
							logger.info("Command 'validate' invocation over file " + resourceURI + " on the server side. File not found");

						}
						else {
							
							DiagnosticConnector 	            diagnosticConnector = new DiagnosticConnector();	
							List <org.eclipse.lsp4j.Diagnostic> diagnostics         = diagnosticConnector.getDiagnostics(diagnosticConnector.validateModel(resourceURI));					

							messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
							String message = "Model regarding file " + resourceURI + " has been validated" + '\n';
							message        = message + "Diagnogstics follow: " + diagnostics.toString(); 
							
							logger.info(message);

							messageParams.setMessage(message);
							
						}
	
					}
					
				}

				if(params.getCommand().equals("svg")) {
					
					//SVG language comes as a parameter
					for(int j = 0; j < arguments.size(); j++) {
						
						svgLanguage = new String((String) arguments.get(j));
						resourceURI = "mySVG.txt";
						
						File file = new File(resourceURI);
						
						//For the specified language
						if(!file.exists()) {
							
							messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
							messageParams.setMessage("No such SVG file for the specified language '" + svgLanguage + "' on the server side");
	
							logger.info("No such SVG file for the specified language '" + svgLanguage + "' on the server side");

						}
						else {
							
							ClientCapabilities clientCapabilities = languageServerImpl.getClientCapabilities();
							
							if(clientCapabilities.getWorkspace().getApplyEdit() == false) {
								
								messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
								messageParams.setMessage("ApplyEdit feature is not enabled on your side. Please enable it and try again later on");

								logger.info("ApplyEdit feature is not enabled on client side. Thus, SVG file cannot be delivered to client");

							}
							else {
								
								String 		   input          = null;
								BufferedReader bufferedReader = null;
								
								try {
									bufferedReader = new BufferedReader(new FileReader(resourceURI));
								}
								catch (FileNotFoundException e1) {e1.printStackTrace();}
								
								StringBuilder stringBuilder   = new StringBuilder();
								String        content         = "";
								
								//Read content
								try {
									
									while((input = bufferedReader.readLine()) != null)		
										stringBuilder.append(input);
									
					    		} 
								catch (IOException e) {e.printStackTrace();} 
								finally {
									
									try {
										bufferedReader.close();
									} 
									catch (IOException e) {e.printStackTrace();} 
									
									content = stringBuilder.toString();
								}
								
								//Inform the client about the SVG file
								messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
								messageParams.setMessage("SVG file follows ahead. Have fun!");
								
								logger.info("SVG file for the language " + svgLanguage + " delivered to the client...");
								logger.info("SVG file content: " + content);

								//Send the client the content of the actual file
								VersionedTextDocumentIdentifier versionedTextDocumentIdentifier = new VersionedTextDocumentIdentifier(new Integer(1));
								versionedTextDocumentIdentifier.setUri(resourceURI);
								
								languageServerImpl.applyEditClient(versionedTextDocumentIdentifier, content);
							
							}
						
						}
	
					}
					
				}

			}
			
		}
		else {
			
			messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
			messageParams.setMessage("Connection is down. Establish connection wiht the server");

			logger.info("Client tired to execute a command before completing initialization");

		}
		
		return CompletableFuture.completedFuture(messageParams);
		
	}
	
	
	/**
	 * The workspace/didChangeWorkspaceFolders notification is sent from the client
	 * to the server to inform the server about workspace folder configuration changes.
	 * The notification is sent by default if both ServerCapabilities/workspaceFolders
	 * and ClientCapabilities/workspace/workspaceFolders are true; or if the server has
	 * registered to receive this notification it first.
	 *
	 * This API is a <b>proposal</b> from LSP and may change.
	 */
	@Override
	public void didChangeWorkspaceFolders(DidChangeWorkspaceFoldersParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
	}


}
