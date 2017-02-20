package tobytv._011;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CFuture {
	public static void main(String[] args) throws ExecutionException, InterruptedException	{
		/*//CompletableFuture<Integer> f = CompletableFuture.completedFuture(1);
		CompletableFuture<Integer> f = new CompletableFuture<>();
		//f.complete(2);
		f.completeExceptionally(new RuntimeException());
		log.info("{}", f.get());*/
		
		/*CompletableFuture
						.runAsync(() -> log.info("runAsync"))
						.thenRun(() -> log.info("thenRun1"))
						.thenRun(() -> log.info("thenRun2"));*/
		
		/*CompletableFuture
						.supplyAsync(() -> {
							log.info("runAsync");
							//if(1 == 1)	throw new RuntimeException();
							return 1;
						})
						.thenApply(s -> {
							log.info("thenApply1 {}", s);
							return s + 1;
						})
						.thenApply(s2 -> {
							log.info("thenApply2 {}", s2);
							return s2 * 3;
						})
						.exceptionally(e -> -10)
						.thenAccept(s3 -> log.info("thenAccept {}", s3));*/
		
		ExecutorService es = Executors.newFixedThreadPool(10);
		
		CompletableFuture
						.supplyAsync(() -> {
							log.info("runAsync");
							return 1;
						}, es)
						.thenCompose(s -> {
							log.info("thenCompose {}", s);
							return CompletableFuture.completedFuture(s + 1);
						})
						.thenApplyAsync(s2 -> {
							log.info("thenApply {}", s2);
							return s2 * 3;
						}, es)
						.exceptionally(e -> -10)
						.thenAcceptAsync(s3 -> log.info("thenAccept {}", s3), es);
		
		log.info("exit");
		
		ForkJoinPool.commonPool().shutdown();
		ForkJoinPool.commonPool().awaitTermination(10, TimeUnit.SECONDS);
		
		es.shutdown();
		es.awaitTermination(10, TimeUnit.SECONDS);
	}
}
