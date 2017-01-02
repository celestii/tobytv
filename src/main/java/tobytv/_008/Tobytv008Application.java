package tobytv._008;

import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
@EnableAsync
public class Tobytv008Application {
	/*@Component
	public static class MyService	{
		@Async
		public ListenableFuture<String> hello() throws InterruptedException	{
			log.info("Hello()");
			Thread.sleep(2000);
			return new AsyncResult<>("Hello");
		}
	}
	
	@Autowired MyService myService;
	
	@Bean
	ThreadPoolTaskExecutor tp()	{
		ThreadPoolTaskExecutor te = new ThreadPoolTaskExecutor();
		te.setCorePoolSize(10);
		te.setMaxPoolSize(100);
		te.setQueueCapacity(200);
		te.setThreadNamePrefix("mythread");
		te.initialize();
		return te;
	}
	
	@Bean
	ApplicationRunner run()	{
		return args ->  {
			log.info("run()");
			ListenableFuture<String> f = myService.hello();
			f.addCallback(s -> System.out.println(s), e -> System.out.println(e.getMessage()));
			log.info("exit");
		};
	}
	
	public static void main(String[] args) {
		try(ConfigurableApplicationContext c = SpringApplication.run(Tobytv8Application.class, args))	{
			
		}
	}*/
	
	@RestController
	public static class MyController	{
		@GetMapping("/callable")
		public Callable<String> callable()	{
			log.info("callable");
			return () -> {
				log.info("async");
				Thread.sleep(2000);
				return "hello";
			};
		}
		/*public String callabe() throws InterruptedException	{
			log.info("async");
			Thread.sleep(2000);
			return "Hello";
		}*/
		
		Queue<DeferredResult<String>> results = new ConcurrentLinkedQueue<>();
		
		@GetMapping("/dr")
		public DeferredResult<String> deferredResult()	{
			log.info("dr");
			DeferredResult<String> dr = new DeferredResult<>(60000L);
			results.add(dr);
			return dr;
		}
		
		@GetMapping("/dr/count")
		public String drcount()	{
			return String.valueOf(results.size());
		}
		
		@GetMapping("/dr/event")
		public String drevent(String msg)	{
			for(DeferredResult<String> dr : results)	{
				dr.setResult("Hello " + msg);
				results.remove(dr);
			}
			
			return "OK";
		}
		
		@GetMapping("/emitter")
		public ResponseBodyEmitter emitter()	{
			log.info("emitter");
			ResponseBodyEmitter emitter = new ResponseBodyEmitter();
			
			Executors.newSingleThreadExecutor().submit(() -> {
				try	{
					for(int i=1; i<=50; i++)	{
						emitter.send("<p>Stream " + i + " </p>");
						Thread.sleep(100);
					}
				} catch(Exception e)	{
					
				}
				
			});
						
			return emitter;
		}
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Tobytv008Application.class, args);
	}
}
