package utility;

import java.util.concurrent.TimeUnit;

import org.graphwalker.core.condition.EdgeCoverage;
import org.graphwalker.core.generator.RandomPath;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.core.machine.Machine;
import org.graphwalker.core.machine.SimpleMachine;
import org.graphwalker.core.model.Edge;
import org.graphwalker.core.model.Model;
import org.graphwalker.core.model.Vertex;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

public class GraphWalkerTest extends ExecutionContext{


	WebDriver driver = null;

	public void e_StartBrowser() {

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.get("http://parabank.parasoft.com");
		
		System.out.println("visiting: e_StartBrowser");
	}

	public void e_LoginFailed() {

		driver.findElement(By.name("username")).sendKeys("incorrect");
		driver.findElement(By.name("password")).sendKeys("incorrect");
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		System.out.println("visiting: e_LoginFailed");
	}

	public void e_LoginFailedAgain() {

		driver.findElement(By.name("username")).sendKeys("incorrect");
		driver.findElement(By.name("password")).sendKeys("incorrect");
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		System.out.println("visiting: e_LoginFailedAgain");
	}

	public void e_LoginSucceeded() {

		driver.findElement(By.name("username")).sendKeys("john");
		driver.findElement(By.name("password")).sendKeys("demo");
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		System.out.println("visiting: e_LoginSucceeded");
	}

	public void e_LoginSucceededAfterFailure() {

		driver.findElement(By.name("username")).sendKeys("john");
		driver.findElement(By.name("password")).sendKeys("demo");
		driver.findElement(By.xpath("//input[@value='Log In']")).click();
		System.out.println("visiting: e_LoginSucceededAfterFailure");
	}

	public void e_Logout() {

		driver.findElement(By.xpath("//a[text()='Log Out']")).click();
		System.out.println("visiting: e_Logout");
	}

	public void v_Start() {

	}

	public void v_HomePage() {

//		Assert.assertEquals(driver.getTitle(),"ParaBank | Welcome | Online Banking");
	}

	public void v_ErrorPage() {
		
		// 10-26-2015: fixed expected value - thanks to Roman Hesteric
//		Assert.assertEquals(driver.getTitle(),"ParaBank | Error");
	}

	public void v_AccountsOverviewPage() {

//		Assert.assertEquals(driver.getTitle(),"ParaBank | Accounts Overview");
	}

	
	@Test
	public void fullCoverageTest() {

		// Create an instance of our model
		Model model = createModel();

		// Build the model (make it immutable) and give it to the execution
		// context
		this.setModel(model.build());

		// Tell GraphWalker to run the model in a random fashion,
		// until every vertex is visited at least once.
		// This is called the stop condition.
//		this.setPathGenerator(new RandomPath(new VertexCoverage(100)));
		this.setPathGenerator(new RandomPath(new EdgeCoverage(100)));

		// Get the starting vertex (v_Start)
		setNextElement(model.getVertices().get(0));

		// Create the machine that will control the execution
		Machine machine = new SimpleMachine(this);

		// As long as the stop condition of the path generator is not fulfilled,
		// hasNext will return true.
		while (machine.hasNextStep()) {

			// Execute the next step of the model.
			machine.getNextStep();
		}
	}

	private Model createModel() {

		// Create a new, empty model
		Model model = new Model();

		// Create vertexes (nodes)
		Vertex v_Start = new Vertex().setName("v_Start");
		Vertex v_HomePage = new Vertex().setName("v_HomePage");
		Vertex v_ErrorPage = new Vertex().setName("v_ErrorPage");
		Vertex v_AccountsOverviewPage = new Vertex().setName("v_AccountsOverviewPage");

		// Create edges
		Edge e_StartBrowser = new Edge().setName("e_StartBrowser").setSourceVertex(v_Start).setTargetVertex(v_HomePage);
		Edge e_LoginFailed = new Edge().setName("e_LoginFailed").setSourceVertex(v_HomePage)
				.setTargetVertex(v_ErrorPage);
		Edge e_LoginFailedAgain = new Edge().setName("e_LoginFailedAgain").setSourceVertex(v_ErrorPage)
				.setTargetVertex(v_ErrorPage);
		Edge e_LoginSucceeded = new Edge().setName("e_LoginSucceeded").setSourceVertex(v_HomePage)
				.setTargetVertex(v_AccountsOverviewPage);
		Edge e_LoginSucceededAfterFailure = new Edge().setName("e_LoginSucceededAfterFailure")
				.setSourceVertex(v_ErrorPage).setTargetVertex(v_AccountsOverviewPage);
		Edge e_Logout = new Edge().setName("e_Logout").setSourceVertex(v_AccountsOverviewPage)
				.setTargetVertex(v_HomePage);

		// Add vertexes to the model
		model.addVertex(v_Start);
		model.addVertex(v_HomePage);
		model.addVertex(v_ErrorPage);
		model.addVertex(v_AccountsOverviewPage);

		// Add edges to the model
		model.addEdge(e_StartBrowser);
		model.addEdge(e_LoginFailed);
		model.addEdge(e_LoginFailedAgain);
		model.addEdge(e_LoginSucceeded);
		model.addEdge(e_LoginSucceededAfterFailure);
		model.addEdge(e_Logout);

		return model;
	}
	
}
