package tobytv._006;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class DelegateSub<T> implements Subscriber<T> {
	Subscriber sub;

	public DelegateSub(Subscriber<? super T> sub) {
		this.sub = sub;
	}

	@Override
	public void onComplete() {
		sub.onComplete();
	}

	@Override
	public void onError(Throwable e) {
		sub.onError(e);
	}

	@Override
	public void onSubscribe(Subscription s) {
		sub.onSubscribe(s);
	}

	@Override
	public void onNext(T i) {
		sub.onNext(i);
	}
}
