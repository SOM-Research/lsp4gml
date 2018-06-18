package es.unex.quercusseg.graphicalserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.apache.logging.log4j.Logger;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeActionParams;
import org.eclipse.lsp4j.CodeLens;
import org.eclipse.lsp4j.CodeLensParams;
import org.eclipse.lsp4j.Command;
import org.eclipse.lsp4j.CompletionItem;
import org.eclipse.lsp4j.CompletionItemKind;
import org.eclipse.lsp4j.CompletionList;
import org.eclipse.lsp4j.CompletionParams;
import org.eclipse.lsp4j.DidChangeTextDocumentParams;
import org.eclipse.lsp4j.DidCloseTextDocumentParams;
import org.eclipse.lsp4j.DidOpenTextDocumentParams;
import org.eclipse.lsp4j.DidSaveTextDocumentParams;
import org.eclipse.lsp4j.DocumentFormattingParams;
import org.eclipse.lsp4j.DocumentHighlight;
import org.eclipse.lsp4j.DocumentHighlightKind;
import org.eclipse.lsp4j.DocumentLink;
import org.eclipse.lsp4j.DocumentLinkParams;
import org.eclipse.lsp4j.DocumentOnTypeFormattingParams;
import org.eclipse.lsp4j.DocumentRangeFormattingParams;
import org.eclipse.lsp4j.DocumentSymbolParams;
import org.eclipse.lsp4j.Hover;
import org.eclipse.lsp4j.Location;
import org.eclipse.lsp4j.MarkedString;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.ParameterInformation;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.ReferenceParams;
import org.eclipse.lsp4j.RenameParams;
import org.eclipse.lsp4j.SignatureHelp;
import org.eclipse.lsp4j.SignatureInformation;
import org.eclipse.lsp4j.SymbolInformation;
import org.eclipse.lsp4j.SymbolKind;
import org.eclipse.lsp4j.TextDocumentItem;
import org.eclipse.lsp4j.TextDocumentPositionParams;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.WillSaveTextDocumentParams;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.jsonrpc.messages.Either;
import org.eclipse.lsp4j.services.TextDocumentService;

import es.unex.quercusseg.graphicalserver.connector.DiagnosticConnector;
import es.unex.quercusseg.graphicalserver.util.Logging;


public class TextDocumentServiceImpl implements TextDocumentService {

	//Logging instance
	private static Logger logger = Logging.getInstance().getLogger();
	
	private TextDocumentItem textDocumentItem;
	private String 			 modifiedContent = "";
	
	
		/**
	 * The Completion request is sent from the client to the server to compute
	 * completion items at a given cursor position. Completion items are
	 * presented in the IntelliSense user interface. If computing complete
	 * completion items is expensive servers can additional provide a handler
	 * for the resolve completion item request. This request is sent when a
	 * completion item is selected in the user interface.
	 * 
	 * Registration Options: CompletionRegistrationOptions
	 */
	@Override
	public CompletableFuture <Either<List<CompletionItem>, CompletionList>> completion(
			CompletionParams position) {
		
		
		System.out.println(position.toString());
		System.out.println();
		
		
		CompletionItem completionItem = new CompletionItem();
		
		completionItem.setLabel("Operation");
		completionItem.setKind(CompletionItemKind.Method);
		completionItem.setDetail("Add Operation Component Operation Label");
		completionItem.setDocumentation("Method completion for the file" + position.getTextDocument().getUri());
		completionItem.setSortText("Operation");
		completionItem.setFilterText("Operation");
		completionItem.setInsertText("node{ type:”operation” ...}, edge{...}");
		completionItem.setInsertTextFormat(null);
		completionItem.setTextEdit(new TextEdit(new Range(new Position(3, 1), new Position(3, 1)), 
				new String("node{ type:”operation” ...}, edge{...}")));
		completionItem.setAdditionalTextEdits(null);
		completionItem.setCommand(null);
		completionItem.setData(null);
		
		
		List <CompletionItem> completionItems = new ArrayList <> ();
		completionItems.add(completionItem);
			
		CompletionList completionList = new CompletionList(false, completionItems);
		
		Either <List<CompletionItem>, CompletionList> myList;
		myList = Either.forRight(completionList);
		myList = Either.forLeft(completionItems);
		
		return CompletableFuture.completedFuture(myList);
	
	}

