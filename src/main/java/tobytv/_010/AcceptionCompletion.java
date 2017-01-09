package tobytv._010;

import java.util.function.Consumer;

public class AcceptionCompletion<S> extends Completion<S, Void> {
	Consumer<S> con;
	
	public AcceptionCompletion(Consumer<S> con) {
		this.con = con;
	}
	
	@Override
	void run(S value) {
		con.accept(value);
	}
}
