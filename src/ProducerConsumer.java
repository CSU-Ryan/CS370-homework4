public class ProducerConsumer {

    public static void main(String[] args) {
        BoundedBuffer buffer = new BoundedBuffer();
        Thread producer = new Thread(new Producer(buffer));
        Thread consumer = new Thread(new Consumer(buffer));

        producer.start();
        consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            System.err.println("ERROR: thread was interrupted.");
            throw new RuntimeException(e);
        }

        System.out.println("Exiting!");
    }
}