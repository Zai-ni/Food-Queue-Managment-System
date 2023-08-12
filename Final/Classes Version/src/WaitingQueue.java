//using circular queue method to waiting queue
public class WaitingQueue {
    private Customer[] waitingQueue;
    private int front;
    private int rear;


    public WaitingQueue() {
        this.waitingQueue = new Customer[10];
        this.front = -1;
        this.rear = -1;
    }

    // Add an element to the rear of the queue
    public void enqueue(Customer customer) {
        if ((front == 0 && rear == waitingQueue.length - 1) || (front == rear + 1)) {
            System.out.println("Waiting queue also full.");
        } else {
            if (front == -1) {
                front = 0;
            }
            rear = (rear + 1) % waitingQueue.length;
            waitingQueue[rear] = customer;
            System.out.println(customer.getFullName()+" is added to the waiting queue");
        }
    }

    // Remove an element from the front of the queue
    public Customer dequeue() {
        Customer dequeueValue;
        if (front == -1) {
            dequeueValue=null;
        }else{
            dequeueValue = waitingQueue[front];
            if (front == rear) {
                front = -1;
                rear = -1;
            } else {
                front = (front + 1) % waitingQueue.length;
            }
        }
        return dequeueValue;
    }
    public Customer[] getWaitingQueue(){
        return waitingQueue;
    }

}
