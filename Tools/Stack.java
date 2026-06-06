import java.lang.reflect.Array;


public class Stack<E> {
    
    private E[] stack;
    private int size = 0;
    private int capacity;


    @SuppressWarnings("unchecked")
    public Stack(Class<E> type, int capacity) {
        stack = (E[]) Array.newInstance(type, capacity);
        this.capacity = capacity;
    } 


    /**
     * @return The recent element in the stack
     */
    public E getTop() {return !isEmpty() ? stack[size - 1] : null;}


    /**
     * @return True, if stack is empty
     */
    public boolean isEmpty() {return size == 0;}


    /**
     * @return True, if the current number of elements reaches stack capacity
     */
    public boolean isFull() {return capacity == size;}


    /**
     * Adds an element at the top of the stack
     */
    public void push(E element) {
        if(isFull()) System.out.println("Stack is full!");
        else {
            stack[size] = element;
            size++;
        }
    }


    /**
     * Removes the element at the top the stack
     * @return The discarded element
     */
    public E pop() {
        E element = null;

        if(isEmpty()) System.out.println("Stack is empty!");
        else {
            element = stack[size - 1];
            stack[size - 1] = null;
            size--;
        }
         
        return element;
    }

    public void iterate() {
        for (E e : stack)
            if(e != null) System.out.print(e + " ");
    }

    
}