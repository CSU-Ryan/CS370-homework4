public class Consumer implements Runnable {
    final int TOTAL_CONSUMPTION = 1000000;
    final int UPDATE_PERIOD = 100000;

    final BoundedBuffer buffer;
    double bufferValueCounter = 0;


    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    void consume(double commodity) {
        bufferValueCounter += commodity;
    }

    void readFromBuffer() throws InterruptedException {
        synchronized (buffer) {
            consume(buffer.read());
        }
    }

    void printUpdate(int i) {
        if (i % UPDATE_PERIOD == 0) {
            System.out.printf("Consumer: Consumed %,d items, Cumulative value of consumed items=%.3f\n",
                    i, bufferValueCounter);
        }
    }

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
