package server;

import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;

import score.ScoreBoard;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;

import login.Login;

import com.sun.net.httpserver.HttpExchange;

public class RequestHandlerTest {
	
	private RequestHandler iut;
	private HttpExchange httpExchangeMock;
	private Login loginMock;
	private ScoreBoard scoreBoardMock;
	private OutputStream osMock;


	@Before
	public void setUp() {
		httpExchangeMock = mock(HttpExchange.class);
		loginMock = mock(Login.class);
		scoreBoardMock = mock(ScoreBoard.class);
		osMock = mock(OutputStream.class);

		iut = new RequestHandler(loginMock, scoreBoardMock);
	}

	@Test
	public void shouldBeAbleToAcceptLoginRequest() throws IOException, URISyntaxException {
		final int userId = 123;
		final String sessionKey = "abcd";
		
		URI loginUri = new URI("/123/login");
		when(httpExchangeMock.getRequestURI()).thenReturn(loginUri);
		when(httpExchangeMock.getResponseBody()).thenReturn(osMock);
		when(loginMock.getSessionKey(userId)).thenReturn(sessionKey);

		iut.handle(httpExchangeMock);
		
		verify(httpExchangeMock).sendResponseHeaders(HttpURLConnection.HTTP_OK, sessionKey.length());
		verify(osMock).write(sessionKey.getBytes());

	}
	
	@Test
	public void shouldBeAbleToAcceptScorePost() throws IOException, URISyntaxException {
		final int levelId = 12;
		final String sessionKey = "abcd";
		final int score = 1500;
		final int userId = 123;
		
		URI scorePostUri = new URI("/12/score?sessionkey=abcd");
		when(httpExchangeMock.getRequestURI()).thenReturn(scorePostUri);
		when(httpExchangeMock.getResponseBody()).thenReturn(osMock);
		
		InputStream is = new ByteArrayInputStream("body=1500".getBytes());
		when(httpExchangeMock.getRequestBody()).thenReturn(is);

		when(loginMock.getUserId(sessionKey)).thenReturn(userId);

		iut.handle(httpExchangeMock);
		
		verify(httpExchangeMock).sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
		verify(scoreBoardMock).post(userId, levelId, score);
	}
	
	@Test
	public void shouldReturnUnauthorizedForScorePostWithInvalidSession() throws IOException, URISyntaxException {
		final String sessionKey = "abcd";
		
		URI scorePostUri = new URI("/12/score?sessionkey=abcd");
		when(httpExchangeMock.getRequestURI()).thenReturn(scorePostUri);
		when(httpExchangeMock.getResponseBody()).thenReturn(osMock);
		
		InputStream is = new ByteArrayInputStream("body=1500".getBytes());
		when(httpExchangeMock.getRequestBody()).thenReturn(is);

		when(loginMock.getUserId(sessionKey)).thenReturn(null);

		iut.handle(httpExchangeMock);
		
		verify(httpExchangeMock).sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, -1);
	}
	
	@Test
	public void shouldReturnHighScoreList() throws IOException, URISyntaxException {
		final String highScoreList = "12=123";
		final int levelId = 12;
		
		URI scorePostUri = new URI("/12/highscorelist");
		when(httpExchangeMock.getRequestURI()).thenReturn(scorePostUri);
		when(httpExchangeMock.getResponseBody()).thenReturn(osMock);
		
        when(scoreBoardMock.retrieve(levelId)).thenReturn(highScoreList);
		iut.handle(httpExchangeMock);
		
		verify(httpExchangeMock).sendResponseHeaders(HttpURLConnection.HTTP_OK, highScoreList.length());
		verify(osMock).write(highScoreList.getBytes());	
	}
	
}
