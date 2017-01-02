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
public class PubSubGeneric {
	

	public static void main(String[] args) {
		Publisher<Integer> pub = iterPub(Stream.iterate(1, a -> a + 1).limit(10).collect(Collectors.toList()));
		Publisher<Integer> mapPub = mapPub(pub, s -> s * 10);
		mapPub.subscribe(logSub());
	}

	private static <T> Publisher<T> mapPub(Publisher<T> pub, Function<T, T> f) {
		return new Publisher<T>()	{
			@Override
			public void subscribe(Subscriber<? super T> sub) {
				pub.subscribe(new DelegateSub<T>(sub)	{
					@Override
					public void onNext(T i)	{
						sub.onNext(f.apply(i));
					}
				});
			}
		};
	}

	private static <T> Subscriber<T> logSub() {
		return new Subscriber<T>()	{
			@Override
			public void onComplete() {
				log.debug("onComplete : ");
			}

			@Override
			public void onError(Throwable e) {
				log.debug("onError : {}", e.getMessage());
			}

			@Override
			public void onNext(T i) {
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
