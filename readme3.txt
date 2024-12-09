To check the depth (the number of messages) in an IBM MQ queue using Java, you can use the MQQueue class to access the queue and then retrieve the queue attributes. Specifically, you can use the MQQueueManager and the MQQueue objects to query the queue depth.

Below is an example Java code to check the depth of an IBM MQ queue.

MQQueueDepthChecker.java 

Explanation:
MQ Environment Configuration:

The MQEnvironment class is configured with the IBM MQ server's hostname, port, and channel information. These values must match your IBM MQ setup.
Opening the Queue:

The queue is opened using the MQOO_INQUIRE option, which allows you to query the queue's attributes without altering its contents.
Getting the Queue Depth:

The getCurrentDepth(int[] depth) method is called on the MQQueue object to retrieve the number of messages in the queue (i.e., the queue depth).
The method populates the depth array, where depth[0] will contain the queue's current depth.
Error Handling:

MQException is used to catch errors related to MQ operations. If an exception occurs while accessing the queue, an error message is printed.
Cleanup:

The queue.close() method is called to close the queue after performing the inquiry.
The queueManager.disconnect() method disconnects from the queue manager to clean up resources.
Key Points:
MQOO_INQUIRE: This is the queue open option used when you want to inquire about a queue's attributes, like its current depth, without modifying the queue.
Queue Depth: The getCurrentDepth() method retrieves the number of messages currently in the queue.
Error Handling: The MQException is essential to catch errors like connection issues, queue access failures, etc.
Prerequisites:
Ensure you have the IBM MQ client libraries (com.ibm.mq.allclient.jar) in your project's classpath.
Make sure the IBM MQ server and queue manager are running, and the specified queue exists.
The MQ user must have the necessary permissions to access the queue and inquire about its attributes.
Running the Code:
To run the code:

Replace the placeholder values (queueManagerName, hostName, port, queueName) with the actual connection details for your MQ server.
The program will output the current depth of the specified queue.
This code assumes that the queue is accessible and that the necessary permissions are granted to the user to perform the queue depth inquiry.




To check the depth (the number of messages) in an IBM MQ queue using Java, you can use the `MQQueue` class to access the queue and then retrieve the queue attributes. Specifically, you can use the `MQQueueManager` and the `MQQueue` objects to query the queue depth.

Below is an example Java code to check the depth of an IBM MQ queue.

### Code to Check the Depth of an IBM MQ Queue

```java
import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQException;

public class MQQueueDepthChecker {

    public static void main(String[] args) {

        // Define MQ connection parameters
        String queueManagerName = "QM1";  // Replace with your Queue Manager name
        String hostName = "localhost";    // Replace with your MQ server host
        int port = 1414;                 // Replace with your MQ port
        String channel = "SYSTEM.DEF.SVRCONN";  // Replace with your channel name
        String queueName = "MYQUEUE";     // Replace with your queue name

        // Set the MQ environment properties
        MQEnvironment.hostname = hostName;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;

        MQQueueManager queueManager = null;
        MQQueue queue = null;

        try {
            // Connect to the QueueManager
            queueManager = new MQQueueManager(queueManagerName);
            System.out.println("Connected to MQ Queue Manager: " + queueManagerName);

            // Define options to open the queue
            int openOptions = MQConstants.MQOO_INQUIRE;

            // Open the queue for inquiry (no input/output)
            queue = queueManager.accessQueue(queueName, openOptions);
            System.out.println("Opened queue: " + queueName);

            // Retrieve the queue depth using the MQQueue attribute
            int[] depth = new int[1];  // Array to hold the queue depth
            queue.getCurrentDepth(depth);  // Get the current depth of the queue

            // Print the queue depth
            System.out.println("Queue depth of " + queueName + ": " + depth[0]);

        } catch (MQException e) {
            System.out.println("An error occurred while accessing the MQ queue: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (queue != null) {
                    queue.close(); // Close the queue after inquiry
                    System.out.println("Queue closed.");
                }
                if (queueManager != null) {
                    queueManager.disconnect(); // Disconnect from the QueueManager
                    System.out.println("Disconnected from MQ Queue Manager.");
                }
            } catch (MQException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
```

### Explanation:

1. **MQ Environment Configuration**:
   - The `MQEnvironment` class is configured with the IBM MQ server's hostname, port, and channel information. These values must match your IBM MQ setup.
   
2. **Opening the Queue**:
   - The queue is opened using the `MQOO_INQUIRE` option, which allows you to query the queue's attributes without altering its contents.

3. **Getting the Queue Depth**:
   - The `getCurrentDepth(int[] depth)` method is called on the `MQQueue` object to retrieve the number of messages in the queue (i.e., the queue depth).
   - The method populates the `depth` array, where `depth[0]` will contain the queue's current depth.

4. **Error Handling**:
   - `MQException` is used to catch errors related to MQ operations. If an exception occurs while accessing the queue, an error message is printed.

5. **Cleanup**:
   - The `queue.close()` method is called to close the queue after performing the inquiry.
   - The `queueManager.disconnect()` method disconnects from the queue manager to clean up resources.

### Key Points:

- **`MQOO_INQUIRE`**: This is the queue open option used when you want to inquire about a queue's attributes, like its current depth, without modifying the queue.
- **Queue Depth**: The `getCurrentDepth()` method retrieves the number of messages currently in the queue.
- **Error Handling**: The `MQException` is essential to catch errors like connection issues, queue access failures, etc.

### Prerequisites:
- Ensure you have the IBM MQ client libraries (`com.ibm.mq.allclient.jar`) in your project's classpath.
- Make sure the IBM MQ server and queue manager are running, and the specified queue exists.
- The MQ user must have the necessary permissions to access the queue and inquire about its attributes.

### Running the Code:
To run the code:
- Replace the placeholder values (`queueManagerName`, `hostName`, `port`, `queueName`) with the actual connection details for your MQ server.
- The program will output the current depth of the specified queue.

This code assumes that the queue is accessible and that the necessary permissions are granted to the user to perform the queue depth inquiry.