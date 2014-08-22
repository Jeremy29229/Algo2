public class Controller
{
	public static void main(String[] args)
	{
		AVLBasedPriorityQueue<Integer> a = new AVLBasedPriorityQueue<Integer>();
		a.offer(3);
		a.offer(5);
		a.offer(5);
		a.offer(2);
		a.offer(4);
		a.offer(0);
		a.offer(1);
		System.out.println(a.poll());
		System.out.println(a.poll());
		System.out.println(a.poll());
		System.out.println(a.poll());
		System.out.println(a.poll());
		System.out.println(a.poll());
		System.out.println(a.poll());
		
		System.out.println();
		
		HeapBasedPriorityQueue<Integer> h = new HeapBasedPriorityQueue<Integer>(10);
		h.offer(3);
		h.offer(5);
		h.offer(5);
		h.offer(2);
		h.offer(4);
		h.offer(0);
		h.offer(1);
		System.out.println(h.poll());
		System.out.println(h.poll());
		System.out.println(h.poll());
		System.out.println(h.poll());
		System.out.println(h.poll());
		System.out.println(h.poll());
		System.out.println(h.poll());
		System.out.println(h.poll());
		

	}
}
