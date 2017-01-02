package tobytv._007;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class IntervalEx {
	public static void main(String[] args) {
		Publisher<Integer> pub = sub -> {
			sub.onSubscribe(new Subscription() {
				int no = 0;
				boolean cancelled = false;
				
				@Override
				public void request(long n) {
					ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
					exec.scheduleAtFixedRate(() -> {
						if(cancelled)	{
							exec.shutdown();
							return;
						}
						sub.onNext(no++);
					}, 0, 500, TimeUnit.MILLISECONDS);
				}

				@Override
				public void cancel() {
					cancelled = true;
				}
			});
		};
		
		Publisher<Integer> takePub = sub -> {
			pub.subscribe(new Subscriber<Integer>() {
				int count = 0;
				Subscription subsc;
				
				@Override
				public void onSubscribe(Subscription s) {
					subsc = s;
					sub.onSubscribe(s);
				}

				@Override
				public void onNext(Integer i) {
					sub.onNext(i);
					if(++count >= 5)	{
						subsc.cancel();
					}
				}

				@Override
				public void onError(Throwable t) {
					sub.onError(t);
				}

				@Override
				public void onComplete() {
					sub.onComplete();
				}
				
			});
		};
		
		takePub.subscribe(new Subscriber<Integer>() {
			@Override
			public void onComplete() {
				log.debug("onComplete");
			}

			@Override
			public void onError(Throwable t) {
				log.debug("Throwable : {}", t.getMessage());
			}

			@Override
			public void onNext(Integer i) {
				log.debug("onNext : {}", i);
			}

			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe");
				s.request(Long.MAX_VALUE);
			}
		});
	}
}
