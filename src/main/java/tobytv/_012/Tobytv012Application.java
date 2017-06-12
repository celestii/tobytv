package tobytv._012;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;


/**
 * 토비의 봄 TV 12화 - Reactive Programming - WebFlux - SpringBoot 2.0 M1, SpringFramework 5.0 RC1
 *
 */
@SpringBootApplication
@RestController
@Slf4j
@EnableAsync
public class Tobytv012Application {
	static final String URL1 = "http://localhost:9091/service1?req={req}";
	static final String URL2 = "http://localhost:9091/service2?req={req}";
		
	@Autowired MyService myService;
	
	WebClient client = WebClient.create();
	
	@GetMapping("/rest")
	public Mono<String> rest(int idx)	{
		return 	client.get().uri(URL1, idx).exchange()				// Mono<ClientResponse>
					.flatMap(c -> c.bodyToMono(String.class))		// Mono<String>
					.flatMap(res1 -> client.get().uri(URL2, res1).exchange())	// Mono<ClientResponse>
					.flatMap(c -> c.bodyToMono(String.class))		// Mono<String>
					.doOnNext(c -> log.info(c))
					.flatMap(res2 -> Mono.fromCompletionStage(myService.work(res2)))			// CompletableFuture<String> -> Mono<String>
					.doOnNext(c -> log.info(c));
		//Mono<ClientResponse> res = client.get().uri(URL1, idx).exchange();
		//Mono<Mono<String>> body = res.map(clientResponse -> clientResponse.bodyToMono(String.class));
		//Mono<String> body = res.flatMap(clientResponse -> clientResponse.bodyToMono(String.class));
		//return body;
		
		//return Mono.just("Hello");
	}
	
	public static void main(String[] args) {
		System.setProperty("server.port", "9090");
		System.setProperty("reactor.ipc.netty.workerCount", "2");
		System.setProperty("reactor.ipc.pool.maxConnections", "2000");
		SpringApplication.run(Tobytv012Application.class, args);
	}
	
	@Service
	public static class MyService	{
		@Async
		public CompletableFuture<String> work(String req)	{	
			return CompletableFuture.completedFuture(req + "/asyncwork");	
		}
	}
}
