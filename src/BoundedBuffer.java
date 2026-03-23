public class BoundedBuffer {
    final Double[] array = new Double[1000];
    int printTo = 0;
    int readFrom = -1;

    // THIS IS DEFINITELY WRONG

    public synchronized void print(Double data) throws InterruptedException {
        while (printTo == readFrom) {
            wait();
        }

        array[printTo] = data;
        printTo++;
        if (readFrom == -1) readFrom++;
    }

    public synchronized Double read() throws InterruptedException {
        while (readFrom == printTo) {
            wait();
        }

        return array[readFrom++];
    }
}
