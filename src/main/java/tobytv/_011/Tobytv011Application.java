package tobytv._011;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.Netty4ClientHttpRequestFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.context.request.async.DeferredResult;

import io.netty.channel.nio.NioEventLoopGroup;

/**
 * 토비의 봄 TV 11화 - CompletableFuture
 *
 */
@SpringBootApplication
public class Tobytv011Application {
	@RestController	
	public static class MyController	{
		AsyncRestTemplate rt = new AsyncRestTemplate(new Netty4ClientHttpRequestFactory(new NioEventLoopGroup(1)));
			
		@Autowired MyService myService;
		
		static final String URL1 = "http://localhost:9091/service1?req={req}";
		static final String URL2 = "http://localhost:9091/service2?req={req}";
		
		@GetMapping("/rest")
		public DeferredResult<String> rest(String idx)	{
			DeferredResult<String> dr = new DeferredResult<>();
			
			toCF(rt.getForEntity(URL1, String.class, "hello" + idx))
				.thenCompose(s -> toCF(rt.getForEntity(URL2, String.class, s.getBody())))
				.thenApplyAsync(s2 -> myService.work(s2.getBody()))
				.thenAccept(s3 -> dr.setResult(s3))
				.exceptionally(e -> {dr.setErrorResult(e.getMessage()); return null;});
			
			return dr;
		}
		
		<T> CompletableFuture<T> toCF(ListenableFuture<T> lf)	{
			CompletableFuture<T> cf = new CompletableFuture<>();
			lf.addCallback(s -> cf.complete(s), e -> cf.completeExceptionally(e));
			return cf;
		}
	}
	
	@Service
	public static class MyService	{
		public String work(String req)	{
			return req + "/asyncwork";
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
		System.setProperty("server.port", "9090");
		SpringApplication.run(Tobytv011Application.class, args);
	}
}
