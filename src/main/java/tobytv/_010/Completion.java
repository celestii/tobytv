package tobytv._010;

import java.util.function.Consumer;
import java.util.function.Function;

import org.springframework.util.concurrent.ListenableFuture;

public class Completion<S, T>	{
	Completion<T, ?> next;
	
	public void andAccept(Consumer<T> con) {
		Completion<T, Void> c = new AcceptionCompletion<>(con);
		this.next = c;
	}
	
	public Completion<T, T> andError(Consumer<Throwable> econ) {
		Completion<T, T> c = new ErrorCompletion<>(econ);
		this.next = c;
		return c;
	}
	
	public <V> Completion<T, V> andApply(Function<T, ListenableFuture<V>> fn) {
		Completion<T, V> c = new AsyncCompletion<>(fn);
		this.next = c;
		return c;
	}
	
	public static <S, T> Completion<S, T> from(ListenableFuture<T> lf) {
		Completion<S, T> c = new Completion<>();
		lf.addCallback(s -> {
			c.complete(s);
		}, e -> {
			c.error(e);
		});
		return c;
	}

	void error(Throwable e) {
		if(next != null)	next.error(e);
	}

	void complete(T s) {
		if(next != null)	next.run(s);
	}

	void run(S value) {
	}
}
