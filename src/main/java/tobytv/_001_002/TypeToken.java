package tobytv._001_002;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.ResolvableType;

public class TypeToken {
	/*
	 * static class TypesafeMap { Map<Class<?>, Object> map = new HashMap<>();
	 * 
	 * <T> void put(Class<T> clazz, T value) { map.put(clazz, value); }
	 * 
	 * <T> T get(Class<T> clazz) { return clazz.cast(map.get(clazz)); } }
	 */

	static class TypesafeMap {
		Map<Type, Object> map = new HashMap<>();

		<T> void put(TypeReference<T> tr, T value) {
			map.put(tr.type, value);
		}

		<T> T get(TypeReference<T> tr) {
			if(tr.type instanceof Class<?>)
				return ((Class<T>) tr.type).cast(map.get(tr.type));
			else	
				return ((Class<T>)((ParameterizedType)tr.type).getRawType()).cast(map.get(tr.type));		//TR<List<String>>
		}
	}

	static class TypeReference<T> { // Sup
		Type type;

		public TypeReference() {
			Type stype = getClass().getGenericSuperclass();
			if (stype instanceof ParameterizedType) {
				this.type = ((ParameterizedType) stype).getActualTypeArguments()[0];
			} else {
				throw new RuntimeException();
			}
		}
	}

	public static void main(String[] args) {
		TypesafeMap m = new TypesafeMap();
		/*
		 * m.put(String.class, "String"); m.put(Integer.class, 1);
		 * m.put(List.class, Arrays.asList(1,2,3)); //List<Integer>
		 * m.put(List.class, Arrays.asList("a", "b", "c")); //List<String>
		 * 
		 * System.out.println(m.get(Integer.class));
		 * System.out.println(m.get(String.class));
		 * System.out.println(m.get(List.class));
		 */
		m.put(new TypeReference<Integer>()	{}, 1);
		m.put(new TypeReference<String>() {}, "String");
		m.put(new TypeReference<List<Integer>>() {}, Arrays.asList(1, 2, 3));
		m.put(new TypeReference<List<String>>() {}, Arrays.asList("a", "b", "c"));
		m.put(new TypeReference<List<List<String>>>() {}, Arrays.asList(Arrays.asList("a", "b"), Arrays.asList("b", "c"), Arrays.asList("c")));
		Map<String, String> map = new HashMap<>();
		map.put("key1", "value1");
		map.put("key2", "value2");
		m.put(new TypeReference<Map<String, String>>() {}, map);

		System.out.println(m.get(new TypeReference<Integer>()	{}));
		System.out.println(m.get(new TypeReference<String>() {}));
		System.out.println(m.get(new TypeReference<List<Integer>>() {}));
		System.out.println(m.get(new TypeReference<List<String>>() {}));
		System.out.println(m.get(new TypeReference<List<List<String>>>() {}));
		System.out.println(m.get(new TypeReference<Map<String, String>>() {}));

		TypeReference t = new TypeReference<String>() {};
		System.out.println(t.type);
		
		//ResolvableType rt = ResolvableType.forClass(TypeReference.class);
		ResolvableType rt = ResolvableType.forInstance(new TypeReference<List<String>>() {});
		System.out.println(rt.getSuperType().getGenerics().length);
		System.out.println(rt.getSuperType().getGeneric(0).getType());
		System.out.println(rt.getSuperType().getGeneric(0).getNested(2).getType());
		
		System.out.println(rt.getSuperType().hasUnresolvableGenerics());
		System.out.println(ResolvableType.forInstance(new ArrayList<String>()).hasUnresolvableGenerics());
	}

}
