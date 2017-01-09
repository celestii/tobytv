package tobytv._010;

import java.util.function.Function;

import org.springframework.util.concurrent.ListenableFuture;

public class AsyncCompletion<S, T> extends Completion<S, T> {
	Function<S, ListenableFuture<T>> fn;
	
	public AsyncCompletion(Function<S, ListenableFuture<T>> fn)	{
		this.fn = fn;
	}
	
	@Override
	void run(S value) {
		if(fn != null)	{
			ListenableFuture<T> lf = fn.apply(value);
			lf.addCallback(s -> complete(s), e -> error(e));
		}
	}
}
