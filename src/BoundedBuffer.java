public class BoundedBuffer {
    final int BUFFER_LENGTH = 1000;
    final double[] buffer = new double[BUFFER_LENGTH];

    // Indices for where to write to/read from next
    int writeTo = 0;
    int readFrom = 0;

    // Number of written buffer items which have yet to be read
    int queueLength = 0;


    /// Used to update indices, reflecting writing to queue.
    private void incrementQueue() {
        writeTo = (writeTo + 1) % BUFFER_LENGTH;
        queueLength++;
        notify();
    }

    /// Used to update indices, reflecting reading from queue.
    private void decrementQueue() {
        readFrom = (readFrom + 1) % BUFFER_LENGTH;
        queueLength--;
        notify();
    }


    /// Adds data to front of queue. Will wait until queue is not full.
    public void write(double data) throws InterruptedException {
        while (queueLength >= BUFFER_LENGTH) {
            // Waits until buffer is not full
            wait();
        }

        buffer[writeTo] = data;
        incrementQueue();
    }

    /// Reads data from back of queue. Will wait until queue is not empty.
    public double read() throws InterruptedException {
        while (queueLength <= 0) {
            // Waits until buffer is not empty
            wait();
        }

        double data = buffer[readFrom];
        decrementQueue();
        return data;
    }
}
