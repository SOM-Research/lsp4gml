package es.unex.quercusseg.graphicalserver;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;
import org.eclipse.lsp4j.ApplyWorkspaceEditParams;
import org.eclipse.lsp4j.ClientCapabilities;
import org.eclipse.lsp4j.CodeLensOptions;
import org.eclipse.lsp4j.CompletionOptions;
import org.eclipse.lsp4j.DocumentLinkOptions;
import org.eclipse.lsp4j.DocumentOnTypeFormattingOptions;
import org.eclipse.lsp4j.ExecuteCommandOptions;
import org.eclipse.lsp4j.InitializeParams;
import org.eclipse.lsp4j.InitializeResult;
import org.eclipse.lsp4j.MessageActionItem;
import org.eclipse.lsp4j.MessageParams;
import org.eclipse.lsp4j.Position;
import org.eclipse.lsp4j.PublishDiagnosticsParams;
import org.eclipse.lsp4j.Range;
import org.eclipse.lsp4j.Registration;
import org.eclipse.lsp4j.RegistrationParams;
import org.eclipse.lsp4j.SaveOptions;
import org.eclipse.lsp4j.ServerCapabilities;
import org.eclipse.lsp4j.ShowMessageRequestParams;
import org.eclipse.lsp4j.SignatureHelpOptions;
import org.eclipse.lsp4j.TextDocumentEdit;
import org.eclipse.lsp4j.TextDocumentSyncKind;
import org.eclipse.lsp4j.TextDocumentSyncOptions;
import org.eclipse.lsp4j.TextEdit;
import org.eclipse.lsp4j.Unregistration;
import org.eclipse.lsp4j.UnregistrationParams;
import org.eclipse.lsp4j.VersionedTextDocumentIdentifier;
import org.eclipse.lsp4j.WorkspaceEdit;
import org.eclipse.lsp4j.WorkspaceFoldersOptions;
import org.eclipse.lsp4j.WorkspaceServerCapabilities;
import org.eclipse.lsp4j.services.LanguageClient;
import org.eclipse.lsp4j.services.LanguageServer;
import org.eclipse.lsp4j.services.TextDocumentService;
import org.eclipse.lsp4j.services.WorkspaceService;

import es.unex.quercusseg.graphicalserver.util.Logging;



public class LanguageServerImpl implements LanguageServer {
	
	//Language client we are communicating with
	private final List <LanguageClient> clients = new CopyOnWriteArrayList <> ();

	//Every field on the clients list has its corresponding capabilities ont this array
	private final List <ClientCapabilities> clientCapabilities = new CopyOnWriteArrayList <> ();
	
	//Indicates if client we are talking to has established connection
	private boolean initialized = false;

	//Capabilities provided by the server
	private ServerCapabilities serverCapabilities;
	
	//TextDocumentServices provided by the language server
	private final TextDocumentServiceImpl textDocumentService = new TextDocumentServiceImpl();
	
	//WorkspaceServices provided by the language server
	private final WorkspaceServiceImpl workspaceService = new WorkspaceServiceImpl();

	//Singleton instance
	private static LanguageServerImpl instance = null;

	//Logging instance
	private static Logger logger = Logging.getInstance().getLogger();
	
