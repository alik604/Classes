import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * <h1>a06: Inheritance, Polymorphism, Abstract Classes</h1>.
 * 
 * @author khizr ali pardhan | 100282129
 * @version 1.0
 * @since 2017-06-17 6:00pm
 * 
 *        The Class FiltersTester.
 */
public class FiltersTester {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		testMyLittleChainRule();
		testTrimFilter();
		testReverseCapsFilter();
		testCamelCapsFilter();
		testLowerCaseFilter();
		testUpperCaseFilter();
		testPrintStreamSink();
		System.err.println("*** PASSED ***");
	}

	/**
	 * Test a filter chain
	 */
	private static void testMyLittleChainRule() {
		/*
		 * i used anonymous objects below, doubt there is any negative impact
		 */

		ByteArrayOutputStream baos;
		PrintStream ps;

		// filter chain 1
		String expected = "hElLoW WoRlD!";
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		new CamelCapsFilter().add(new ReverseCapsFilter())
				.add(new TrimFilter()).add(new PrintStreamSink(ps))
				.filter("  Hellow World!  \t");
		assert baos.toString().equals(expected);

		// filter chain 2
		expected = "Hi wOrLd!";
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		new UpperCaseFilter().add(new CamelCapsFilter()).add(new TrimFilter())
				.add(new PrintStreamSink(ps)).filter("  Hi World!  ");
		assert baos.toString().equals(expected);

		// filter chain null
		expected = "";
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		Filter pipeline = new UpperCaseFilter().add(new CamelCapsFilter())
				.add(new TrimFilter()).add(new PrintStreamSink(ps));
		pipeline.filter(null);
		assert baos.toString().equals(expected);
	}

	/**
	 * Test print stream sink.
	 */
	private static void testPrintStreamSink() {
		ByteArrayOutputStream baos;
		PrintStream ps;
		Filter sink;

		// test mixed cases
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		sink = new PrintStreamSink(ps);
		sink.doFilter("A secret message.");
		assert baos.toString().equals("A secret message.");

		// test specials char
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		sink = new PrintStreamSink(ps);
		sink.doFilter("T/@@");
		assert baos.toString().equals("T/@@");

		// test null
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		sink = new PrintStreamSink(ps);
		sink.doFilter(null);
		assert baos.toString().equals("");

	}

	/**
	 * Test upper case filter.
	 */
	private static void testUpperCaseFilter() {
		UpperCaseFilter l = new UpperCaseFilter();
		String s;

		s = "hello"; // all lower case
		assert "HELLO".equals(l.doFilter(s));
		s = "HELLO";// all upper case
		assert "HELLO".equals(l.doFilter(s));
		s = "Hi"; // mixed case
		assert "HI".equals(l.doFilter(s));
		s = "@#a"; // with symbols
		assert "@#A".equals(l.doFilter(s));
		s = "  "; // spaces
		assert "  ".equals(l.doFilter(s));
		// special
		s = ""; // empty
		assert "".equals(l.doFilter(s));
		// special
		s = null; // null
		assert null == l.doFilter(s);
	}

	/**
	 * Test lower case filter.
	 */
	private static void testLowerCaseFilter() {
		LowerCaseFilter l = new LowerCaseFilter();
		String s;

		s = "HELLO"; // all upper case
		assert "hello".equals(l.doFilter(s));
		s = "hello";// all lower case
		assert s.equals(l.doFilter(s));
		s = "Hi\t"; // mixed case
		assert "hi\t".equals(l.doFilter(s));
		s = "@#A"; // with symbols
		assert "@#a".equals(l.doFilter(s));
		s = "  "; // just spaces
		assert "  ".equals(l.doFilter(s));
		// special
		s = ""; // empty
		assert "".equals(l.doFilter(s));
		// special
		s = null; // null
		assert null == l.doFilter(s);
	}

	/**
	 * Test camel caps filter.
	 */
	private static void testCamelCapsFilter() {
		CamelCapsFilter c = new CamelCapsFilter();
		String s;

		s = "hello"; // all lower case
		assert "HeLlO".equals(c.doFilter(s));
		s = "HELLO"; // all upper case
		assert "HeLlO".equals(c.doFilter(s));
		s = "##E"; // odd upper case
		assert s.equals(c.doFilter(s));
		s = "##z"; // odd lower case
		assert "##Z".equals(c.doFilter(s));
		s = "hello WORLD!"; // mixed case
		assert "HeLlO WoRlD!".equals(c.doFilter(s));
		// special
		s = ""; // empty
		assert "".equals(c.doFilter(s));
		// special
		s = null; // null
		assert null == c.doFilter(s);
	}

	/**
	 * Test reverse caps filter.
	 */
	private static void testReverseCapsFilter() {
		ReverseCapsFilter r = new ReverseCapsFilter();
		String s;

		s = "StRiNg"; // boundary
		assert "sTrInG".equals(r.doFilter(s));
		s = "Hello World!"; // 1st letter caps
		assert "hELLO wORLD!".equals(r.doFilter(s));
		s = "aa\t "; // lower case
		assert "AA\t ".equals(r.doFilter(s));
		s = "@@"; // lower case
		assert "@@".equals(r.doFilter(s));
		// special
		s = ""; // empty
		assert "".equals(r.doFilter(s));

		// special
		s = null; // null
		assert null == r.doFilter(s);
	}

	/**
	 * Test trim filter.
	 */
	private static void testTrimFilter() {
		TrimFilter f = new TrimFilter();
		String s;

		// typical
		s = "i am a string"; // no spaces
		assert s.equals(f.doFilter(s));
		s = "    leading"; // leading
		assert "leading".equals(f.doFilter(s));
		s = "trailing    "; // trailing
		assert "trailing".equals(f.doFilter(s));
		s = "  leading & trailing  "; // both
		assert "leading & trailing".equals(f.doFilter(s));
		s = "  a     b  "; // both with gap
		assert "a     b".equals(f.doFilter(s));
		s = "\tx\t"; // tabs
		assert "x".equals(f.doFilter(s));

		// boundary
		s = "    "; // all spaces
		assert "".equals(f.doFilter(s));

		// special
		s = ""; // empty
		assert "".equals(f.doFilter(s));

		// special
		s = null; // null
		assert null == f.doFilter(s);
	}

	static {
		boolean assertsEnabled = false;
		assert assertsEnabled = true; // Intentional side effect!!!
		if (!assertsEnabled) {
			throw new RuntimeException("Asserts must be enabled!!! java -ea");
		}
	}
}
