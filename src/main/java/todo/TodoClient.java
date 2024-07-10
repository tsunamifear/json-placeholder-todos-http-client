package todo;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TodoClient {
	private final String TODO_URI = "https://jsonplaceholder.typicode.com/todos";
	private final String POST_URI = "https://jsonplaceholder.typicode.com/posts";
	private final HttpClient httpClient;
	private final ObjectMapper objectMapper;

	public TodoClient() {
		httpClient = HttpClient.newHttpClient();
		objectMapper = new ObjectMapper();
	}

	public String getAllfromTodoAsString() throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(TODO_URI))
				.GET()
				.build();
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		

		return response.body();
	}
	
	public List<Todo> getAllfromTodosAsList() throws IOException, InterruptedException {
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(TODO_URI))
				.GET()
				.build();
		
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		return objectMapper.readValue(httpResponse.body(), new TypeReference<>() {});
	}

	
	
	public Todo getTodoByID(int i) throws IOException, InterruptedException, TodoExceptionHandling {
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(TODO_URI+"/"+i))
				.GET()
				.build();
		HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		if(response.statusCode() == 404) {

			throw new TodoExceptionHandling("couldn't find requested id");
		}
		System.out.println("Inside getTodoByID method, the body is: " +response.body());
		return objectMapper.readValue(response.body(), Todo.class);
	}
	
	public HttpResponse<String> createNewTodo (Todo todo) throws IOException, InterruptedException{
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(TODO_URI))
				.header("Content-Type","application/json")
				.POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(todo)))
				.build();
		
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		System.out.println("Body of httpReponse object in createNewTodo method: "+httpResponse.body());
		System.out.println("httResponse of createNewTodo object is: "+httpResponse);
		return httpResponse;
	}
	
	public HttpResponse<String> createNewPost (Post post) throws IOException, InterruptedException{
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(POST_URI))
				.header("Content-Type","application/json")
				.POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(post)))
				.build();
		
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		System.out.println("Body of httpReponse object in createNewPost method: "+httpResponse.body());
		System.out.println("httResponse of createNewPost object is: "+httpResponse);
		return httpResponse;
	}
	
	public Post getPostById(int i) throws IOException, InterruptedException, TodoExceptionHandling{
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(POST_URI+"/"+i))
				.header("Content-Type","application/json")
				.GET()
				.build();
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		System.out.println("httpRequest we created is: "+httpRequest);
		System.out.println("httpRequest headers we created is: "+httpRequest.headers());
		System.out.println("httpRequest uri we created is: "+httpRequest.uri());
		System.out.println("httpRequest version we created is: "+httpRequest.version());
		System.out.println("httpRequest method we created is: "+httpRequest.method());
		if(httpResponse.statusCode() == 404) {
			throw new TodoExceptionHandling("couldn't find requested id");
		}
		System.out.println("Inside getPostById method, the httpResponse body is: " +httpResponse.body());
		Post post = objectMapper.readValue(httpResponse.body(), Post.class);
		return post;
	}
	
	
	public HttpResponse<String> updateNewTodo(Todo todo) throws IOException, InterruptedException{
		System.out.println("Inside updateNewTodo, todo.id should be 1: "+todo.id()+todo.completed());
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(TODO_URI+"/"+todo.id()))
				.PUT(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(todo)))
				.build();
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		System.out.println("httpResponse of updateNewTodo: "+httpResponse);
		System.out.println("httpResponse body of updateNewTodo: "+httpResponse.body());
		return httpResponse;
	}
	
	public HttpResponse<String> deleteTodoEntry(Todo todo) throws IOException, InterruptedException{
		HttpRequest httpRequest = HttpRequest.newBuilder()
				.uri(URI.create(POST_URI+"/"+todo.id()))
				.DELETE()
				.build();
		
		HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
		
		return httpResponse;
	}
}
