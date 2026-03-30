# File Structure:
```
+-- src
|   +-- BoundedBuffer.java [The finite buffer for transferring data from Producer to Consumer]
|   +-- Producer.java [Produces random doubles and pushes them to the buffer]
|   +-- Consumer.java [Consumes items off the buffer]
|   \-- ProducerConsumer.java [Main script for running the Producer and Consumer via threads]
+-- Makefile [Builds and cleans java files for running]
\-- README.md [This file]
```

# Instructions:

Run the project by first building everything with 

`$ make build` or `$ make all`

then call 

`$ java ProducerConsumer` 

to run the project. When complete, build files can be cleaned with 

`$ make clean`