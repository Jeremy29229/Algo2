
public interface Queueable<T extends Comparable<T>>
{
	boolean offer(T data);
	T peek();
	T poll();
}
