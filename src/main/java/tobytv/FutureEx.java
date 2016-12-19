package tobytv;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureEx {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService es = Executors.newCachedThreadPool();
		
		// Future
		/*Future<String> f = es.submit(() -> {
			Thread.sleep(2000);
			log.debug("Async");
			return "Hello";
		});
		
		System.out.println(f.isDone());
		Thread.sleep(2000);
		log.debug("Exit");
		System.out.println(f.isDone());
		
		log.debug(f.get());
		*/
		
		//Callback
		/*FutureTask<String> f = new FutureTask<String>(() -> {
			Thread.sleep(2000);
			log.debug("Async");
			return "Hello";
		})	{
			@Override
			protected void done()	{
				try {
					System.out.println(get());
				} catch (InterruptedException e){
					
				} catch (ExecutionException e) {
					
				}
			}
		};
		
		es.execute(f);
		es.shutdown();*/
		
		CallbackFutureTask f= new CallbackFutureTask(() -> {
			Thread.sleep(2000);
			//if(1==1) throw new RuntimeException("Asunc ERROR!!!");
			log.debug("Async");
			return "Hello";
			},
			s -> System.out.println("Resut : " + s),
			e -> System.out.println("Error : " + e.getMessage()));
	
		es.execute(f);
		es.shutdown();
	}
	
	public static class CallbackFutureTask extends FutureTask<String>	{
		SuccessCallback sc;
		ExceptionCallback ec;
		public CallbackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec) {
			super(callable);
			this.sc = Objects.requireNonNull(sc);
			this.ec = Objects.requireNonNull(ec);
		}
		
		@Override
		protected void done()	{
			try {
				System.out.println(get());
			} catch (InterruptedException e){
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				ec.onError(e.getCause());
			}
		}
	}
	
	interface SuccessCallback	{
		void onSuccess(String result);
	}
	
	interface ExceptionCallback	{
		void onError(Throwable t);
	}
}
