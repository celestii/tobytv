package tobytv._009;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 토비의 봄 TV 9화 - 스프링 리엑티브 웹 개발 5부, 비동기 RestTemplate과 비동시 MVC의 결합
 *
 */
@SpringBootApplication
@EnableAsync
public class RemoteService {
	@RestController	
	public static class MyController	{
		@GetMapping("/service1")
		public String service1(String req) throws InterruptedException	{
			Thread.sleep(2000);
			return req + "/service1";
		}
		
		@GetMapping("/service2")
		public String service2(String req) throws InterruptedException	{
			Thread.sleep(2000);
			return req + "/service2";
		}
	}
	
	public static void main(String[] args) {
		System.setProperty("SERVER_PORT", "9091");
		System.setProperty("server.tomcat.max-threads", "1000");
		SpringApplication.run(RemoteService.class, args);
	}
}
