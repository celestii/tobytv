package tobytv._001_002;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.core.ParameterizedTypeReference;

public class SpringTypeReference {
	
	public static void main(String[] args) {
		ParameterizedTypeReference<?> typeRef = new ParameterizedTypeReference<List<Map<Set<Integer>, String>>>()	{};
		System.out.println(typeRef.getType());
	}

}
