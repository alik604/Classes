/**
 * Convert the string to upper case.
 */
public class UpperCaseFilter extends Filter {

	  /**
	  * Convert the string to upper case.
	  * @param s the string to apply the filter's transformation to
	  * @return a string whose value is the given string, but
	  *   with all letter converted to upper case
	  */
	protected String doFilter(String s) {

		return s != null ? s.toUpperCase() : null;

	}
}
