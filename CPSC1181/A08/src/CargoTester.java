//
// javac Cargo*.java ; java -ea CargoTester
//
// javadoc Cargo.java -link "http://docs.oracle.com/javase/8/docs/api/"
//

import java.util.*;

public class CargoTester {

    public static void main(String[] args) {
		new TestRunner(CargoTester.class).run();
    }

    private static void testMakeItem() {
        Cargo.Item item;

        item = new Cargo.Item("Item1", 10);
        assert "Item1".equals(item.getName());
        assert 10 == item.getWeight();
		assert 100 < item.getTracking();
        final long nextItem = item.getTracking() + 1;

        item = new Cargo.Item("Item2", 20);
        assert "Item2".equals(item.getName());
        assert 20 == item.getWeight();
        assert nextItem == item.getTracking();
    }

    private static void testMakeRobot() {
        Cargo cb;

        cb = new Cargo(0);
        assert 0 == cb.getMaxWeight();
        assert 0 == cb.getItemCount();
        assert 0 == cb.getTotalWeight();
        assert !cb.isOverWeight();
        assert !cb.contains(101);

        cb = new Cargo(100);
        assert 100 == cb.getMaxWeight();
        assert 0 == cb.getItemCount();
        assert 0 == cb.getTotalWeight();
        assert !cb.isOverWeight();
        assert !cb.contains(101);
    }

    private static void testAddItems() {
        Cargo cb;
        Cargo.Item item;

        cb = new Cargo(0); // special
        item = new Cargo.Item("", 1);
        cb.add(item);
        assert 1 == cb.getItemCount();
        assert cb.isOverWeight();
        assert 1 == cb.getTotalWeight();
        assert cb.contains(item.getTracking());

        cb = new Cargo(4);
        item = new Cargo.Item("", 0);    // special
        cb.add(item);
        assert 1 == cb.getItemCount();
        assert !cb.isOverWeight();
        assert 0 == cb.getTotalWeight();
        assert cb.contains(item.getTracking());
        item = new Cargo.Item("", 4);    // boundary, not overweight
        cb.add(item);
        assert 2 == cb.getItemCount();
        assert !cb.isOverWeight();
        assert 4 == cb.getTotalWeight();
        assert cb.contains(item.getTracking());
        item = new Cargo.Item("", 1); // boundary, overweight
        cb.add(item);
        assert 3 == cb.getItemCount();
        assert cb.isOverWeight();
        assert 5 == cb.getTotalWeight();
        assert cb.contains(item.getTracking());
    }

    private static void testRemove() {
        Cargo cb;
        Cargo.Item item;

        // special, no items
        cb = new Cargo(10);
        assert false == cb.remove(101);

        // typical 1 item
        item = new Cargo.Item("", 20);
        cb.add(item);
        assert cb.remove(item.getTracking());
        assert 0 == cb.getItemCount();
        assert !cb.isOverWeight();
        assert 0 == cb.getTotalWeight();
        assert !cb.contains(item.getTracking());
        assert !cb.remove(item.getTracking());

        Cargo.Item[] items = new Cargo.Item[] {
            new Cargo.Item("", 4), new Cargo.Item("", 5), new Cargo.Item("", 6) };
        for(Cargo.Item anItem : items) {
            cb.add(anItem);
        }
        assert 3 == cb.getItemCount();
        assert cb.isOverWeight();
        assert cb.remove(items[1].getTracking());    // typical, remove 1 item
        assert 2 == cb.getItemCount();
        assert !cb.isOverWeight();
        assert cb.contains(items[0].getTracking());
        assert !cb.contains(items[1].getTracking());
        assert cb.contains(items[2].getTracking());
        // typical, remove what's left
        assert cb.remove(items[0].getTracking());
        assert !cb.remove(items[1].getTracking());
        assert cb.remove(items[2].getTracking());
        // check
        assert 0 == cb.getItemCount();
        assert 0 == cb.getTotalWeight();
        assert !cb.isOverWeight();
    }

    private static void testGetItems_name() {
        Cargo cb;
        Set<Cargo.Item> sub;

        cb = new Cargo(10);
        // special
		sub = cb.getItems((String) null);
		assert sub.isEmpty();
        sub = cb.getItems("");
        assert sub.isEmpty();

        cb.add(new Cargo.Item("a", 0));
        cb.add(new Cargo.Item("b", 0));
        cb.add(new Cargo.Item("a", 0));

        // typical
        sub = cb.getItems("a");
        assert 2 == sub.size();
        sub = cb.getItems("b");
        assert 1 == sub.size();
        sub = cb.getItems("c");
        assert 0 == sub.size();
    }

