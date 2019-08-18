/**
* A filter for transforming text; filters may be chained together to form a pipeline.
*
* For: Langara College, CPSC 1181-003 Summer 2017, Instructor: Jeremy Hilliker.
*
* @author Jeremy Hilliker langara
* @version 2017-06-03 02:00
*/
public abstract class Filter {

	private Filter next;

	/**
	* Applies the transform for this filter, then sends the result down the pipeline.
	* @param in the string to filter.
	*/
	public final void filter(String in) {
		String out = doFilter(in);
		if(next != null) {
			next.filter(out);
		}
	}

	/**
	* Applies the transform for this filter.
	* @param in the string to filter.
	* @return the transformed string.
	*/
	protected abstract String doFilter(String in);

	/** Adds a filter to the end of the pipeline. Usage:
	* <pre>
	* Filter pipeline = new Filter1().add(new Filter2())
	*                             .add(new Filter3())
	*                             .add(new Filter4())
	*                             .add(new Sink());
	* </pre>
	* @param aNext the filter to add to the end of the pipeline.
	* @return this filter (for fluent interface)
	*/
	public Filter add(Filter last) {
		if(next == null) {
			next = last;
		} else {
			next.add(last); // recursion
		}
		return this;
	}
}
