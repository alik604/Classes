/**
 * Reverses the capitalization of a filter.
 */
public class ReverseCapsFilter extends Filter {

	/**
	  * Reverses the capitalization of a filter.
	  * @param s the string to apply the filter's transformation to
	  * @return a string whose value is the given string, but
	  *   with case of all letters reversed
	  */
	protected String doFilter(String s) {
		if (s == null) {
			return null;
			// could use ternary statement a make this recursive (semi), but i
			// doubt there is any benefit
		}

		char[] arry = s.toCharArray();
		for (int i = 0; i < arry.length; i++) {
			if (arry[i] >= 'a' && arry[i] <= 'z') {
				arry[i] -= 32;
				// or arry[i] = Character.toUpperCase(arry[i]);
			} else if (arry[i] >= 'A' && arry[i] <= 'Z') {
				arry[i] += 32;
			}
		}

		return new String(arry);

	}
}