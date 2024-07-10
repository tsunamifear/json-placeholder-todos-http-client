import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.List;

import org.junit.jupiter.api.Test;

import todo.Post;
import todo.Todo;
import todo.TodoClient;
import todo.TodoExceptionHandling;

class TodoClientTest {
	TodoClient client = new TodoClient();
	
	@Test
	void finalAll() throws IOException, InterruptedException {
		List<Todo> todoList = client.getAllfromTodosAsList();
		assertEquals(200, todoList.size(),"Our List size is should have been 200");
	}
	
	@Test
	void shouldReturnTodGivenValidId() throws IOException, InterruptedException, TodoExceptionHandling {
		Todo todo = client.getTodoByID(1);
		assertEquals(1,todo.userId(),"Somehow \"userId\" is wrong");
		assertEquals(1,todo.id(),"Somehow \"id\" is wrong");
		assertEquals("delectus aut autem",todo.title(),"Somehow the \"title\" is wrong");
		assertEquals(false, todo.completed(),"Somehow \"completed\" is wrong");		
	}
	
	@Test
	void shouldThrowNotFoundExceptionGivenInvalidId() {
		TodoExceptionHandling todoExceptionHandling = assertThrows(TodoExceptionHandling.class,
				() -> client.getTodoByID(999),"shouldThrowNotFoundExceptionGivenInvalidId method failed :(");
		assertEquals("couldn't find requested id",todoExceptionHandling.getMessage());
	}
	
	@Test
	void createTodo () throws IOException, InterruptedException {
		Todo todo = new Todo(201,201,"201 new entry row", false);
		HttpResponse<String> httpResponse = client.createNewTodo(todo);
	}
	@Test 
	void createPost() throws IOException, InterruptedException{
		Post post = new Post(101,101,"New 101 post entry", "The body of the new 101 post entry");
		HttpResponse<String> httpResponse = client.createNewPost(post);
		assertEquals(201, httpResponse.statusCode());
	}
	
	@Test
	void updateTodo() throws IOException, InterruptedException {
		Todo todo = new Todo(1,1,"1 new entry row with \"completed\" flag set as true", false);
		HttpResponse<String> httpResponse =  client.updateNewTodo(todo);
		assertEquals(200,httpResponse.statusCode());
	}
	
	@Test
	void deleteTodo() throws IOException, InterruptedException, TodoExceptionHandling {
		Todo todo = client.getTodoByID(1);
		HttpResponse<String> httpResponse = client.deleteTodoEntry(todo);
		assertEquals(200, httpResponse.statusCode());
	}

}
