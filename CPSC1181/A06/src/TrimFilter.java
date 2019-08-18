/**
* TrimFilter will remove the leading and trailing whitespace from a string.
*/
public class TrimFilter extends Filter {

  /**
  * TrimFilter will remove the leading and trailing whitespace from a string.
  * @param s the string to apply the filter's transformation to
  * @return a string whose value is the given string,
  *   with any leading and trailing whitespace removed. AKN: Javadoc
  */
  protected String doFilter(String s) {
    return s != null ? s.trim() : null;
  }
}
