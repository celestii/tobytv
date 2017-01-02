package tobytv._006;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import lombok.extern.slf4j.Slf4j;

/**
 * Reactive Stream - Operators
 * 
 * Publlisher -> [Data1] -> Op1(mapPub) -> [Data2] -> Op2(logPub)
 * 							<- subscribe(logPub)
 * 							-> onSubscribe(s)
 * 							-> onNext
 * 							-> onNext
 * 							-> onComplite
 * 1. map (data1 -> f -> data2)
 *
 */
@Slf4j
public class PubSub2 {
	

	public static void main(String[] args) {
		Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
		//Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);
		//Publisher<Integer> map2Pub = mapPub(mapPub, s -> -s);
		////pub.subscribe(logSub());
		////mapPub.subscribe(logSub());
		//map2Pub.subscribe(logSub());
		
		/*Publisher<Integer> sumPub = sumPub(pub);
		sumPub.subscribe(logSub());*/
		
		Publisher<Integer> resucePub = resucePub(pub, 0, (a, b) -> a + b);
		resucePub.subscribe(logSub());
	}

	private static Publisher<Integer> resucePub(Publisher<Integer> pub, int init, BiFunction<Integer, Integer, Integer> f) {
		return new Publisher<Integer>()	{
			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				pub.subscribe(new DelegateSub<Integer>(sub)	{
					int result =  init;
					public void onNext(Integer i)	{
						result =  f.apply(result, i);
					}
					@Override
					public void onComplete() {
						sub.onNext(result);
						sub.onComplete();
					}
				});
			}
		};
	}

	private static Publisher<Integer> sumPub(Publisher<Integer> pub) {
		return new Publisher<Integer>()	{
			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				pub.subscribe(new DelegateSub<Integer>(sub)	{
					int sum = 0;
					@Override
					public void onNext(Integer i)	{
						sum += i;
					}
					@Override
					public void onComplete() {
						sub.onNext(sum);
						sub.onComplete();
					}
				});
			}
		};
	}

	private static Publisher<Integer> mapPub(Publisher<Integer> pub, Function<Integer, Integer> f) {
		return new Publisher<Integer>()	{
			@Override
			public void subscribe(Subscriber<? super Integer> sub) {
				pub.subscribe(new DelegateSub<Integer>(sub)	{
					@Override
					public void onNext(Integer i)	{
						sub.onNext(f.apply(i));
					}
				});
			}
		};
	}

	private static Subscriber<Integer> logSub() {
		return new Subscriber<Integer>()	{
			@Override
			public void onComplete() {
				log.debug("onComplete : ");
			}

			@Override
			public void onError(Throwable e) {
				log.debug("onError : {}", e.getMessage());
			}

			@Override
			public void onNext(Integer i) {
				log.debug("onNext : {}", i);
			}

			@Override
			public void onSubscribe(Subscription s) {
				log.debug("onSubscribe : ");
				s.request(Long.MAX_VALUE);
			}
		};
	}

	private static Publisher<Integer> iterPub(List<Integer> list) {
		return new Publisher<Integer>()	{
			@Override
			public void subscribe(Subscriber<? super Integer> sub)	{
				
				sub.onSubscribe(new Subscription()	{
					@Override
					public void request(long n) {
						try	{
							list.forEach(s -> sub.onNext(s));
							sub.onComplete();
						}catch(Throwable t)	{
							sub.onError(t);
						}
					}
					
					@Override
					public void cancel() {
						
					}
				});
			}
		};
	}
}
