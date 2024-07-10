package todo;



import java.io.IOException;
import java.util.List;

public class Application {

	public static void main(String[] args) throws IOException, InterruptedException, TodoExceptionHandling {
		// TODO Auto-generated method stub
		System.out.println("test");
		TodoClient todoClient = new TodoClient();
//		System.out.println(todoClient.getAllfromTodoAsString());
		List<Todo> todoList = todoClient.getAllfromTodosAsList();
//		System.out.println(todoList);
		System.out.println("todoClient.getTodoByID method invoked: "+todoClient.getTodoByID(1));
		Todo todo = new Todo(201,201,"201 new entry row", false);
		Post post = new Post(101,101,"New 101 post entry", "The body of the new 101 post entry");
		System.out.println("201 todo object has: "+todo);
		System.out.println("101 post object has: "+post);
		System.out.println("calling createNewTodo method:");
		todoClient.createNewTodo(todo);
		System.out.println("calling createNewPost method:");
		todoClient.createNewPost(post);
		System.out.println("----");
//		todoList = todoClient.getAllfromTodosAsList();
//		System.out.println(todoList);
		System.out.println("LETS SEE IF IT WORKED: ");
//		System.out.println(todoClient.getTodoByID(201));
		System.out.println(todoClient.getPostById(55));
		
		Todo todoUpdate = new Todo(1,1,"1 new entry row with \"completed\" flag set as true", true);
		System.out.println(todoClient.updateNewTodo(todoUpdate));
		System.out.println(todoClient.getTodoByID(1));
	}
	
	
}