	/**
	 * The request is sent from the client to the server to resolve additional
	 * information for a given completion item.
	 */
	@Override
	public CompletableFuture <CompletionItem> resolveCompletionItem(CompletionItem unresolved) {
		
		System.out.println(unresolved.toString());
		System.out.println();
		
		unresolved.setLabel("Operation");
		unresolved.setKind(CompletionItemKind.Method);
		unresolved.setDetail("Add Operation Component Operation Label");
		unresolved.setDocumentation("Method completion for the file myLanguage.txt");
		unresolved.setSortText("Operation");
		unresolved.setFilterText("Operation");
		unresolved.setInsertText("node{ type:”operation” ...}, edge{...}");
		unresolved.setInsertTextFormat(null);
		unresolved.setTextEdit(new TextEdit(new Range(new Position(3, 1), new Position(3, 1)), 
				new String("node{ type:”operation” ...}, edge{...}")));
		unresolved.setAdditionalTextEdits(null);
		unresolved.setCommand(null);
		unresolved.setData(null);

		return CompletableFuture.completedFuture(unresolved);
		
	}

	
	/**
	 * The hover request is sent from the client to the server to request hover
	 * information at a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <Hover> hover(TextDocumentPositionParams position) {
		
		System.out.println(position.toString());
		System.out.println();
		
		MarkedString markedString = new MarkedString("MyLanguage", "node");
		Range        range        = new Range(position.getPosition(), new Position(position.getPosition().getLine(), 4));
		
		Either <String, MarkedString> markedStringItem;
		markedStringItem = Either.forLeft(new String ("Hover request resolved"));
		markedStringItem = Either.forRight(markedString);
	
		List <Either<String, MarkedString>> myList = new ArrayList<>();
		myList.add(markedStringItem);
		
		Hover hover = new Hover(myList, range);
		
		return CompletableFuture.completedFuture(hover);
		
	}

	
	/**
	 * The signature help request is sent from the client to the server to
	 * request signature information at a given cursor position.
	 * 
	 * Registration Options: SignatureHelpRegistrationOptions
	 */
	@Override
	public CompletableFuture <SignatureHelp> signatureHelp(TextDocumentPositionParams position) {
		
		System.out.println(position.toString());
		System.out.println();
		
		ParameterInformation parameterInformation = new ParameterInformation();
		parameterInformation.setLabel("node");
		parameterInformation.setDocumentation("node is an instance of an object within the editor");
		
		List <ParameterInformation> parameterInformations = new ArrayList<>();
		parameterInformations.add(parameterInformation);
		
		SignatureInformation signatureInformation = new SignatureInformation();
		signatureInformation.setDocumentation("node is an instance of an object within the editor");
		signatureInformation.setLabel("node");
		signatureInformation.setParameters(parameterInformations);
		
		List <SignatureInformation> signatureInformations = new ArrayList<>();
		signatureInformations.add(signatureInformation);
		
		SignatureHelp signatureHelp = new SignatureHelp();
		signatureHelp.setActiveParameter(new Integer(0));
		signatureHelp.setActiveParameter(new Integer(0));
		signatureHelp.setSignatures(signatureInformations);
		
		
		return CompletableFuture.completedFuture(signatureHelp);
	
	}

	
	/**
	 * The goto definition request is sent from the client to the server to
	 * resolve the definition location of a symbol at a given text document
	 * position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends Location>> definition(TextDocumentPositionParams position) {
		
		System.out.println(position.toString());
		System.out.println();
		
		Location location = new Location();
		location.setUri("myLanguage.txt");
		location.setRange(new Range(new Position(3,1), new Position(20, 1)));
		
		List <Location> locations = new ArrayList<>();
		locations.add(location);
		
		return CompletableFuture.completedFuture(locations);
		
	}

	
	/**
	 * The references request is sent from the client to the server to resolve
	 * project-wide references for the symbol denoted by the given text document
	 * position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends Location>> references(ReferenceParams params) {
		
		
		System.out.println(params.toString());
		System.out.println();
		
		List <Location> locations = new ArrayList<>();
		
		Location location1 = new Location();
		location1.setUri("myLanguage.txt");
		location1.setRange(new Range(new Position(3, 1), new Position(3, 4)));

		Location location2 = new Location();
		location2.setUri("myLanguage.txt");
		location2.setRange(new Range(new Position(21, 1), new Position(21, 4)));
	
		Location location3 = new Location();
		location2.setUri("CanonicalUseCases.txt");
		location2.setRange(new Range(new Position(3, 1), new Position(3, 4)));

		locations.add(location1);
		locations.add(location2);
		locations.add(location3);
		
		return CompletableFuture.completedFuture(locations);
		
	}

	
	/**
	 * The document highlight request is sent from the client to the server to
	 * to resolve a document highlights for a given text document position.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends DocumentHighlight>> documentHighlight(TextDocumentPositionParams position) {
		
		System.out.println(position.toString());
		System.out.println();
		
		DocumentHighlight documentHighlight1 = new DocumentHighlight();
		documentHighlight1.setRange(new Range(new Position(3, 1), new Position(3, 4)));
		documentHighlight1.setKind(DocumentHighlightKind.Text);

		DocumentHighlight documentHighlight2 = new DocumentHighlight();
		documentHighlight2.setRange(new Range(new Position(21, 1), new Position(21, 4)));
		documentHighlight2.setKind(DocumentHighlightKind.Text);

		List <DocumentHighlight> documentHighlights = new ArrayList<>();
		documentHighlights.add(documentHighlight1);
		documentHighlights.add(documentHighlight2);
		
		return CompletableFuture.completedFuture(documentHighlights);
		
	}

	
	/**
	 * The document symbol request is sent from the client to the server to list
	 * all symbols found in a given text document.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends SymbolInformation>> documentSymbol(DocumentSymbolParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		SymbolInformation symbolInformation1 = new SymbolInformation(
				"C1", 
				SymbolKind.Class, 
				new Location(params.getTextDocument().getUri(), new Range(new Position(7, 1), new Position(7, 2))));
		symbolInformation1.setContainerName("RootPackage");
		
		SymbolInformation symbolInformation2 = new SymbolInformation(
				"P1", 
				SymbolKind.Package, 
				new Location(params.getTextDocument().getUri(), new Range(new Position(25, 1), new Position(22, 2))));
		symbolInformation2.setContainerName("RootPackage");
				
		
		List <SymbolInformation> informations = new ArrayList<>();
		informations.add(symbolInformation1);
		informations.add(symbolInformation2);
		
		return CompletableFuture.completedFuture(informations);
		
	}

	
	/**
	 * The code action request is sent from the client to the server to compute
	 * commands for a given text document and range. These commands are
	 * typically code fixes to either fix problems or to beautify/refactor code.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends Command>> codeAction(CodeActionParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		Command command = new Command();
		command.setTitle("Validate model");
		command.setCommand("Validate");
		command.setArguments(null);
		
		List <Command> commands = new ArrayList<>();
		commands.add(command);
		
		return CompletableFuture.completedFuture(commands);
		
	}

	
	/**
	 * The code lens request is sent from the client to the server to compute
	 * code lenses for a given text document.
	 * 
	 * Registration Options: CodeLensRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends CodeLens>> codeLens(CodeLensParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		Command command = new Command();
		command.setTitle("Validate model");
		command.setCommand("Validate");
		command.setArguments(null);

		CodeLens codeLens1 = new CodeLens();
		codeLens1.setRange(new Range(new Position(1, 1), new Position(40, 1)));
		codeLens1.setData(null);
		codeLens1.setCommand(command);
			
		List <CodeLens> codeLensList = new ArrayList<>();
		codeLensList.add(codeLens1);		
		
		return CompletableFuture.completedFuture(codeLensList);
		
	}

	
	/**
	 * The code lens resolve request is sent from the client to the server to
	 * resolve the command for a given code lens item.
	 */
	@Override
	public CompletableFuture <CodeLens> resolveCodeLens(CodeLens unresolved) {
		
		System.out.println(unresolved.toString());
		System.out.println();
		
		CodeLens myCodeLens = unresolved;
		Command  myCommand  = unresolved.getCommand();
		
		myCommand.setCommand("Validate");
		myCodeLens.setCommand(myCommand);

		return CompletableFuture.completedFuture(myCodeLens);
	
	}

	
	/**
	 * The document formatting request is sent from the client to the server to
	 * format a whole document.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends TextEdit>> formatting(DocumentFormattingParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		TextEdit textEdit = new TextEdit();
		textEdit.setNewText("This   is   the   language   server   protocol   -   Whole   document   formatting");
		textEdit.setRange(new Range(new Position(0, 1), new Position(40, 1)));
		
		List <TextEdit> edits = new ArrayList<>();
		edits.add(textEdit);
		
		return CompletableFuture.completedFuture(edits);
		
	}

	
	/**
	 * The document range formatting request is sent from the client to the
	 * server to format a given range in a document.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends TextEdit>> rangeFormatting(DocumentRangeFormattingParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		TextEdit textEdit = new TextEdit();
		textEdit.setNewText("This   is   the   language   server   protocol   -   Range   formatting");
		textEdit.setRange(new Range(new Position(1, 1), new Position(40, 1)));
		
		List <TextEdit> edits = new ArrayList<>();
		edits.add(textEdit);

		return CompletableFuture.completedFuture(edits);
		
	}

	
	/**
	 * The document on type formatting request is sent from the client to the
	 * server to format parts of the document during typing.
	 * 
	 * Registration Options: DocumentOnTypeFormattingRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<? extends TextEdit>> onTypeFormatting(DocumentOnTypeFormattingParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		TextEdit textEdit = new TextEdit();
		textEdit.setNewText("pacage");
		textEdit.setRange(new Range(new Position(23, 1), new Position(23, 7)));
		
		List <TextEdit> edits = new ArrayList<>();
		edits.add(textEdit);

		return CompletableFuture.completedFuture(edits);
		
	}

	
	/**
	 * The rename request is sent from the client to the server to do a
	 * workspace wide rename of a symbol.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <WorkspaceEdit> rename(RenameParams params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		WorkspaceEdit workspaceEdit = new WorkspaceEdit();
		workspaceEdit.setDocumentChanges(null);
		
		TextEdit textEdit = new TextEdit();
		textEdit.setNewText((new String("​RootElement::PCK1::C1​")));
		textEdit.setRange(new Range(new Position(8, 1), new Position(8, 25)));
		
		List <TextEdit> edits = new ArrayList<>();
		edits.add(textEdit);
		
		LinkedHashMap<String, List<TextEdit>> linkedHashMap = new LinkedHashMap<String, List<TextEdit>>();
		linkedHashMap.put("Edit01", edits);
		
		workspaceEdit.setChanges(linkedHashMap);
		
		return CompletableFuture.completedFuture(workspaceEdit);
		
	}

	
	/**
	 * The document open notification is sent from the client to the server to
	 * signal newly opened text documents. The document's truth is now managed
	 * by the client and the server must not try to read the document's truth
	 * using the document's uri.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public void didOpen(DidOpenTextDocumentParams params) {
		
	
		LanguageServerImpl languageServerImpl = LanguageServerImpl.getInstance();

		logger.info("didOpen notification received from the client side...");
		logger.info(params.toString());

		if(languageServerImpl.getInitialized() == true) {
			
			this.textDocumentItem = params.getTextDocument();
			
			
			System.out.println("didOpen notification received from the client side...");
			System.out.println(this.textDocumentItem.toString());
			
			System.out.print("Creating copy of the document on the server side...");
				
			logger.info("Document has been opened on the client side...");
			logger.info(this.textDocumentItem.toString());
			logger.info("Creating copy of the document on the server side...");
			
			String resourceURI = params.getTextDocument().getUri();
			String content     = params.getTextDocument().getText();
			
			try {
				
				BufferedWriter writer = new BufferedWriter(new FileWriter(resourceURI));
				writer.write(content);
				writer.close();
				
			}
		    catch (IOException e) {
		    	
		    	e.printStackTrace();
		    	logger.info(e.toString());
		    	
				MessageParams messageParams = new MessageParams();
				messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
				messageParams.setMessage("Couldn't open document " + params.getTextDocument().getUri() + ". Try again, please");
				
				logger.info("Server couldn't open document " + params.getTextDocument().getUri());

				if(languageServerImpl.doesClientApplyEdit() == true)
					languageServerImpl.notifyMessageClient(messageParams);

		    }
		  	
			System.out.println("copy created properly");

			System.out.print("Checking model correctness...");
			
			logger.info("Copy created properly");
			logger.info("Checking model correctness...");

			DiagnosticConnector diagnosticConnector = new DiagnosticConnector();
			diagnosticConnector.validateModel(resourceURI);
			
			if(!diagnosticConnector.didError()) {
				
				MessageParams messageParams = new MessageParams();
				messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
				messageParams.setMessage("Document " + params.getTextDocument().getUri() + " has been correctly opened");
				
				logger.info("Document " + params.getTextDocument().getUri() + " has been correctly opened");
				
				if(languageServerImpl.doesClientApplyEdit() == true)
					languageServerImpl.notifyMessageClient(messageParams);
			
			}
			else {
				
				MessageParams messageParams = new MessageParams();
				messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
				
				String message = "Document " + params.getTextDocument().getUri() + " has not been correctly opened" + '\n';
				message = message + "Diagnostic: " + diagnosticConnector.getDiagnostics(diagnosticConnector.validateModel(resourceURI)).toString(); 
				messageParams.setMessage(message);
				
				logger.info(message);

				if(languageServerImpl.doesClientApplyEdit() == true)
					languageServerImpl.notifyMessageClient(messageParams);
		
			}
			
			logger.info("...end of document parsing");
			
			System.out.println("end of document parsing");
			System.out.println("");
			
		}
		else {
			
			MessageParams messageParams = new MessageParams();
			messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
			messageParams.setMessage("Connection has not been established. Establish connection");

			logger.info("Client tired to open a document before completing initialization");

			if(languageServerImpl.doesClientApplyEdit() == true)
				languageServerImpl.notifyMessageClient(messageParams);
			
		}		
		
	}

	
	/**
	 * The document save notification is sent from the client to the server when
	 * the document for saved in the client.
	 * 
	 * Registration Options: TextDocumentSaveRegistrationOptions
	 */
	@Override
	public void didSave(DidSaveTextDocumentParams params) {
		
		LanguageServerImpl languageServerImpl = LanguageServerImpl.getInstance();

		logger.info("didSave notification received from the client side...");
		logger.info(params.toString());

		System.out.println("Save notification received from the client side --");
		System.out.println(params.toString());
		System.out.println();
		
		//Modified content of the file --> We do store for later purpose
		this.modifiedContent = params.getText();
			
		MessageParams messageParams = new MessageParams();
		messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
		messageParams.setMessage("Save notification for the file " + params.getTextDocument().getUri() + " received");
		
		if(languageServerImpl.doesClientApplyEdit() == true)
			languageServerImpl.notifyMessageClient(messageParams);

	}


