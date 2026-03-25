import java.util.Random;

public class Producer implements Runnable {
    final Random random = new Random();
    BoundedBuffer buffer;
    double bufferValueCounter = 0;


    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    private double produce() {
        double product = random.nextDouble();
        bufferValueCounter += product;
        return product;
    }

    private void writeToBuffer() throws InterruptedException {
        buffer.write(produce());
    }

    private void printUpdate(int i) {
        if ((i > 0) && (i % 1e5 == 0)) {
            System.out.printf("Producer: Generated %,d items, Cumulative value of generated items=%.3f\n",
                    i, bufferValueCounter);
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1e6; i++) {
                printUpdate(i);
                writeToBuffer();
            }
        } catch (InterruptedException e) {
            System.err.println("ERROR: thread was interrupted.");
            throw new RuntimeException(e);
        }
    }
}
