package nc.util;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

public class StreamHelper {
	
	public static <T, U> U[] map(T[] array, Function<? super T, ? extends U> function, IntFunction<U[]> generator) {
		return Arrays.stream(array).map(function).toArray(generator);
	}
	
	public static <T, U> List<U> map(List<T> list, Function<? super T, ? extends U> function) {
		return list.stream().map(function).collect(Collectors.toList());
	}
	
	public static <T, U> Set<U> map(Set<T> set, Function<? super T, ? extends U> function) {
		return set.stream().map(function).collect(Collectors.toSet());
	}
}
