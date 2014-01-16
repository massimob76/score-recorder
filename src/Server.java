import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;

public class Server {
	
	public static void start() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000),0);
		server.createContext("/", new Handler());
		server.setExecutor(null);
		server.start();
	}
	
//	GETÊ/<userid>/login
//	POSTÊ/<levelid>/score?sessionkey=<sessionkey>
//	GETÊ/<levelid>/highscorelist
	
	private static class Handler implements HttpHandler {

		@Override
		public void handle(HttpExchange httpExchange) throws IOException {
			URI uri = httpExchange.getRequestURI();
			uri.getPath();
			Pattern p = Pattern.compile("/(\\d+)/(login|score|highscorelist)");
			Matcher m = p.matcher(uri.getPath());
			
			System.out.println("request");
			if (m.matches()) {
				
				int value = Integer.parseInt(m.group(1));
				
				System.out.println("matching");
				
				String response = null;
				
				if (m.group(2).equals("login")) {
					
					int userId = Integer.parseInt(m.group(1));
					response = new Login().getSessionKey(userId);
				
				} else if (m.group(2).equals("score")) {
					
					response = "score";

				} else if (m.group(2).equals("highscorelist")) {
					
					response = "highscorelist";
					
				}
				
				send200(httpExchange, response);
				
			} else {
				
				System.out.println("not matching");
				send404(httpExchange);
			}
			

		}
		
		private void send404(HttpExchange httpExchange) throws IOException {
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
