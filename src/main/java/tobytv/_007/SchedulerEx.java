package tobytv._007;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SchedulerEx {
	public static void main(String[] args) {
		Publisher<Integer> pub = sub -> {
			sub.onSubscribe(new Subscription()	{
				@Override
				public void cancel() {
					
				}

				@Override
				public void request(long n) {
					log.debug("request");
					sub.onNext(1);
					sub.onNext(2);
					sub.onNext(3);
					sub.onNext(4);
					sub.onNext(5);
					sub.onComplete();
				}
			});
		};
		// pub
		
		Publisher<Integer> subOnPub = sub -> {
			//pub.subscribe(sub);
			ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
				@Override
				public String getThreadNamePrefix()	{ return "subOn-";	}
			});
			es.execute(() -> pub.subscribe(sub)); 
			es.shutdown();
		};
		
		Publisher<Integer> pubOnPub = sub -> {
			subOnPub.subscribe(new Subscriber<Integer>() {
				ExecutorService es = Executors.newSingleThreadExecutor(new CustomizableThreadFactory() {
					@Override
					public String getThreadNamePrefix()	{ return "pubOn-";	}
				});
				
				@Override
				public void onComplete() {
					//log.debug("pubOnPub onComplete");
					es.execute(() -> sub.onComplete()); 
					es.shutdown();
				}

				@Override
				public void onError(Throwable t) {
					//log.debug("pubOnPub Throwable : {}", t.getMessage());
					es.execute(() -> sub.onError(t));
					es.shutdown();
				}

				@Override
				public void onNext(Integer i) {
					//log.debug("pubOnPub onNext : {}", i);
					es.execute(() -> sub.onNext(i)); 
				}

				@Override
				public void onSubscribe(Subscription s) {
					//log.debug("pubOnPub onSubscribe");
					sub.onSubscribe(s);
				}
			});
		};
		
		// sub
		//pub.subscribe(new Subscriber<Integer>() {
		//subOnPub.subscribe(new Subscriber<Integer>() {
		pubOnPub.subscribe(new Subscriber<Integer>() {
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
		
		log.debug("Exit");
	}
}
