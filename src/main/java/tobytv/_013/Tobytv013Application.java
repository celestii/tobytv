package tobytv._013;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


/**
 * 토비의 봄 TV 13화 - Reactive Programming - WebFlux - SpringBoot 2.0 M1, SpringFramework 5.0 RC1
 * 스프링 5.0 WebFlux에서 사용되는 Reactor의 Mono의 기본 동작방식을 살펴봅니다.
 * Mono의 동작방식과 block() -> block에서 subscribe가 동작한다.
 */
@SpringBootApplication
@RestController
@Slf4j
@EnableAsync
public class Tobytv013Application {
	@GetMapping("/")
	Mono<String> hello()	{
		log.info("pos1");
		String msg = generaterHello();
		Mono<String> m = Mono.just(msg).doOnNext(log::info).log();	// Publisher -> (Publisher) -> (Publisher) -> Subscriber
		String msg2 = m.block();
		//Mono<String> m = Mono.fromSupplier(() -> generaterHello()).doOnNext(log::info).log();
		//m.subscribe();
		log.info("pos2 : {}", msg2);
		return m;
	}
	
	private String generaterHello()	{
		log.info("method generaterHello()");
		return "Hello Mono";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Tobytv013Application.class, args);
	}
}
