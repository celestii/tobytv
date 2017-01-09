package tobytv._010;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 토비의 봄 TV 9화 - 스프링 리엑티브 웹 개발 5부, 비동기 RestTemplate과 비동시 MVC의 결합
 *
 */
@SpringBootApplication
@EnableAsync
public class Tobytv010Application {
	@RestController	
	public static class MyController	{
		//AsyncRestTemplate rt = new AsyncRestTemplate();
		AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
			
		@Autowired MyService myService;
		
		static final String URL1 = "http://localhost:9091/service1?req={req}";
		static final String URL2 = "http://localhost:9091/service2?req={req}";
		
		@GetMapping("/rest")
		public DeferredResult<String> rest(String idx)	{
			DeferredResult<String> dr = new DeferredResult<>();
			
			Completion
				.from(rt.getForEntity(URL1, String.class, "hello" + idx))
				.andApply(s -> rt.getForEntity(URL2, String.class, s.getBody()))
				.andApply(s -> myService.work(s.getBody()))
				.andError(e -> dr.setErrorResult(e.toString()))
				.andAccept(s -> dr.setResult(s));
			
			return dr;
		}
	}
	
	@Service
	public static class MyService	{
		public ListenableFuture<String> work(String req)	{
			return new AsyncResult<>(req + "/asynwork");
		}
	}
	
	@Bean
	public ThreadPoolTaskExecutor myThreadPool()	{
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(1);
		te.setMaxPoolSize(1);
		te.initialize();
		return te;
	}
	
	public static void main(String[] args) {
		System.setProperty("SERVER_PORT", "9090");
		SpringApplication.run(Tobytv010Application.class, args);
	}
}
