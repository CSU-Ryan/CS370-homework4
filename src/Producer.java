import java.util.Random;

public class Producer implements Runnable {
    // Total number of elements to produce
    final int TOTAL_PRODUCTION = 1000000;
    // Wait length between print updates of buffer sum
    final int UPDATE_PERIOD = 100000;
    // Used to check that producer and consumer data match
    double bufferValueCounter = 0;

    final Random random = new Random();
    final BoundedBuffer buffer;


    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    double produce() {
        double product = random.nextDouble();
        bufferValueCounter += product;
        return product;
    }

    /// Produces data and writes to buffer. Will wait for buffer to have space.
    void writeToBuffer() throws InterruptedException {
        synchronized (buffer) {
            buffer.write(produce());
        }
    }

    /// Prints bufferValueCounter after a printing period.
    void checkPrintUpdate(int i) {
        if (i % UPDATE_PERIOD == 0) {
            System.out.printf("Producer: Generated %,d items, Cumulative value of generated items=%.3f\n",
                    i, bufferValueCounter);
        }
    }

    /// Produces number of products specified and writes to buffer.
    @Override
    public void run() {
        try {
            for (int i = 1; i <= TOTAL_PRODUCTION; i++) {
                writeToBuffer();
                checkPrintUpdate(i);
            }
        } catch (InterruptedException e) {
            System.err.println("ERROR: thread was interrupted.");
            throw new RuntimeException(e);
        }

        System.out.println("Producer: Finished generating 1,000,000 items");
    }
}
