package tobytv._010;

import java.util.function.Consumer;

public class ErrorCompletion<T> extends Completion<T, T> {
	Consumer<Throwable> econ;
	
	public ErrorCompletion(Consumer<Throwable> econ) {
		this.econ = econ;
	}
	
	@Override
	void run(T value) {
		if(next != null) next.run(value);
	}
	
	@Override
	void error(Throwable e) {
		super.error(e);
	}
}
