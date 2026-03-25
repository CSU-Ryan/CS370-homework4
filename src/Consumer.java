public class Consumer implements Runnable {
    final int TOTAL_CONSUMPTION = 1000000;
    final int UPDATE_PERIOD = 100000;

    BoundedBuffer buffer;
    double bufferValueCounter = 0;


    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    void consume(double commodity) {
        bufferValueCounter += commodity;
    }

    void readFromBuffer() throws InterruptedException {
        consume(buffer.read());
    }

    void printUpdate(int i) {
        if ((i > 0) && (i % UPDATE_PERIOD == 0)) {
            System.out.printf("Consumer: Consumed %,d items, Cumulative value of consumed items=%.3f\n",
                    i, bufferValueCounter);
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < TOTAL_CONSUMPTION; i++) {
                printUpdate(i);
                readFromBuffer();
            }
        } catch (InterruptedException e) {
            System.err.println("ERROR: thread was interrupted.");
            throw new RuntimeException(e);
        }
    }
}
