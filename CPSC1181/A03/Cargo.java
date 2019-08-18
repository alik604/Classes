package A03;
import java.awt.HeadlessException;
import java.util.*;

/** A cargo module for holding Items. Items can be retrieved in various ways.
    @author Jeremy Hilliker @ Langara
    @author khizr ali pardhan
    @version 2017-05-21 15h00
    @see <a href="https://d2l.langara.bc.ca/d2l/le/content/88736/viewContent/1313159/View">a 03: Cargo</a>
*/

// as suggested i tried to not add repetitive documentation which explains my code.

public class Cargo {

	private final int maxWeight;
	private final HashSet<Item> cargo;

    /** Create a new cargo module with a given weight capacity.
        @param aMaxWeight the maximum weight capacity of the cargo module before it becomes overweight.
    */
	public Cargo(int aMaxWeight) {
		maxWeight = aMaxWeight;
		cargo = new HashSet<Cargo.Item>();
	}

    /** Gets the maximum weight capacity of the cargo module.
        @return the maximum weight capacity of the cargo module.
    */
    public int getMaxWeight() {
        return maxWeight;
    }

    /** Gets the number of items currently stored.
        @return the number of items currently stored
    */
	public int getItemCount() {
		return cargo.size();
	}

    /** Get the sum of the weights of all of the items currently stored.
        @return the sum of the weights of all of the items currently stored
    */
	public int getTotalWeight() {

		// create iterator and run thought all; add up and return weight

		int totalWeight = 0;
		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			totalWeight += Iterator.next().weight;
		}
		return totalWeight;
	}

    /** Determines if the total weight of all of the stored items exceeds the weight capacity of the cargo module.
        @return true iff the total weight of all of the stored items exceeds the weight capacity of the cargo module.
    */
	public boolean isOverWeight() {
	/*	double totalWeight = 0;
		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			totalWeight += Iterator.next().weight;
		}*/
		if (getTotalWeight() > getMaxWeight()) {
			return true;
		}
		return false;
	}

    /** Adds an item to the cargo module.
        @param item the item to add. It must not already be in the cargo module.
    */
	public void add(Item item) {
		cargo.add(item);
	}

    /** Determines if an item is stored in the cargo module.
        @param tracking the tracking number of the item to search for
        @return true iff an item with the given tracking umber is stored in the cargo module
    */
    public boolean contains(long tracking) {
    	//create iterator and run thought all; if there is an object with given tracking number, return true
		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			if (tracking == Iterator.next().tracking) {
				return true;
			}
		}
		
    	return false;
    }

    /** Removes an item from the cargo hold.
        @param tracking the tracking number of the item to remove from the cargo module
        @return true iff an item was removed
    */
	public boolean remove(long tracking) {
		//create iterator and run thought all; if there is an object with given tracking number, remove it and return true
		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			if (tracking == Iterator.next().tracking) {
				Iterator.remove();
				return true;
			}
		}
		return false;
	}

    /** Gets all items with the given name.
        @param name the name to filter items by
        @return the set of all items with the given name.
    */
	public HashSet<Item> getItems(String name) {
	
		HashSet<Item> mySet = new HashSet<Cargo.Item>();

		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			Item myItem = Iterator.next();
			if (name == myItem.name) {
				mySet.add(myItem);
			}
		}
		return mySet;
	}

    /** Gets the heaviest item in the cargo module.
        @return the heaviest item in the cargo module; null if empty
    */
	public Item getHeaviest() {
		List<Item> list = new ArrayList<Item>(cargo);
		Item heaviest = (list.isEmpty()) ? null : list.get(0);
		// ^^*how are other students doing this part?*^^^
		/*  
		  heaviest;
		if (list.isEmpty()) {
			heaviest = null;
		} else {
			heaviest = list.get(0);// or set to 0
		}  
		 */
		Item myItem;

		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			myItem = Iterator.next();// Iterator.next();
			if (myItem.weight >= heaviest.weight) {
				heaviest = myItem;
			}
		}
		return heaviest;
	}

    /** Gets the heaviest item with the given name.
        @param name the name with which to filter
        @return the heaviest item with the given name; null if no item matches the name
    */
	public Item getHeaviest(String name) {
		return getHeaviest(getItems(name));
		// return the heaviest item in a given set. "given set" in this case
		// being the return value of the method 'getItem' with the name param
		// being used as the filtering argument
	}
	  /** Gets the heaviest item with the given set.
    @param hashset the hashset with which to filter
    @return the heaviest item in the given set; null if no items in the given set
*/
	private Item getHeaviest(HashSet<Item> sub) {
		Item heaviest = null;// <- why you no default to null?
		// given how my code works, i don't need to explicitly handle hashset
		// being null
		Item myItem;

		Iterator<Cargo.Item> Iterator = sub.iterator();

		if (Iterator.hasNext())
			heaviest = Iterator.next();//assume first is heaviest before running though loop. 
		while (Iterator.hasNext()) {
			myItem = Iterator.next();// Iterator.next();
			if (myItem.weight >= heaviest.weight) {// not sure if >= or >. this passes test. i return last 'heaviest' item(well that would technically be a tie...)  
				heaviest = myItem;
			}
		}
		return heaviest;
	}

    /** Gets the average weight of the items in the cargo module.
        @return the average weight of the items in the cargo module; NaN if empty
    */
	public double getAverageWeight() {
		double average = 0;
		Iterator<Cargo.Item> Iterator = cargo.iterator();
		while (Iterator.hasNext()) {
			average += Iterator.next().weight;

		}
		average /= cargo.size();
		return average;
	}

    /** Gets the average weight of the items with the given name.
        @param name the name with which to filter
        @return the average weight of the items with the given name; NaN if no item matches the given name
    */
    public double getAverageWeight(String name) {
		return getAverageWeight(getItems(name));
		// return the average weight in a given set. "given set" in this case
		// being the return value of the method 'getItem' with the param 'name' 
		// being used as the filtering argument
 
    }
    /** Gets the average weight of the items from given set.
    @param hashset of with which to filter
    @return the average weight of the items with the given name; NaN if no item matches the given name
*/
	private double getAverageWeight(HashSet<Item> sub) {
		double average = 0;
		// add up all weights of items in sub. return average/total values.
		Iterator<Cargo.Item> Iterator = sub.iterator();
		while (Iterator.hasNext()) {
			average += Iterator.next().weight;
		}
		average /= sub.size();
		return average;
	}

    /** Retrieves the items with the given tracking numbers.
        @param tracking the tracking numbers of the items to retrieve
        @return the items with the given tracking numbers
    */
	public HashSet<Item> getItems(long... tracking) {
		HashSet<Item> mySet = new HashSet<Cargo.Item>();

		Iterator<Cargo.Item> Iterator = cargo.iterator();

		if (tracking == null) {
			return null;
		}
		// run though items, add ones with match tracking numbers to a hashset, return said hashset after.
		while (Iterator.hasNext()) {
			Item myItem = Iterator.next();
			for (int i = 0; i < tracking.length; i++)
				if (tracking[i] == myItem.tracking) {
					mySet.add(myItem);
				}
		}
		return mySet;
	}

    /** Retrieves the items with the given names.
        @param names the names of the items to retrieve
        @return a map from names to sets of items with that name. Names that have no items will get an empty set
    */
	public HashMap<String, HashSet<Item>> getItemsMap(String... names) {
		if (names == null) {
			return null;
		}
		//create hashmap with of type string and hashset. 
		HashMap<String, HashSet<Item>> myHashMap = new HashMap();
		//loop thought names array, add current name, and return value of getItems with current name as param.
		for (int i = 0; i < names.length; i++) {
			myHashMap.put((names[i]), (getItems(names[i])));
		}
		return myHashMap;
	}

    /** An item in the cargo module. Items are immutable.
    */
    public static class Item {
        public final long tracking;
        public final String name;
        public final int weight;

        private static long nextTrack = 101;

        /** An item for transport.
            @param aName the name of the item
            @param aWeight the weight of the item
            */
        public Item(String aName, int aWeight) {
            tracking = nextTrack++;
            name = aName;
            weight = aWeight;
        }
    }
}
