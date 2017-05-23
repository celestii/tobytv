package tobytv._012;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
public class RemoteService {
	@RestController	
	public static class MyController	{
		@GetMapping("/service1")
		public Mono<String> service1(String req) throws InterruptedException	{
			Thread.sleep(1000);
			return Mono.just(req + "/service1");
		}
		
		@GetMapping("/service2")
		public Mono<String> service2(String req) throws InterruptedException	{
			Thread.sleep(1000);
			return Mono.just(req + "/service2");
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("server.port", "9091");
		System.setProperty("server.tomcat.max-threads", "1000");
		SpringApplication.run(RemoteService.class, args);
	}
}
