/**
 * Convert the string to lower case.
 */
public class LowerCaseFilter extends Filter {

	
	 /**
	  * Convert the string to lower case.
	  * @param s the string to apply the filter's transformation to
	  * @return a string whose value is the given string, but
	  *   with all letter converted to lower case
	  */
	//not a public method, but given TrimFilter.java , i guess i should do java doc here anyway 
	protected String doFilter(String s) {

		return s != null ? s.toLowerCase() : null;
	}
}
