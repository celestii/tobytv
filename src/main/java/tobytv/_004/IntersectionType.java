package tobytv._004;

import java.util.function.Consumer;
import java.util.function.Function;

/*
 * 추론은 컴파일타임에 결정된다
 */
public class IntersectionType	{
	/*private static void hello1(Function o)	{
		
	}
	
	private static <T extends Function & Serializable & Cloneable> void hello2(T o)	{
		
	}
	// marker interface
	// Serializable, Cloneable, ...
	 */
	
	interface Hello	{
		default void hello()	{
			System.out.println("Hello");
		}
	}
	
	interface Hi	{
		default void hi()	{
			System.out.println("hi");
		}
	}
	
	interface Printer	{
		default void print(String str)	{
			System.out.println(str);
		}
	}
	
	private static <T extends Function & Hello & Hi> void hello(T t) {
		t.hello();
		t.hi();
	}
	
	private static <T extends Function>void run(T t, Consumer<T> consumer) {
		consumer.accept(t);
	}

	public static void main(String[] args) {
		/*hello1((Function & Serializable & Cloneable)s -> s);
		hello2((Function & Serializable & Cloneable)s -> s);*/
		
		hello((Function & Hello & Hi) s -> s);
		
		run((Function & Hello & Hi & Printer) s -> s, o -> {
			o.hello();
			o.hi();
			o.print("Lambda");
		});
	}
	
}
