package tobytv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Generic/*<T extends List>*/ {
	/*static class Hello<T>	{	// T -> type parameter
		T t;
		T method(T val)	{
			return null;
		}
	}*/
	
	/*<T> void print(T t)	{
		System.out.println(t.toString());
	}*/
	
	/*static void print(String value)	{
		System.out.println(value);
	}*/
	
	//static <T extends List & Serializable & Comparable & Closeable> void print(T t) 	{}
	
	/*static <T extends Comparable<T>>long countGreaterThan(T[] arr, T elem)	{
		return Arrays.stream(arr).filter(s -> s.compareTo(elem) > 0).count();
	}*/
	
	/*static <T> void method1(List<T> list)	{
		
	}
	
	static void method2(List<?> list)	{
		
	}*/
	
	static void printList1(List<Object> list)	{
		list.forEach(System.out::println);
	}
	
	static void printList2(List<?> list)	{
		list.forEach(System.out::println);
	}
	
	static class A	{}
	static class B extends A	{}
	
	public static void main(String[] args) {
		//new Hello<String>();	//type argument
		
		/*new Generic().print("Hello");
		new Generic().print(1);*/
		
		/*Integer[] arr = new Integer[]{1,2,3,4,5,6,7};
		System.out.println(countGreaterThan(arr, 3));
		
		String[] arr1 = new String[]{"a","b","c","d","e","f"};
		System.out.println(countGreaterThan(arr1, "e"));*/
		
		/*Integer i = 10;
		Number n= i;
		
		List<Integer> intList = new ArrayList<>();
		//List<Number> numberList = intList;		// 1.compile error
		
		ArrayList<Integer> arrList = new ArrayList<>();
		List<Integer> intList2 = arrList;*/
		
		//List<String> str = Collections.<String>emptyList();
		
		//List<?> list; // ? : wildcards
		//List<T> list; // T : type이 정해지면 해당 type으로 사용하겠다.
		
		List<Integer> list = Arrays.asList(1,2,3);
		printList1(Arrays.asList(1,2,3));
		printList2(Arrays.asList(1,2,3));
		//printList1(list);	// compile error
		printList2(list);
		
		List<B> listB = new ArrayList<>();
		//List<A> listA= listB;	// compile error
		List<? extends A> la = listB;
		List<? super B> lb = listB;
		//la.add(new B());	// compile error	wildcard의 제한성 null 만 가능
	}
	
}
