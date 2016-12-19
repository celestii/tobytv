package tobytv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Generic의 T type 을 활용할 경우 Generic을 사용하고 아니라면 wildcard를 사용하는 것이 java 사상적으로 옳바르다
 */
public class GenericTest {
	/*static <T> void method1(List<T> list)	{}
	
	static void method2(List<?> list)	{}*/
	
	/*static <T> boolean isEmpty1(List<T> list)	{
		return list.size() == 0;
	}
	
	static boolean isEmpty2(List<?> list)	{
		return list.size() == 0;
	}*/
	
	/*static <T> long frequency1(List<T> list, T elem)	{
		return list.stream().filter(s -> s.equals(elem)).count();
	}
	
	static long frequency2(List<?> list, Object elem)	{
		return list.stream().filter(s -> s.equals(elem)).count();
	}*/
	
	/*private static <T extends Comparable<T>> T max1(List<T> list)	{
		return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
	}
	
	private static <T extends Comparable<? super T>> T max2(List<? extends T> list)	{
		return list.stream().reduce((a, b) -> a.compareTo(b) > 0 ? a : b).get();
	}*/
	
	/*static <T> void reverse1(List<T> list)	{
		List<T> temp = new ArrayList<>(list);
		for(int i = 0 ; i < list.size() ; i++)	{
			list.set(i, temp.get(list.size() - i -1));
		}
	}
	
	static void reverse2(List<?> list)	{	// capture error
		List<?> temp = new ArrayList<>(list);
		for(int i = 0 ; i < list.size() ; i++)	{
			list.set(i, temp.get(list.size() - i -1));
		}
	}
	
	static void reverse2(List<?> list)	{
		reverseHelper(list);
	}
	
	private static <T> void reverseHelper(List<T> list)	{
		List<T> temp = new ArrayList<>(list);
		for(int i = 0 ; i < list.size() ; i++)	{
			list.set(i, temp.get(list.size() - i -1));
		}
	}
	
	//raw type
	static void reverse(List<?> list)	{
		List temp = new ArrayList(list);
		List list2 = list;
		for(int i = 0 ; i < list.size() ; i++)	{
			list2.set(i, temp.get(temp.size() - i -1));
		}
	}*/
	
	
	public static void main(String[] args) {
		/*List<Integer> ints = Arrays.asList(1,2,3,4,5);
		method1(ints);
		method2(ints);*/
		
		/*List<Integer> ints = Arrays.asList(1,2,3,4,5);
		System.out.println(isEmpty1(ints));
		System.out.println(isEmpty2(ints));*/
		
		/*List<Integer> list = Arrays.asList(1,2,3,4,5,3,2);
		System.out.println(frequency1(list, 3));
		System.out.println(frequency2(list, 1));*/
		
		/*List<Integer> list = Arrays.asList(1,2,3,4,5,3,2);
		System.out.println(max1(list));
		System.out.println(max2(list));
		
		System.out.println(Collections.max(list, (a, b) -> a - b));
		System.out.println(Collections. max(list, (Comparator<Object>)(a, b) -> a.toString().compareTo(b.toString())));
		System.out.println(Collections.<Number>max(list, (Comparator<Object>)(a, b) -> a.toString().compareTo(b.toString())));*/
		
		/*List<Integer> list = Arrays.asList(1,2,3,4,5);
		reverse1(list);
		System.out.println(list);
		reverse2(list);
		System.out.println(list);
		reverse(list);
		System.out.println(list);*/
	}
}
