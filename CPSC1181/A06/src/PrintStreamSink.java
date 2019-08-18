import java.io.IOException;
import java.io.PrintStream;

/**
 * A sink that prints to the given PrintStream (eg: System.out).
 */
public class PrintStreamSink extends Filter {

	private final PrintStream PSS;

	/**
	 * Instantiates a new prints the stream sink.
	 * @param p the PrintStreamSink
	 */
	public PrintStreamSink(PrintStream p) {
		PSS = p;
	}

	/**
	  * writes string to PrintStreamSink
	  * @param s the string to write to PrintStreamSink
	  * @return s 
	  */
	protected String doFilter(String s) {
		if (s == null) {
			return null;
		}
		try {
			PSS.write(s.getBytes());
		} catch (IOException e) {
			System.err.println("java IOException occurred*");
		}
		return s;
	}

}