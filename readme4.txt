To connect from Java to IBM MQ 8.5.5, you need to use IBM MQ's Java libraries (JMS API or MQ classes for Java). Below are the steps you can follow to achieve this:

### Step 1: Install IBM MQ and Get the Required Libraries
- Install IBM MQ 8.5.5 on your machine or use an MQ server if you don't have it installed.
- You need to include the required IBM MQ client JARs in your Java project:
  - `com.ibm.mq.jar`
  - `com.ibm.mq.jmqi.jar`
  - `com.ibm.mq.commonservices.jar`
  - `com.ibm.mq.headers.jar`
  - `com.ibm.mq.transport.jar`

These JAR files are typically found in the MQ installation directory under `lib/`.

### Step 2: Set Up Environment Variables
Ensure the following environment variables are set:
- `MQ_INSTALLATION_PATH` – The path to the MQ installation directory.
- `CLASSPATH` – Add the necessary JARs to the Java classpath.
  
For example, if you're running a local MQ client:
```bash
export MQ_INSTALLATION_PATH=/opt/mqm
export CLASSPATH=$CLASSPATH:$MQ_INSTALLATION_PATH/java/lib/*
```

### Step 3: Java Code to Connect to IBM MQ

Here's an example code to connect to IBM MQ, send a message, and receive a response:

#### Sending a Message to IBM MQ

```java
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQException;

public class MQSender {
    public static void main(String[] args) {
        String queueManagerName = "QM1";  // Name of your Queue Manager
        String queueName = "MYQUEUE";     // Name of the Queue
        String channel = "SVRCONN.CHANNEL";
        String host = "127.0.0.1";        // MQ Server IP or Host
        int port = 1414;                  // MQ Port number

        try {
            // Set up the MQ connection
            MQQueueManager queueManager = new MQQueueManager(queueManagerName, host, port, channel);
            
            // Create a queue object
            MQQueue queue = queueManager.accessQueue(queueName, MQQueueManager.MQOO_OUTPUT);

            // Create a message object and set the message content
            MQMessage message = new MQMessage();
            message.writeUTF("Hello from Java!");

            // Create options for putting the message
            MQPutMessageOptions options = new MQPutMessageOptions();
            
            // Put the message on the queue
            queue.put(message, options);

            // Close the queue and queue manager
            queue.close();
            queueManager.disconnect();
            System.out.println("Message sent to the queue successfully!");
        } catch (MQException | java.io.IOException e) {
            System.err.println("Error sending message: " + e.getMessage());
        }
    }
}
```

#### Receiving a Message from IBM MQ

```java
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQException;

public class MQReceiver {
    public static void main(String[] args) {
        String queueManagerName = "QM1";  // Name of your Queue Manager
        String queueName = "MYQUEUE";     // Name of the Queue
        String channel = "SVRCONN.CHANNEL";
        String host = "127.0.0.1";        // MQ Server IP or Host
        int port = 1414;                  // MQ Port number

        try {
            // Set up the MQ connection
            MQQueueManager queueManager = new MQQueueManager(queueManagerName, host, port, channel);

            // Create a queue object
            MQQueue queue = queueManager.accessQueue(queueName, MQQueueManager.MQOO_INPUT_AS_Q_DEF);

            // Create a message object to get the message
            MQMessage message = new MQMessage();
            MQGetMessageOptions options = new MQGetMessageOptions();

            // Get the message from the queue
            queue.get(message, options);
            
            // Print the received message
            String receivedMessage = message.readUTF();
            System.out.println("Received message: " + receivedMessage);

            // Close the queue and queue manager
            queue.close();
            queueManager.disconnect();
        } catch (MQException | java.io.IOException e) {
            System.err.println("Error receiving message: " + e.getMessage());
        }
    }
}
```

### Step 4: Running the Java Program
1. **Set up your Queue Manager** in IBM MQ.
2. **Create the Queue** where you will send/receive messages (e.g., `MYQUEUE`).
3. **Run the sender** (to put a message on the queue).
4. **Run the receiver** (to get the message from the queue).

### Step 5: Troubleshooting
- Ensure that the MQ server is accessible from your Java client.
- Make sure the queue manager is running.
- Verify the MQ connection settings (channel, host, port, etc.).
- Check IBM MQ logs for any issues related to the connection.

### Notes:
- You can use **JMS API** for more advanced features, such as message selectors, message listeners, etc., but the example above uses **IBM MQ classes** directly (MQ API).
- Ensure that the IBM MQ client libraries are included in your Java project's classpath when compiling and running the code.

Let me know if you need any further details!
