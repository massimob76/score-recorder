package server;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import login.Login;

import score.ScoreBoard;

import com.sun.net.httpserver.HttpServer;

public class Server {
	
	private static final int PORT = 8081;
	private static final int BACKLOG = 10;
	private static final int TEN_MIN_IN_MILLIS = 10 * 60 * 1000;
	
	
	public static void start() throws IOException {
		
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT), BACKLOG);
		
		Login login = new Login(TEN_MIN_IN_MILLIS);
		ScoreBoard scoreBoard = new ScoreBoard();
		server.createContext("/", new RequestHandler(login, scoreBoard));
		
		Executor exec = Executors.newCachedThreadPool();
		server.setExecutor(exec);
		
		server.start();
		
		System.out.println("listening on port: " + PORT);
		
	}
	
	public static void main(String[] args) throws IOException {
		start();
	}

}
