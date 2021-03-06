package telran.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ListTest<T> {
	static int numForCheck;
	static String strForCheck;
	private static final int N_NUMBERS_PERFORMANCE = 100;
	private static final String BASE_PACKAGE = "telran.util.";
	private List<Integer> numbers;
	private List<String> strings;
	Integer initialNumbers[] = { 10, 20, 40 };
	String initialStrings[] = { "name1", "name2" };
	@BeforeEach
	
	void setUp() throws Exception {
		numbers = getInitialNumbers();
		strings = getInitialStrings();
	}
	
	private List<String> getInitialStrings() throws Exception {
		//TODO DONE
		String className = getClassName();
		List<String> res  =  (List<String>) Class.forName(className).getConstructor().newInstance();//List<String> res = new LinkedList<>();
		for (int i = 0; i < initialStrings.length; i++) {
			res.add(initialStrings[i]);
		}
		return res;
	}

	private String getClassName() throws Exception {
		//TODO DONE

		File file = new File ("ContainerType.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		return BASE_PACKAGE + br.readLine();
	}

	private List<Integer> getInitialNumbers() throws Exception {
		//TODO DONE
		String className = getClassName();
		List<Integer> res  =  (List<Integer>) Class.forName(className).getConstructor().newInstance();
		for (int num : initialNumbers) {
			res.add(num);
		}
		return res;
	}

	@Test
	void clearListTest() {
		Integer exp[] = {};
		numbers.clear();
		assertEquals(0, numbers.size());
		assertNull(numbers.get(0));
		assertArrayEquals(exp, getArrayFromList(numbers));
		numbers.clear();
	}

	@Test
	void sortedSearchExist() {
		assertEquals(0, numbers.sortedSearch(10));
		assertEquals(1, numbers.sortedSearch(20));
		assertEquals(2, numbers.sortedSearch(40));
	}

	@Test
	void sortedSearchNotExist() {
		assertEquals(-1, numbers.sortedSearch(5));
		assertEquals(-2, numbers.sortedSearch(15));
		assertEquals(-3, numbers.sortedSearch(25));
		assertEquals(-4, numbers.sortedSearch(45));

	}

	@Test
	void testGet() {
		assertEquals(10, numbers.get(0));
		assertEquals("name1", strings.get(0));
		assertNull(numbers.get(-1));
		assertNull(numbers.get(3));

	}

	@Test
	void testAddAtIndex() {
		int inserted0 = 100;
		int inserted2 = -8;
		int inserted4 = 1000;
		Integer[] expected = { inserted0, 10, inserted2, 20, 40, inserted4 };
		assertTrue(numbers.add(0, inserted0));
		assertTrue(numbers.add(2, inserted2));
		assertTrue(numbers.add(5, inserted4));
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.add(7, 1000));
		assertFalse(numbers.add(-1, 1000));
	}

	@Test
	void testRemove() {
		numbers.add(2, 6);
		assertEquals(6, numbers.remove(2));
		Integer expected0[] = { 20, 40 };
		Integer expected1[] = { 20 };
		assertNull(numbers.remove(3));
		assertNull(numbers.remove(-1));
		assertEquals(10, numbers.remove(0));
		assertArrayEquals(expected0, getArrayFromList(numbers));
		assertEquals(40, numbers.remove(1));
		assertArrayEquals(expected1, getArrayFromList(numbers));
	}

	@Test
	void testSize() {
		assertEquals(initialNumbers.length, numbers.size());
		numbers.add(100);
		assertEquals(initialNumbers.length + 1, numbers.size());
		numbers.remove(0);
		assertEquals(initialNumbers.length, numbers.size());
	}

	@Test
	void testContainsNumbers() {
		assertTrue(numbers.contains(initialNumbers[0]));
		assertFalse(numbers.contains(1000));
		numbers.add(1000);
		assertTrue(numbers.contains(1000));

	}

	@Test
	void testContainsStrings() {

		strings.add("Hello");
		String pattern = new String("Hello");
		assertTrue(strings.contains(pattern));
		assertTrue(strings.contains("Hello"));
	}

	@Test
	void testContainsPersons() {
		Person prs = new Person(123, "Moshe");
		Person prs2 = new Person(124, "Vasya");
//		List<Person> persons = new ArrayList<>();
		List<Person> persons = new LinkedList<>();
		persons.add(prs);
		persons.add(prs2);
		assertTrue(persons.contains(new Person(124, "Vasya")));
		assertTrue(persons.contains(prs));
		assertFalse(persons.contains(new Person(125, "Olya")));
	}

	@Test
	void containsPredicateNumbersTest() {
	// DONE
		numForCheck = 100;
		Predicate<Integer> checkContainsNum = (el -> el > numForCheck);
		assertFalse(numbers.contains(checkContainsNum));
		numForCheck = 25;
		assertTrue(numbers.contains(checkContainsNum));

	}

	@Test
	// TODO
	// Done
	void containsPredicateStringsTest() {
		Predicate<String> checkContainsStr =(str -> str.contains(strForCheck));
		strForCheck = "main";
		assertFalse(strings.contains(checkContainsStr));
		strForCheck = "name";
		assertTrue(strings.contains(checkContainsStr));

	}

	@SuppressWarnings("unchecked")
	private <T> T[] getArrayFromList(List<T> list) {
		int size = list.size();
		T[] res = (T[]) new Object[size];
		int resIndex = 0;
		for (T obj : list) {
			res[resIndex++] = obj;
		}

		return res;
	}

	@Test
	void indexOfTest() {
		assertEquals(0, numbers.indexOf(10));
		assertEquals(2, numbers.indexOf(40));
		assertEquals(-1, numbers.indexOf(100));
	}

	@Test
	void lastIndexOfTest() {
		assertEquals(0, numbers.lastIndexOf(10));
		assertEquals(2, numbers.lastIndexOf(40));
		assertEquals(-1, numbers.lastIndexOf(100));
		numbers.add(10);
		assertEquals(3, numbers.lastIndexOf(10));

	}

	@Test
	void indexOfPredicate() {
		// TODO
		// DONE
		Predicate<Integer> checkIndexOfNum = (el -> el > numForCheck);
		numForCheck = 25;
		assertEquals(2, numbers.indexOf(checkIndexOfNum));
		numForCheck = 5;
		assertEquals(0, numbers.indexOf(checkIndexOfNum));
		numForCheck = 45;
		assertEquals(-1, numbers.indexOf(checkIndexOfNum));
	}

	@Test
	void lastIndexOfPredicate() {
		//TODO
		//DONE
		Predicate<Integer> checkLastIndexOfNum = (el -> el > numForCheck);
		numForCheck = 25;
		assertEquals(2, numbers.lastIndexOf(checkLastIndexOfNum));
		numForCheck = 5;
		assertEquals(2, numbers.lastIndexOf(checkLastIndexOfNum));
		numForCheck = 45;
		assertEquals(-1, numbers.lastIndexOf(checkLastIndexOfNum));
	}

	@Test
	void removeIfTest() {
		//TODO
		//Done
		Integer expected[] = { 10, 20 };
		Integer expectedEmpty[] = {};
		Predicate<Integer> checkRemuveIfPredicateNum = (el -> el > numForCheck);
		numForCheck = 25; 
		assertTrue(numbers.removeIf(checkRemuveIfPredicateNum));
		assertFalse(numbers.removeIf(checkRemuveIfPredicateNum));
		assertArrayEquals(expected, getArrayFromList(numbers));
		numForCheck = 0;
		assertTrue(numbers.removeIf(checkRemuveIfPredicateNum));
		assertArrayEquals(expectedEmpty, getArrayFromList(numbers));

	}

	@Test
	void removeAllTest() {
		numbers.add(20);
		List<Integer> otherNumbers = new ArrayList<>();
		otherNumbers.add(20);
		otherNumbers.add(40);
		assertTrue(numbers.removeAll(otherNumbers));
		Integer expected[] = { 10 };
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.removeAll(otherNumbers));
	}

	@Test
	void removeAllSame() {
		assertTrue(numbers.removeAll(numbers));
		assertArrayEquals(new Integer[0], getArrayFromList(numbers));
	}

	@Test
	void retainAllTest() {
		numbers.add(20);
		List<Integer> otherNumbers = new ArrayList<>();
		otherNumbers.add(20);
		otherNumbers.add(40);
		assertTrue(numbers.retainAll(otherNumbers));
		Integer expected[] = { 20, 40, 20 };
		assertArrayEquals(expected, getArrayFromList(numbers));
		assertFalse(numbers.retainAll(otherNumbers));
	}

	@Test
	void retainAllSame() {
		assertFalse(numbers.retainAll(numbers));
		assertArrayEquals(initialNumbers, getArrayFromList(numbers));
	}

	@Test
	void removeObjectTest() {
		Integer expected0[] = { 20, 40 };
		Integer expected1[] = { 20 };
		assertNull(numbers.remove((Integer) 25));
		assertEquals(10, numbers.remove((Integer) 10));
		assertArrayEquals(expected0, getArrayFromList(numbers));
		assertEquals(40, numbers.remove((Integer) 40));
		assertArrayEquals(expected1, getArrayFromList(numbers));
	}

	@Test
	void sortNaturalTest() {
		numbers.add(40);
		numbers.add(10);
		numbers.add(20);
		Integer expected[] = { 10, 10, 20, 20, 40, 40 };
		numbers.sort();
		assertArrayEquals(expected, getArrayFromList(numbers));
	}

	@Test
	void sortComparatorTest() {
		//TODO
		//Done!
		Integer expectedReverse[] = { 40, 20, 10 };
		Integer expectedProximity23[] = { 20, 10, 40 }; // sorted per proximity to 23
		Comparator<Integer> compNatural = Comparator.naturalOrder();
		numbers.sort(compNatural.reversed());
		assertArrayEquals(expectedReverse, getArrayFromList(numbers));
		numForCheck = 23;
		numbers.sort((a,b) -> Math.abs(numForCheck-a)-Math.abs(numForCheck-b));
		assertArrayEquals(expectedProximity23, getArrayFromList(numbers));
	}

	@Test
	void removeIfPerformanceTest() {
		
		List<Integer> list = new LinkedList<>();
		//List<Integer> list = new ArrayList<>();
		fillListPerformance(list);
		Predicate<Integer> divider4 = (el -> el % numForCheck ==0);
		numForCheck = 4;
		 list.removeIf(divider4);
		 assertEquals(-1, list.indexOf(divider4));

	}

	@Test
	void RemoveByIteratorTest() {
		Iterator<Integer> numbIterator = numbers.iterator();
		while (numbIterator.hasNext()) {
			Integer el = numbIterator.next();
			if (el.equals(20)) {
				numbIterator.remove();
			}
		}
		Integer exp[] = { 10, 40 };
		assertArrayEquals(exp, getArrayFromList(numbers));
		numbers.add(30);
		numbers.add(40);
		Iterator<Integer> numbIterator2 = numbers.iterator();
		Integer exp2[] = { 10, 30 };
		while (numbIterator2.hasNext()) {
			Integer el = numbIterator2.next();
			if (el.equals(40)) {
				numbIterator2.remove();
			}
		}
		assertArrayEquals(exp2, getArrayFromList(numbers));
	}

	@Test
	void removeAllIteratorTest() {
		Iterator<Integer> numbIterator = numbers.iterator();
		while (numbIterator.hasNext()) {
			System.out.println(numbIterator.next());
			numbIterator.remove();
		}
		assertArrayEquals(new Integer[0], getArrayFromList(numbers));
	}

	private void fillListPerformance(List<Integer> list) {
		for (int i = 0; i < N_NUMBERS_PERFORMANCE; i++) {
			list.add((int) (Math.random() * Integer.MAX_VALUE));
		}
	}
	@Test
	void testNextException() {
		Iterator<Integer> it = numbers.iterator();
		while(it.hasNext()) {
			it.next();
			}
		try {
			it.next();
			fail("There shoud be thrown exception");
		} catch (NoSuchElementException e) {
		
		} catch (Exception e) {
			fail("There shoud be thrown NoSuchElementException");
		}
	}
	
	@Test
	void testREmoveNoNext(){
		Iterator<Integer> it = numbers.iterator();
		it.next();
		it.next();
		it.remove();
		exceptionRemoveTest(it);
		it = numbers.iterator();
		exceptionRemoveTest(it);
	}

	private void exceptionRemoveTest(Iterator<Integer> it) {
		try {
			it.remove();
			fail("there should be thrown Exception");
		} catch (IllegalStateException e) {
			
		} catch (Exception e) {
			fail("there should be thrown illegalStateException ");
		}		
	}




	
}