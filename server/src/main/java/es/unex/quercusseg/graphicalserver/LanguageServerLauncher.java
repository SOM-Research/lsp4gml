package es.unex.quercusseg.graphicalserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.lsp4j.jsonrpc.Launcher;
import org.eclipse.lsp4j.launch.LSPLauncher;
import org.eclipse.lsp4j.services.LanguageClient;


public class LanguageServerLauncher {
	
	public static void main(String[] args) throws Exception {
		
		
		//Create the language server
		LanguageServerImpl languageServer = LanguageServerImpl.getInstance();
		ExecutorService    threadPool     = Executors.newCachedThreadPool();

		//Port on which server is listening
//		Integer port = Integer.valueOf(args[0]);
		Integer port = 8080;
	
		//Create the socket server
		try (ServerSocket serverSocket = new ServerSocket(port)) {
			
			System.out.println("-- The language server is running on port " + port + " --");
			
			threadPool.submit(() -> {
				
				while (true) {
					
					//Wait for clients to connect
					Socket socket = serverSocket.accept();

					System.out.print("Incoming client....");
					
					//Create a JSON-RPC connection for the accepted socket
					Launcher <LanguageClient> launcher = LSPLauncher.createServerLauncher(languageServer, socket.getInputStream(), socket.getOutputStream());
					
					//Connect a remote client to our server
					languageServer.addLanguageClient(launcher.getRemoteProxy());

					System.out.println("connected");
					System.out.println();

                    //Start listening for incoming messages. When the JSON-RPC connection is closed,
					//disconnect the remote client from the chat server.
					launcher.startListening();
					

				}
				
			});
			
			System.out.println("-- Enter any character to stop --");
			System.out.println();
			System.in.read();
			System.exit(0);

		}
		
	}
	
	
}