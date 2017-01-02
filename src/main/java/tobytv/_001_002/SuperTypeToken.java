package tobytv._001_002;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class SuperTypeToken {
	static class Sup<T>	{
		T value;
	}
	
	//NESTED STATIC CLASS = INNER CLASS???
	/*static class Sub extends Sup<List<String>>	{
		
	}*/
	
	public static void main(String[] args) throws NoSuchFieldException	{
		//LOCAL CLASS
		/*class Sub extends Sup<List<String>>	{
			
		}*/
		
		//ANONYMOUS CLASS
		//Sup b = new Sup<List<String>>()		{};
		
		//Sub b = new Sub();
		//Type t = b.getClass().getGenericSuperclass();
		Type t = (new Sup<List<String>>()	{}).getClass().getGenericSuperclass();
		ParameterizedType ptype = (ParameterizedType) t;
		System.out.println(ptype.getActualTypeArguments()[0]);
	}

}
