import java.util.ArrayList;

public class FoodQueue {
    ArrayList<Customer> queue;
    public FoodQueue(int size,ArrayList<Customer> queue) {
        this.queue=queue;

        if(size==2){
            this.queue.add(null);
            this.queue.add(null);
        } else if (size==3) {
            this.queue.add(null);
            this.queue.add(null);
            this.queue.add(null);

        } else if (size==5) {
            this.queue.add(null);
            this.queue.add(null);
            this.queue.add(null);
            this.queue.add(null);
            this.queue.add(null);

        }
    }
    public ArrayList<Customer> getQueue(){
        return queue;
    }
}




