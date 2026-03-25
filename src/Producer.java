import java.util.Random;

public class Producer implements Runnable {
    final int TOTAL_PRODUCTION = 1000000;
    final int UPDATE_PERIOD = 100000;

    final Random random = new Random();
    final BoundedBuffer buffer;
    double bufferValueCounter = 0;


    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    double produce() {
        double product = random.nextDouble();
        bufferValueCounter += product;
        return product;
    }

    void writeToBuffer() throws InterruptedException {
        synchronized (buffer) {
            buffer.write(produce());
        }
    }

    void printUpdate(int i) {
        if (i % UPDATE_PERIOD == 0) {
            System.out.printf("Producer: Generated %,d items, Cumulative value of generated items=%.3f\n",
                    i, bufferValueCounter);
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 1; i <= TOTAL_PRODUCTION; i++) {
                writeToBuffer();
                printUpdate(i);
            }
        } catch (InterruptedException e) {
            System.err.println("ERROR: thread was interrupted.");
            throw new RuntimeException(e);
        }

        System.out.println("Producer: Finished generating 1,000,000 items");
    }
}
