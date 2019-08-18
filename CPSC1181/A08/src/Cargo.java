import java.util.*;
import java.util.stream.*;

/**
 * A cargo module for holding Items. Items can be retrieved in various ways.
 * 
 * @author Jeremy Hilliker @ Langara
 * @author Khizr Ali Pardhan
 * @version 2017-06-28 23h20
 * @see <a href=
 *      "https://d2l.langara.bc.ca/d2l/le/content/88736/viewContent/1313159/View">a
 *      03: Cargo</a>
 */
public class Cargo {

	private final int maxWeight;
	private final Collection<Item> cargo;

	/**
	 * Create a new cargo module with a given weight capacity.
	 * 
	 * @param aMaxWeight
	 *            the maximum weight capacity of the cargo module before it
	 *            becomes overweight.
	 */
	public Cargo(int aMaxWeight) {
		maxWeight = aMaxWeight;
		cargo = new HashSet<>();
	}

	/**
	 * Gets the maximum weight capacity of the cargo module.
	 * 
	 * @return the maximum weight capacity of the cargo module.
	 */
	public int getMaxWeight() {
		return maxWeight;
	}

	/**
	 * Gets the number of items currently stored.
	 * 
	 * @return the number of items currently stored
	 */
	public int getItemCount() {
		return cargo.size();
	}

	/**
	 * Get the sum of the weights of all of the items currently stored.
	 * 
	 * @return the sum of the weights of all of the items currently stored
	 */
	public int getTotalWeight() {
		return (int) cargo.stream().mapToInt(Item -> Item.getWeight()).sum();
	}

	/**
	 * Determines if the total weight of all of the stored items exceeds the
	 * weight capacity of the cargo module.
	 * 
	 * @return true iff the total weight of all of the stored items exceeds the
	 *         weight capacity of the cargo module.
	 */
	public boolean isOverWeight() {
		return cargo.stream().mapToInt(Item -> Item.getWeight()).sum() > maxWeight;
	}

	/**
	 * Adds an item to the cargo module.
	 * 
	 * @param item
	 *            the item to add. It must not already be in the cargo module.
	 */
	public void add(Item item) {
		if (cargo.stream().anyMatch(
				Item -> Item.getTracking() != item.getTracking())
				|| cargo.isEmpty()) {
			cargo.add(item);
		}
	}

	/**
	 * Determines if an item is stored in the cargo module.
	 * 
	 * @param tracking
	 *            the tracking number of the item to search for
	 * @return true iff an item with the given tracking number is stored in the
	 *         cargo module
	 */
	public boolean contains(long tracking) {
		return (cargo.stream().anyMatch(Item -> Item.getTracking() == tracking));
	}

	/**
	 * Removes an item from the cargo hold.
	 * 
	 * @param tracking
	 *            the tracking number of the item to remove from the cargo
	 *            module
	 * @return true iff an item was removed
	 */
	public boolean remove(long tracking) {
		return cargo.removeIf(Item -> Item.tracking == tracking);
	}

	/**
	 * Gets all items with the given name.
	 * 
	 * @param name
	 *            the name to filter items by
	 * @return the set of all items with the given name.
	 */
	public Set<Item> getItems(String name) {
		return cargo.stream().filter(Item -> Item.name.equals(name))
				.collect(Collectors.toSet());
	}

	/**
	 * Gets the heaviest item in the cargo module.
	 * 
	 * @return the heaviest item in the cargo module; null if empty
	 */
	public Item getHeaviest() {
		// reference used:
		// https://stackoverflow.com/questions/32699583/java-8-stream-get-object-from-filter-result
		return cargo.stream().max(Comparator.comparing(Item::getWeight))
				.orElse(null);
	}

	/**
	 * Gets the heaviest item with the given name.
	 * 
	 * @param name
	 *            the name with which to filter
	 * @return the heaviest item with the given name; null if no item matches
	 *         the name
	 */
	public Item getHeaviest(String name) {

		return cargo.stream().filter(s -> s.getName().equals(name))
				.max(Comparator.comparing(Item::getWeight)).orElse(null);
	}

	/**
	 * Gets the average weight of the items in the cargo module.
	 * 
	 * @return the average weight of the items in the cargo module; NaN if empty
	 */
	public double getAverageWeight() {
		if (cargo.size() == 0) {
			return Double.NaN;
		}
		return cargo.stream().mapToDouble(Item -> Item.getWeight())
				.summaryStatistics().getAverage();
		// or .sum() / # of Items. i prefer my, even though i need to import
		// more stuff.
	}

	/**
	 * Gets the average weight of the items with the given name.
	 * 
	 * @param name
	 *            the name with which to filter
	 * @return the average weight of the items with the given name; NaN if no
	 *         item matches the given name
	 */
	public double getAverageWeight(String name) {
		if (cargo.stream().filter(s -> s.getName().equals(name)).count() == 0) {
			return Double.NaN;
		}

		return cargo.stream().filter(s -> s.getName().equals(name))
				.mapToDouble(Item -> Item.getWeight()).summaryStatistics()
				.getAverage();
	}

	/**
	 * Retrieves the items with the given tracking numbers.
	 * 
	 * @param tracking
	 *            the tracking numbers of the items to retrieve
	 * @return the items with the given tracking numbers
	 */
	public Set<Item> getItems(long... tracking) {
		if (tracking == null) {
			return null;
		}
		Set<Item> mySet = cargo
				.stream()
				.filter(i -> LongStream.of(tracking).anyMatch(
						t -> t == i.getTracking())).collect(Collectors.toSet());
		// got help for help center, was told to use "LongStream.of(tracking)"
		// i only knew how to compare to a single element
		return mySet;
	}

	/**
	 * Retrieves the items with the given names.
	 * 
	 * @param names
	 *            the names of the items to retrieve
	 * @return a map from names to sets of items with that name.
	 */
	public Map<String, List<Item>> getItemsMap(String... names) {
		if (names == null) {
			return null;
		}
		return cargo.stream()
				.filter(i -> Stream.of(names).anyMatch(s -> s.equals(i.name)))
				.collect(Collectors.groupingBy(i -> i.name));
	}

	/**
	 * An item in the cargo module. Items are immutable.
	 */
	public static class Item {
		private final long tracking;
		private final String name;
		private final int weight;

		private static long nextTrack = 101;

		/**
		 * An item for transport.
		 * 
		 * @param aName
		 *            the name of the item
		 * @param aWeight
		 *            the weight of the item
		 */
		public Item(String aName, int aWeight) {
			tracking = nextTrack++;
			name = aName;
			weight = aWeight;
		}

		public long getTracking() {
			return tracking;
		}

		public int getWeight() {
			return weight;
		}

		public String getName() {
			return name;
		}
	}
}