    private static void testGetHeaviest_Average() {
        Cargo cb;
        Cargo.Item[] items = new Cargo.Item[] {
            new Cargo.Item("", 0), new Cargo.Item("", 2), new Cargo.Item("", 4)
        };

        cb = new Cargo(0);
        // special, no items
        assert null == cb.getHeaviest();
        assert Double.isNaN(cb.getAverageWeight());
        // boundary 1 item, special 0 weight
        cb.add(items[0]);
 
        assert 0 == cb.getAverageWeight();

        // typical first
        cb = new Cargo(0);
        cb.add(items[2]);
        cb.add(items[0]);
        cb.add(items[1]);
        assert items[2] == cb.getHeaviest();
        assert 2 == cb.getAverageWeight();

        // typical middle
        cb = new Cargo(0);
        cb.add(items[1]);
        cb.add(items[2]);
        cb.add(items[0]);
        assert items[2] == cb.getHeaviest();
        assert 2 == cb.getAverageWeight();

        // typical last
        cb = new Cargo(0);
        cb.add(items[0]);
        cb.add(items[1]);
        cb.add(items[2]);
        assert items[2] == cb.getHeaviest();
        assert 2 == cb.getAverageWeight();

        // typical, after remove
        cb.remove(items[2].getTracking());
        assert items[1] == cb.getHeaviest();
        assert 1 == cb.getAverageWeight();
    }

    private static void testGetHeaviest_Average_name() {
        Cargo cb;
        Cargo.Item[] items = new Cargo.Item[] {
            new Cargo.Item("a", 1),
            new Cargo.Item("b", 1),
            new Cargo.Item("a", 10),
            new Cargo.Item("a", 2)
        };

        cb = new Cargo(0);
        for(Cargo.Item i : items) {
            cb.add(i);
        }
        assert items[2] == cb.getHeaviest("a");
        assert items[1] == cb.getHeaviest("b");
        assert null == cb.getHeaviest("c");
        assert (1+10+2)/3.0 == cb.getAverageWeight("a");
        assert 1 == cb.getAverageWeight("b");
        assert Double.isNaN(cb.getAverageWeight("c"));
    }

    private static void testGetItems_tracking() {
        Cargo cb = new Cargo(10);
        Cargo.Item[] items = new Cargo.Item[] {
            new Cargo.Item("", 0),
            new Cargo.Item("", 1),
            new Cargo.Item("", 2),
            new Cargo.Item("", 3),
        };
        for(Cargo.Item i : items) {
            cb.add(i);
        }

        Set<Cargo.Item> sub;
        // boundary, at ends
        sub = cb.getItems(items[0].getTracking(), items[3].getTracking());
        assert 2 == sub.size();
        assert sub.contains(items[0]);
        assert sub.contains(items[3]);

        // typical, in middle
        sub = cb.getItems(items[1].getTracking(), items[2].getTracking());
        assert 2 == sub.size();
        assert sub.contains(items[1]);
        assert sub.contains(items[2]);

        // typical/special, not there
        sub = cb.getItems(0);
        assert sub.isEmpty();

        // special
        sub = cb.getItems(new long[] {});
        assert sub.isEmpty();

        // special
        sub = cb.getItems((long[]) null);
        assert null == sub;
    }

    private static void testGetItemsMap() {
        Cargo cb = new Cargo(10);
        Cargo.Item[] items = new Cargo.Item[] {
            new Cargo.Item("a", 0),
            new Cargo.Item("b", 1),
            new Cargo.Item("b", 2),
            new Cargo.Item("a", 3),
        };
        for(Cargo.Item i : items) {
            cb.add(i);
        }

        Map<String, List<Cargo.Item>> map;
        List<Cargo.Item> sub;

        // special
        map = cb.getItemsMap((String[]) null);
        assert null == map;

        // special
        map = cb.getItemsMap(new String[] {});
        assert map.isEmpty();

        // typical, not there
        map = cb.getItemsMap("c");
		if(0 == map.size()) {	// either way
			assert true;
		} else if(1 == map.size()) {
			assert map.containsKey("c");
			assert null == map.get("c") || map.get("c").isEmpty();
		} else {
			assert false;
		}

        // typical, 1 set, there
        map = cb.getItemsMap("a");
        assert 1 == map.size();
        sub = map.get("a");
        assert 2 == sub.size();
        assert sub.contains(items[0]);
        assert sub.contains(items[3]);

        // typical, 1 set, there
        map = cb.getItemsMap("b");
        assert 1 == map.size();
        sub = map.get("b");
        assert 2 == sub.size();
        assert sub.contains(items[1]);
        assert sub.contains(items[2]);

        // typical, multi sets, there
        map = cb.getItemsMap("a", "b");
        assert 2 == map.size();
        sub = map.get("a");
        assert 2 == sub.size();
        assert sub.contains(items[0]);
        assert sub.contains(items[3]);
        sub = map.get("b");
        assert 2 == sub.size();
        assert sub.contains(items[1]);
        assert sub.contains(items[2]);

        // typical, 1 there, 1 not
        map = cb.getItemsMap("a", "c");
		if (map.size() == 1) {		// either way
			assert 1 == map.size();
	        assert map.containsKey("a");
	        assert 2 == map.get("a").size();
	        assert !map.containsKey("b");
	        assert !map.containsKey("c");
		} else if(map.size() == 2) {
			assert 2 == map.size();
	        assert map.containsKey("a");
	        assert 2 == map.get("a").size();
	        assert !map.containsKey("b");
	        assert map.containsKey("c");
	        assert null == map.get("c") || map.get("c").isEmpty();
		} else {
			assert false;
		}
    }

    static {
        boolean assertsEnabled = false;
        assert assertsEnabled = true; // Intentional side effect!!!
        if (!assertsEnabled) {
            throw new RuntimeException("Asserts must be enabled!!! java -ea");
        }
    }
}
