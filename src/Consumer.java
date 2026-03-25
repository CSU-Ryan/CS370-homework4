public class Consumer implements Runnable {
    // Total number of elements to consume
    final int TOTAL_CONSUMPTION = 1000000;
    // Wait length between print updates of buffer sum
    final int UPDATE_PERIOD = 100000;
    // Used to check that producer and consumer data match
    double bufferValueCounter = 0;

    final BoundedBuffer buffer;


    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    void consume(double commodity) {
        bufferValueCounter += commodity;
    }

    /// Reads and consumes data from buffer. Will wait for buffer to have space.
    void readFromBuffer() throws InterruptedException {
        synchronized (buffer) {
            consume(buffer.read());
        }
    }

    /// Prints bufferValueCounter after a printing period.
    void printUpdate(int i) {
        if (i % UPDATE_PERIOD == 0) {
            System.out.printf("Consumer: Consumed %,d items, Cumulative value of consumed items=%.3f\n",
                    i, bufferValueCounter);
        }
    }

    /// Consumes number of commodities specified from buffer.
    @Override
    public void run() {
        try {
            for (int i = 1; i <= TOTAL_CONSUMPTION; i++) {
                readFromBuffer();
                printUpdate(i);
            }
        } catch (InterruptedException e) {
            System.err.println("ERROR: thread was interrupted.");
            throw new RuntimeException(e);
        }

        System.out.println("Consumer: Finished consuming 1,000,000 items");
    }
}
