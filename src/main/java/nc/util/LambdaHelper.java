package nc.util;

import java.util.function.*;

public class LambdaHelper {
	
	public static <T> T also(T t, Consumer<T> consumer) {
		consumer.accept(t);
		return t;
	}
	
	@FunctionalInterface
	public interface ThrowingRunnable {
		
		void run() throws Exception;
	}
	
	public static void runThrowing(ThrowingRunnable runnable) {
		try {
			runnable.run();
		}
		catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}
	
	@FunctionalInterface
	public interface ThrowingConsumer<T> {
		
		void accept(T x) throws Exception;
	}
	
	public static <T> void acceptThrowing(ThrowingConsumer<T> consumer, T x) {
		try {
			consumer.accept(x);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}
	
	@FunctionalInterface
	public interface ThrowingSupplier<T> {
		
		T get() throws Exception;
	}
	
	public static <T> T getThrowing(ThrowingSupplier<T> supplier) {
		try {
			return supplier.get();
		}
		catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}
	
	@FunctionalInterface
	public interface ThrowingFunction<A, B> {
		
		B apply(A x) throws Exception;
	}
	
	public static <A, B> B applyThrowing(ThrowingFunction<A, B> function, A x) {
		try {
			return function.apply(x);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException();
		}
	}
}