	/**
	 * Singleton for this class
	 * @return
	 */
	public static LanguageServerImpl getInstance() {
		
		if(instance == null)
			instance = new LanguageServerImpl();
		
		return instance;
		
	}
	
	
	/**
	 * The initialize request is sent as the first request from the client to
	 * the server.
	 * 
	 * If the server receives request or notification before the initialize request it should act as follows:
	 * 	- for a request the respond should be errored with code: -32001. The message can be picked by the server.
	 *  - notifications should be dropped, except for the exit notification. This will allow the exit a server without an initialize request.
	 *  
	 * Until the server has responded to the initialize request with an InitializeResult 
	 * the client must not sent any additional requests or notifications to the server.
	 * 
	 * During the initialize request the server is allowed to sent the notifications window/showMessage, 
	 * window/logMessage and telemetry/event as well as the window/showMessageRequest request to the client.
	 */
	@Override
	public CompletableFuture <InitializeResult> initialize(InitializeParams params) {

		/**
		 * Initialize connection
		 */
		
			this.initialized = true;
		
		/**
		 * Show client capabilities and init params
		 */
			
			this.clientCapabilities.add(params.getCapabilities());
			
			logger.info("Client capabilities...");
			logger.info(params.toString());
			System.out.println(params.toString());
			System.out.println();
		
		/**
		 * Set server capabilities
		 */
			
			this.serverCapabilities = new ServerCapabilities();
			this.serverCapabilities.setCodeActionProvider(false);
			this.serverCapabilities.setCodeLensProvider(new CodeLensOptions(false));
			this.serverCapabilities.setCompletionProvider(new CompletionOptions(false, null));
			this.serverCapabilities.setDefinitionProvider(false);
			this.serverCapabilities.setDocumentFormattingProvider(false);
			this.serverCapabilities.setDocumentHighlightProvider(false);
			this.serverCapabilities.setDocumentLinkProvider(new DocumentLinkOptions(false));
			this.serverCapabilities.setDocumentOnTypeFormattingProvider(new DocumentOnTypeFormattingOptions(null));
			this.serverCapabilities.setDocumentRangeFormattingProvider(false);
			this.serverCapabilities.setDocumentSymbolProvider(true);
		
			List <String> commands = new ArrayList <String> ();
			commands.add("validate");
			commands.add("svg");
			this.serverCapabilities.setExecuteCommandProvider(new ExecuteCommandOptions(commands));
			
			this.serverCapabilities.setExperimental(null);
			this.serverCapabilities.setHoverProvider(false);
			this.serverCapabilities.setReferencesProvider(false);
			this.serverCapabilities.setRenameProvider(false);
			this.serverCapabilities.setSignatureHelpProvider(new SignatureHelpOptions(null));
			
			TextDocumentSyncOptions textDocumentSyncOptions = new TextDocumentSyncOptions();
			textDocumentSyncOptions.setOpenClose(true);
			textDocumentSyncOptions.setSave(new SaveOptions(true));
			textDocumentSyncOptions.setWillSave(true);
			textDocumentSyncOptions.setWillSaveWaitUntil(true);
			textDocumentSyncOptions.setChange(TextDocumentSyncKind.Full);
			this.serverCapabilities.setTextDocumentSync(textDocumentSyncOptions);
			
			this.serverCapabilities.setWorkspaceSymbolProvider(true);
			
			WorkspaceServerCapabilities workspaceServerCapabilities = new WorkspaceServerCapabilities();
			WorkspaceFoldersOptions     workspaceFoldersOptions     = new WorkspaceFoldersOptions();
			workspaceFoldersOptions.setChangeNotifications(true);
			workspaceFoldersOptions.setSupported(true);
			workspaceServerCapabilities.setWorkspaceFolders(workspaceFoldersOptions);
			this.serverCapabilities.setWorkspace(workspaceServerCapabilities);
	
			logger.info("Server capabilities...");
			logger.info(this.serverCapabilities.toString());

		/**
		 * Set initialize result
		 */
	
			InitializeResult initializeResult = new InitializeResult();
			initializeResult.setCapabilities(this.serverCapabilities);

		/**
		 * Send feedback to the client
		 */
			
			MessageParams messageParams = new MessageParams(org.eclipse.lsp4j.MessageType.Info, "Connection has been established");
			logger.info("Connection with client (id:" + params.getProcessId() + ") has been established");
			clients.get(0).showMessage(messageParams);
		
		/**
		 * Return initialize results to the client
		 */
			
			return CompletableFuture.completedFuture(initializeResult);
	
	}

	
	/**
	 * The shutdown request is sent from the client to the server. It asks the
	 * server to shutdown, but to not exit (otherwise the response might not be
	 * delivered correctly to the client). There is a separate exit notification
	 * that asks the server to exit.
	 */
	@Override
	public CompletableFuture <Object> shutdown() {
		
		this.initialized = false;
		logger.info("Shutdown completed");
		return CompletableFuture.completedFuture(new String("Shutdown completed - You've succesfully logged of from the server"));
		
	}

	
	/**
	 * A notification to ask the server to exit its process.
	 */
	@Override
	public void exit() {
		
		System.out.println("Client " + this.clients.get(0) + " has been disconnected");
		logger.info("Client " + this.clients.get(0) + " has been disconnected");
		
		MessageParams messageParams = new MessageParams();
		messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
		messageParams.setMessage("You have succesfully exited the server");
		clients.get(0).showMessage(messageParams);

		this.clients.clear();
				
	}

	
	/**
	 * Provides access to the textDocument services.
	 */
	@Override
	public TextDocumentService getTextDocumentService() {
			
		logger.info("Text document service requested");
		return this.textDocumentService;

	}

	
	/**
	 * Provides access to the workspace services.
	 */
	@Override
	public WorkspaceService getWorkspaceService() {
		
		logger.info("Workspace service requested");		
		return this.workspaceService;
		
	}
			
	
	/**
	 * Add language client
	 */
	public void addLanguageClient(LanguageClient _languageClient) {
		
		this.clients.add(_languageClient);
		
	}

	
	/**
	 * Get them language clients
	 */
	public List <LanguageClient> getLanguageClients() {
		
		return this.clients;
	
	}

	
	/**
	 * Checks if client has established the connection properly with the initialize method before
	 * sending any other request
	 */
	public void notifyMessageClient(MessageParams _messageParams) {
		
		MessageParams messageParams = _messageParams;
		messageParams.setType(org.eclipse.lsp4j.MessageType.Info);
		clients.get(0).showMessage(messageParams);
						
	}
	
	
	/**
	 * The workspace/applyEdit request is sent from the server to the client to modify resource on the client side.
	 * 
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public void applyEditClient(VersionedTextDocumentIdentifier versionedTextDocumentIdentifier, String _content) {
		
		ApplyWorkspaceEditParams applyWorkspaceEditParams = new ApplyWorkspaceEditParams();
		
		//Edits to be applied
		TextEdit textEdit = new TextEdit();
		textEdit.setNewText(_content);
		textEdit.setRange(new Range(new Position(0,1), new Position(_content.length(), 1)));
		
		List <TextEdit> edits = new ArrayList<>();
		edits.add(textEdit);

		LinkedHashMap<String, List<TextEdit>> linkedHashMap = new LinkedHashMap<String, List<TextEdit>>();
		linkedHashMap.put("Edit to be applied", edits);

		TextDocumentEdit textDocumentEdit = new TextDocumentEdit();
		textDocumentEdit.setTextDocument(versionedTextDocumentIdentifier);
		textDocumentEdit.setEdits(edits);
		
		List <TextDocumentEdit> documentChanges = new ArrayList<>();
		documentChanges.add(textDocumentEdit);
		
		WorkspaceEdit workspaceEdit = new WorkspaceEdit();
		workspaceEdit.setChanges(linkedHashMap);
		workspaceEdit.setDocumentChanges(documentChanges);
		
		applyWorkspaceEditParams.setEdit(workspaceEdit);
		clients.get(0).applyEdit(applyWorkspaceEditParams);
			
	}

	/**
	 * Return initialized property
	 */
	public Boolean getInitialized() {
		
		return this.initialized;
		
	}
	
	
	/**
	 * The client/registerCapability request is sent from the server to the client
	 * to register for a new capability on the client side.
	 * Not all clients need to support dynamic capability registration.
	 * A client opts in via the ClientCapabilities.dynamicRegistration property
	 */
	public void registerCapabilityClient() {
		
		RegistrationParams registrationParams = new RegistrationParams();
		
		Registration registration = new Registration();
		registration.setId("1");
		registration.setMethod("setRename");
		registration.setRegisterOptions(new String("No further means needed to register for this capability"));
		
		List <Registration> registrations = new ArrayList<>();
		registrations.add(registration);
		
		registrationParams.setRegistrations(registrations);
		
		try {
			System.out.println(clients.get(0).registerCapability(registrationParams).get().toString());
		} catch (Exception e) {}
		
	}

	
	/**
	 * The client/unregisterCapability request is sent from the server to the client
	 * to unregister a previously register capability.
	 */
	public void unregisterCapabilityClient() {
		
		UnregistrationParams unregistrationParams = new UnregistrationParams();
		
		Unregistration unregistration = new Unregistration();
		unregistration.setId("1");
		unregistration.setMethod("setRename");
			
		List <Unregistration> unregistrations = new ArrayList<>();
		unregistrations.add(unregistration);
		
		unregistrationParams.setUnregisterations(unregistrations);
		
		try {
			System.out.println(clients.get(0).unregisterCapability(unregistrationParams).get().toString());
		} catch (Exception e) {}
		
	}

	
	/**
	 * The telemetry notification is sent from the server to the client to ask
	 * the client to log a telemetry event.
	 */
	public void telemetryEventClient() {
		
		clients.get(0).telemetryEvent(new String("Client editor has been properly synchronized so far"));
		
	}
		
	
	/**
	 * The show message request is sent from a server to a client to ask the
	 * client to display a particular message in the user interface. In addition
	 * to the show message notification the request allows to pass actions and
	 * to wait for an answer from the client.
	 */
	public void showMessageRequest() {
		
		ShowMessageRequestParams showMessageRequestParams = new ShowMessageRequestParams();
		
		MessageActionItem messageActionItem = new MessageActionItem();
		messageActionItem.setTitle("Check error");
		
		List <MessageActionItem> actions = new ArrayList<>();
		actions.add(messageActionItem);
		
		showMessageRequestParams.setActions(actions);
		showMessageRequestParams.setType(org.eclipse.lsp4j.MessageType.Error);
		showMessageRequestParams.setMessage("Plase check this, an error has been detected");
		
		try {
			System.out.println(clients.get(0).showMessageRequest(showMessageRequestParams).get().toString());
		} catch (Exception e) {}

	}
	
	
	/**
	 * The log message notification is send from the server to the client to ask
	 * the client to log a particular message.
	 */
	public void logMessageClient() {
		
		MessageParams message = new MessageParams();
		message.setType(org.eclipse.lsp4j.MessageType.Log);
		message.setMessage("All changes have been succesfully saved on the server side");
		
		clients.get(0).logMessage(message);
		
	}
	
	
	/**
	 * Diagnostics notifications are sent from the server to the client to
	 * signal results of validation runs.
	 */
	public void publishDiagnogsticsToClient(PublishDiagnosticsParams params) {
		
		this.clients.get(0).publishDiagnostics(params);
		
	}
	
	
	/**
	 * The workspace/workspaceFolders request is sent from the server to the client
	 * to fetch the current open list of workspace folders.
	 *
	 * This API is a <b>proposal</b> from LSP and may change.
	 *
	 * @return null in the response if only a single file is open in the tool,
	 *         an empty array if a workspace is open but no folders are configured,
	 *         the workspace folders otherwise.
	 */
	public void workspaceFoldersClient() {
		
		//try {
			//System.out.println(clients.get(0).workspaceFolders().get().toString());
		//} catch (Exception e) {}

	}
	
	
	public ServerCapabilities getServerCapabilities () {
	
		return this.serverCapabilities;
		
	}
	
	
	public ClientCapabilities getClientCapabilities () {
		
		return this.clientCapabilities.get(0);
		
	}
	
}