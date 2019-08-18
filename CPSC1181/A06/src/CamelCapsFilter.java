/**
 * Alternates the capitalization of each position in the string so that every
 * even position is in upper case, and every odd position is in lower case.
 * String indexes start at 0.
 */
public class CamelCapsFilter extends Filter {

	/**
	  * Alternates the capitalization of each position in the string.
	  * @param s the string to apply the filter's transformation to
	  * @return s, but with Alternates the capitalization.
	  */
	protected String doFilter(String s) {
		if (s == null) {
			return null;
		}
		char[] arry = s.toCharArray();

		for (int i = 0; i < arry.length; i++) {
			if (i % 2 == 0) {
				arry[i] = Character.toUpperCase(arry[i]);
			} else {
				arry[i] = Character.toLowerCase(arry[i]);
			}
		}
		return new String(arry);

	}
}
