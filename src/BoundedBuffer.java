public class BoundedBuffer {
    final int BUFFER_LENGTH = 1000;
    final double[] array = new double[BUFFER_LENGTH];

    int writeTo = 0;
    int readFrom = 0;
    int queueLength = 0;


    /// Used to update indices, reflecting added queue item.
        System.out.println("Buffer: Incrementing queue.");
    private void incrementQueue() {
        writeTo = (writeTo + 1) % BUFFER_LENGTH;
        queueLength++;
        notify();
    }

    /// Used to update indices, reflecting removed queue item.
        System.out.println("Buffer: decrementing queue.");
    private void decrementQueue() {
        readFrom = (readFrom + 1) % BUFFER_LENGTH;
        queueLength--;
        notify();
    }


    public void write(double data) throws InterruptedException {
        System.out.printf("Buffer write: checking queue length (%d)\n", queueLength);
        while (queueLength >= BUFFER_LENGTH) {
            System.out.println("Buffer write: Waiting!");
            wait();
        }

        array[writeTo] = data;
        System.out.printf("Buffer write: Wrote %f to %d.\n", data, writeTo);
        incrementQueue();
    }

    public double read() throws InterruptedException {
        System.out.printf("Buffer read: checking queue length (%d)\n", queueLength);
        while (queueLength <= 0) {
            System.out.println("Buffer read: Waiting!");
            wait();
        }

        double value = array[readFrom];
        System.out.printf("Buffer read: Read %f from %d.\n", value, readFrom);
        decrementQueue();
        return value;
    }
}
