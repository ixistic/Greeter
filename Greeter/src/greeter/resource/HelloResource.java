package greeter.resource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * HelloResource provides RESTful web resources using JAX-RS
 * annotations to map requests to request handling code,
 * and to inject resources into code.
 * 
 * @author jim
 *
 */
@Path("/hello")
public class HelloResource {
	// Hello resource stores custom greetings in a Map.
	// The key is the user name obtained from the request path.
	private Map<String, String> greetings = new HashMap<>();
	@Context
	UriInfo uriInfo;
	
	public HelloResource() {
		// Jersey requires a default constructor for resources it instantiates.
	}

	@GET
	@Produces("text/plain")
	public String getGreeting( ) {
		return "Hello, ancient surfer.";
	}

	@GET
	@Produces("text/html")
	public String getHtmlGreeting() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><title>Greetings!</title></head>");
		sb.append("\n<body>\n");
		sb.append("<h1>Hello, Web Surfer.</h1>\n");
		sb.append("</body>\n</html>\n");
		return sb.toString();
	}
	
	/**
	 * Put a greeting. JAX-RS automatically provides an InputStream
	 * or Reader (!) to read the request body.
	 * @param name name derived from path parameter
	 * @param reader body of the request
	 */
	@PUT
	@Path("{name}")
	public void putGreeting(@PathParam("name") String name, Reader reader) {
		BufferedReader buff = new BufferedReader(reader);
		try {
			String greeting = buff.readLine().trim();
			greetings.put(name, greeting);
			
			System.out.printf("New greeting for %s is %s\n", name, greeting);
		} catch (IOException | NullPointerException ioe) {
			//return Response.serverError().build();
		} finally {
			try { buff.close(); } catch (IOException e) { /* ignore */ }
		}
	}
}
