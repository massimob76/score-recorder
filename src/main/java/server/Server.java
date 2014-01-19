package server;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import score.ScoresBoard;

import login.Login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

public class Server {
	
	private static final int PORT = 8081;
	
	private static Login login = new Login();
	private static ScoresBoard scoresBoard = new ScoresBoard();
	
	public static void start() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(PORT),0);
		server.createContext("/", new Handler());
		server.setExecutor(null);
		server.start();
		System.out.println("listening on port: " + PORT);
	}
	
	private static class Handler implements HttpHandler {

		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			URI uri = httpExchange.getRequestURI();
			Pattern p = Pattern.compile("/(\\d+)/(login|score|highscorelist)");
			Matcher m = p.matcher(uri.getPath());
			
			if (m.matches()) {
				
				String action = m.group(2);
				
				String response = null;
				
				Integer userId;
				int levelId;
				
				switch(action) {
				
				case "login":
					userId = Integer.parseInt(m.group(1));
					response = login.getSessionKey(userId);
					break;
					
				case "score":
					int score = getScoreFromRequestBody(httpExchange.getRequestBody());
					levelId = Integer.parseInt(m.group(1));
					String sessionKey = getSessionKeyForQueryParams(uri);
					userId = login.getUserId(sessionKey);
					if (userId != null) { // considering only valid sessions
						scoresBoard.post(userId, levelId, score);
					}
					response = "";
					break;
					
				case "highscorelist":
					levelId = Integer.parseInt(m.group(1));
					response = scoresBoard.retrieve(levelId);
					break;
				}
				
				send200(httpExchange, response);
				
			} else {
				send404(httpExchange);
			}
			
		}
		
		private String getSessionKeyForQueryParams(URI uri) {
			String query = uri.getQuery();
			return query.split("=")[1];
		}

		private int getScoreFromRequestBody(InputStream requestBody) throws IOException {
			InputStreamReader isr = new InputStreamReader(requestBody);
			BufferedReader br = new BufferedReader(isr);
			String payload = br.readLine();
			br.close();
			isr.close();
			
			return Integer.parseInt(payload.split("=")[1]);
			
		}

		private static void send404(HttpExchange httpExchange) throws IOException {
			httpExchange.sendResponseHeaders(404, -1);
		}
		
		private void send200(HttpExchange httpExchange, String response) throws IOException {
			httpExchange.sendResponseHeaders(200, response.length());
			
			OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.flush();
			os.close();
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		start();
	}

}
