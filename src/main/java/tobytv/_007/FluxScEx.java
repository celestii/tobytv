package tobytv._007;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class FluxScEx {
	
	/**
	 * User Thread, Daemon Thread
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		/*Flux.range(1, 10)
			.publishOn(Schedulers.newSingle("pub"))
			.log()
			.subscribeOn(Schedulers.newSingle("sub"))
			.subscribe(System.out::println);
		
		System.out.println("Exit");*/
		
		Flux.interval(Duration.ofMillis(200))
			.take(10)
			.subscribe(s -> log.debug("onNext : {}", s));
		
		log.debug("exit");
		TimeUnit.SECONDS.sleep(5);
	}
}
