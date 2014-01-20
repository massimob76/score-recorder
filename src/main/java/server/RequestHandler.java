package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import login.Login;
import score.ScoreBoard;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class RequestHandler implements HttpHandler {
	
	private final Login login;
	private final ScoreBoard scoreBoard;
	private static final String REQUEST_PATTERN = "/(\\d+)/(login|score|highscorelist)";
	
	public RequestHandler(Login login, ScoreBoard scoreBoard) {
		this.login = login;
		this.scoreBoard = scoreBoard;
	}


	@Override
	public void handle(HttpExchange httpExchange) throws IOException {
		URI uri = httpExchange.getRequestURI();
		Pattern p = Pattern.compile(REQUEST_PATTERN);
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
				if (userId == null) {
					sendNotAuthorized(httpExchange);
				} else {
					scoreBoard.post(userId, levelId, score);
				}
				response = "";
				break;
				
			case "highscorelist":
				levelId = Integer.parseInt(m.group(1));
				response = scoreBoard.retrieve(levelId);
				break;
			}
			
			sendOk(httpExchange, response);
			
		} else {
			sendNotFound(httpExchange);
		}
		
	}


	private String getSessionKeyForQueryParams(URI uri) {
		String query = uri.getQuery();
		return query.split("=")[1];
	}

	private int getScoreFromRequestBody(InputStream requestBody) throws IOException {
		InputStreamReader isr = new InputStreamReader(requestBody);
		BufferedReader br = new BufferedReader(isr);
		
		String payload;
		try {
			payload = br.readLine();
		} finally {
			br.close();
			isr.close();
		}
		
		return Integer.parseInt(payload.split("=")[1]);
		
	}
	
	private static void sendNotAuthorized(HttpExchange httpExchange) throws IOException {
		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
	}

	private static void sendNotFound(HttpExchange httpExchange) throws IOException {
		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, -1);
	}
	
	private static void sendOk(HttpExchange httpExchange, String response) throws IOException {
		httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.length());
		
		OutputStream os = httpExchange.getResponseBody();
		try {
			os.write(response.getBytes());
		} finally {
			os.flush();
			os.close();
		}
	}
		
}
