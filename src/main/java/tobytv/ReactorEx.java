package tobytv;

import reactor.core.publisher.Flux;

public class ReactorEx {
	public static void main(String[] args) {
		Flux.<Integer>create(e -> {
			e.next(1);
			e.next(2);
			e.next(3);
			e.complete();
		})
		.log("____1")
		.map(s -> s * 10)
		.reduce(0, (a, b) -> a+ b)
		.log("____2")
		.subscribe(System.out::println);
	}
}