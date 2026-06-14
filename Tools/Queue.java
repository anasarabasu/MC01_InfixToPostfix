import java.lang.reflect.Array;


public class Queue<E>{

    private E[] queue;

    private int size = 0;
    private int capacity;

    public int getCapacity() {return capacity;}

    private int head = 0;
    private int tail = 0;


    @SuppressWarnings("unchecked")
    public Queue(Class<E> type, int capacity) {
        queue = (E[]) Array.newInstance(type, capacity);
        this.capacity = capacity;
    }


    /**
     * @return The element at the head of the queue
     */
    public E getHead() {
        E element = null;

        if(isEmpty()) System.out.println("Queue has no elements!");
        else element = queue[head];

        return element;
    }


    /**
     * @return The element at the tail of the queue
     */
    public E getTail() {
        E element = null;

        if(isEmpty()) System.out.println("Queue has no elements!");
        else element = queue[(tail - 1 + capacity) % capacity];

        return element;
    }


    /**
     * @return True, if queue is empty
     */
    public boolean isEmpty() {return size == 0;} 


    /**
     * @return True, if the current number of elements reaches queue capacity
     */
    public boolean isFull() {return capacity == size;}


    /**
     * Appends an element to the end of the queue
     */
    public void enqueue(E element) {
        if(isFull()) System.out.println("Queue is full!");
        else {
            queue[tail] = element;
            size++;
            tail++;

            if(tail >= capacity) tail = 0;
        }
    }


    /**
     * Removes the first element from the queue
     * @return The discarded element
     */
    public E dequeue() {
        E element = null;

        if(isEmpty()) System.out.println("Queue has no elements!");
        else {
            element = queue[head];
            queue[head] = null;
            size--;
            head++;

            if(head >= capacity) head = 0;
        }

        return element;
    }

    public String iterate() {
        String string = "";
        for (E e : queue) 
            if(e != null) string = string.concat(e + " ");
        
        System.out.print(string);
        return string;
    }

}