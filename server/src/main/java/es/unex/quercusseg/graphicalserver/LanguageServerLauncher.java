package es.unex.quercusseg.graphicalserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.io.*;
import java.util.regex.*;
import java.util.Scanner;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;

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

					InputStream in = socket.getInputStream();

OutputStream out = socket.getOutputStream();

//translate bytes of request to string
// String data = new Scanner(in,"UTF-8").useDelimiter("\\r\\n\\r\\n").next();

// Matcher get = Pattern.compile("^GET").matcher(data);

// 					//WebSocket handshaking
// 					if (get.find()) {
//     Matcher match = Pattern.compile("Sec-WebSocket-Key: (.*)").matcher(data);
//     match.find();
//     byte[] response = ("HTTP/1.1 101 Switching Protocols\r\n"
//             + "Connection: Upgrade\r\n"
//             + "Upgrade: websocket\r\n"
//             + "Sec-WebSocket-Accept: "
//             + DatatypeConverter
//             .printBase64Binary(
//                     MessageDigest
//                     .getInstance("SHA-1")
//                     .digest((match.group(1) + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11")
//                             .getBytes("UTF-8")))
//             + "\r\n\r\n")
//             .getBytes("UTF-8");

//     out.write(response, 0, response.length);
// }
					
					//Create a JSON-RPC connection for the accepted socket
					Launcher <LanguageClient> launcher = LSPLauncher.createServerLauncher(languageServer, in, out);
					
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