	/**
	 * The document will save notification is sent from the client to the server before the document is actually saved.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public void willSave(WillSaveTextDocumentParams params) {
		
		LanguageServerImpl languageServerImpl = LanguageServerImpl.getInstance();
		
		logger.info("willSave notification received from the client side...");
		logger.info(params.toString());

		System.out.println("WillSave notification received from the client side");
		System.out.println(params.toString());
		System.out.println();
		
		MessageParams messageParams = new MessageParams();
		messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
		messageParams.setMessage("WillSave notification for the file " + params.getTextDocument().getUri() + " received");
		
		if(languageServerImpl.doesClientApplyEdit() == true)
			languageServerImpl.notifyMessageClient(messageParams);
		
	}


	/**
	 * The document change notification is sent from the client to the server to
	 * signal changes to a text document.
	 * 
	 * Registration Options: TextDocumentChangeRegistrationOptions
	 */
	@Override
	public void didChange(DidChangeTextDocumentParams params) {
		
		LanguageServerImpl languageServerImpl = LanguageServerImpl.getInstance();
		
		System.out.println("didChange notification received from the client side...");
		logger.info("didChange notification received from the client side...");
		logger.info(params.toString());

		if(languageServerImpl.getInitialized() == true) {
			
			String resourceURI = params.getTextDocument().getUri();

			File file = new File(resourceURI);
			
			if(!file.exists()) {
							 
				MessageParams messageParams = new MessageParams();
				messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
				messageParams.setMessage("Document " + params.getTextDocument().getUri() + " has not been previously opened. Open it in first place and try again");
				
				logger.info("Client tried to change a document " + params.getTextDocument().getUri() + " which has not been prevo¡iously opened on the server side");

				if(languageServerImpl.doesClientApplyEdit() == true)
					languageServerImpl.notifyMessageClient(messageParams);

			}
			else {
				
				System.out.println("File " + resourceURI + " has changed");
				System.out.println(params.toString());
				
				logger.info("File " + resourceURI + " has changed");
				logger.info(params.toString());

				System.out.print("Creating copy of the document on the server side...");
				logger.info("Creating copy of the document on the server side...");
				
				String auxResource = "auxResource.txt";
				
				try {
					
					BufferedWriter writer = new BufferedWriter(new FileWriter(auxResource));
					
					if(this.modifiedContent.equals(""))
						writer.write(params.getContentChanges().get(0).getText());
					else
						writer.write(this.modifiedContent);

					writer.close();
					
				}
			    catch (IOException e) {e.printStackTrace(); logger.info(e.toString());}
			  	
				System.out.println("copy created properly");
				System.out.print("Checking IRF correctness...");
			
				logger.info("Copy created properly");
				logger.info("Checking IRF correctness...");

				DiagnosticConnector diagnosticConnector = new DiagnosticConnector();
				diagnosticConnector.validateModel(auxResource);
								
				if(!diagnosticConnector.didError()) {
					
					//Delete aux file and update current IRF file on the client side. Keep synchronization
					try {
						
						BufferedWriter writer = new BufferedWriter(new FileWriter(resourceURI));
						
						if(this.modifiedContent.equals(""))
							writer.write(params.getContentChanges().get(0).getText());
						else
							writer.write(this.modifiedContent);

						writer.close();
						
						file = new File(auxResource);
						file.delete();
						
					}
				    catch (IOException e) {e.printStackTrace(); logger.info(e.toString());}
					 
					List <org.eclipse.lsp4j.Diagnostic> diagnostics              = diagnosticConnector.getDiagnostics(diagnosticConnector.validateModel(resourceURI));					
					PublishDiagnosticsParams            publishDiagnosticsParams = new PublishDiagnosticsParams(resourceURI, diagnostics);

					logger.info("Diagnostics...");
					logger.info(publishDiagnosticsParams);

					if(languageServerImpl.doesClientApplyEdit() == true)
						languageServerImpl.publishDiagnogsticsToClient(publishDiagnosticsParams);
				
				}
				else {
															
					List <org.eclipse.lsp4j.Diagnostic> diagnostics              = diagnosticConnector.getDiagnostics(diagnosticConnector.validateModel(auxResource));					
					PublishDiagnosticsParams 		    publishDiagnosticsParams = new PublishDiagnosticsParams(resourceURI, diagnostics);

					logger.info("Diagnostics...");
					logger.info(publishDiagnosticsParams);

					languageServerImpl.publishDiagnogsticsToClient(publishDiagnosticsParams);

					//We do check if client allows applyEdit options. If true, we do send him those applyEdits
					ClientCapabilities clientCapabilities = languageServerImpl.getClientCapabilities();
					
					if(languageServerImpl.doesClientApplyEdit() == true) {
					
						//Read content of previous version of the file
						String         applyContent   = "";
						String         input          = "";
						file 						  = new File(resourceURI);
						BufferedReader bufferedReader = null;
						StringBuilder  stringBuilder  = new StringBuilder();
						
						try {
							
							bufferedReader = new BufferedReader(new FileReader(file));
							
							while((input = bufferedReader.readLine()) != null) {
								
								stringBuilder.append(input);
								
							}
							
						} 
						catch (FileNotFoundException e) {e.printStackTrace(); logger.info(e.toString());} 
						catch (IOException e) 		    {e.printStackTrace(); logger.info(e.toString());}
						finally {
							
							try {
								
								bufferedReader.close();
								file = new File(auxResource);
								file.delete();
								
							} catch (IOException e) {e.printStackTrace(); logger.info(e.toString());} 
							
							applyContent = stringBuilder.toString();
						}
	
						logger.info("ApplyContent to text document '" + params.getTextDocument() + "'...");
						logger.info(applyContent);

						if(languageServerImpl.doesClientApplyEdit() == true)
							languageServerImpl.applyEditClient(params.getTextDocument(), applyContent);
					
					}
					else {
						
						MessageParams messageParams = new MessageParams();
						messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
						messageParams.setMessage("ApplyEdit feature is not enabled on your side, thus changes may not be aplied to you."
								+ "Please enable it and try again later on");
						
						logger.info("Something went worong and client's apply edit feature is not enable, thus following ApplyEdit cannot be applied");

						if(languageServerImpl.doesClientApplyEdit() == true)
							languageServerImpl.notifyMessageClient(messageParams);
						
					}
				}
				
				logger.info("...end of document parsing");

				System.out.println("end of document parsing");
				System.out.println("");

			}

		}
		else {

			MessageParams messageParams = new MessageParams();
			messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
			messageParams.setMessage("Connection has not been established. Establish connection");
			
			logger.info("Client tired to change a document before completing initialization");
			
			if(languageServerImpl.doesClientApplyEdit() == true)
				languageServerImpl.notifyMessageClient(messageParams);
			
		}		
	
	}

	
	/**
	 * The document close notification is sent from the client to the server
	 * when the document got closed in the client. The document's truth now
	 * exists where the document's uri points to (e.g. if the document's uri is
	 * a file uri the truth now exists on disk).
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public void didClose(DidCloseTextDocumentParams params) {
		
		logger.info("didClose notification received from the client side...");
		logger.info(params.toString());
		
		System.out.println("didClose notification received from the client side");
		System.out.println(params.toString());
		System.out.println();
		
		LanguageServerImpl languageServerImpl = LanguageServerImpl.getInstance();
		MessageParams      messageParams      = new MessageParams();
		
		if(languageServerImpl.getInitialized() == true) {
			
			String resourceURI = params.getTextDocument().getUri();
			File file          = new File(resourceURI);
			
			if(!file.exists()) {
				
				messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
				messageParams.setMessage("Document " + params.getTextDocument().getUri() + " has not been previously opened. Open it in first place and try again");
				
				logger.info("Client tried to close document with uri '" + params.getTextDocument().getUri() + "but it has not been previously opened");

			}
			else {
				
				file.delete();

				messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
				messageParams.setMessage("Document " + params.getTextDocument().getUri() + " has been properly closed");
		
				logger.info("Document " + params.getTextDocument().getUri() + " has been properly closed");

			}
			
		}
		else {
			
			messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
			messageParams.setMessage("Connection has not been established. Establish connection");

			logger.info("Client tired to close a document before completing initialization");

		}

		if(languageServerImpl.doesClientApplyEdit() == true)
			languageServerImpl.notifyMessageClient(messageParams);

	}

	
	/**
	 * The document will save request is sent from the client to the server before the document is actually saved.
	 * The request can return an array of TextEdits which will be applied to the text document before it is saved.
	 * Please note that clients might drop results if computing the text edits took too long or if a server constantly fails on this request.
	 * This is done to keep the save fast and reliable.
	 * 
	 * Registration Options: TextDocumentRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<TextEdit>> willSaveWaitUntil(WillSaveTextDocumentParams params) {
		
		logger.info("willSaveWaitUntil notification received from the client side...");
		logger.info(params.toString());

		System.out.println(params.toString());
		System.out.println();
		
		TextEdit textEdit = new TextEdit();
		textEdit.setNewText((new String("​RootElement::PCK1::C1​")));
		textEdit.setRange(new Range(new Position(8, 1), new Position(8, 25)));
		
		List <TextEdit> edits = new ArrayList<>();
		edits.add(textEdit);

		return CompletableFuture.completedFuture(edits);
		
	}

	
	/**
	 * The document links request is sent from the client to the server to request the location of links in a document.
	 * 
	 * Registration Options: DocumentLinkRegistrationOptions
	 */
	@Override
	public CompletableFuture <List<DocumentLink>> documentLink(DocumentLinkParams params) {
		
		System.out.println(params.toString());
		System.out.println();
			
		List <DocumentLink> documentLinks = new ArrayList<>();
		documentLinks.add(null);
		
		return CompletableFuture.completedFuture(documentLinks);

	}

	
	/**
	 * The document link resolve request is sent from the client to the server to resolve the target of a given document link.
	 */
	@Override
	public CompletableFuture <DocumentLink> documentLinkResolve(DocumentLink params) {
		
		System.out.println(params.toString());
		System.out.println();
		
		DocumentLink myDocumentLink = params;
		myDocumentLink.setTarget("MyFile2.txt");
		
		return CompletableFuture.completedFuture(myDocumentLink);
		
	}

}