import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.pcf.PCFMessage;
import com.ibm.mq.pcf.PCFMessageAgent;

import java.io.IOException;

public class MQExample {

    public static void main(String[] args) {
        // Define MQ connection parameters
        String queueManagerName = "QM1";  // Replace with your Queue Manager name
        String hostName = "localhost";    // Replace with your MQ server host
        int port = 1414;                 // Replace with your MQ port
        String channel = "SYSTEM.DEF.SVRCONN";  // Replace with your channel name
        String queueName = "MYQUEUE";     // Replace with your queue name

        // Define connection properties
        MQEnvironment.hostname = hostName;
        MQEnvironment.port = port;
        MQEnvironment.channel = channel;

        // Create the MQ QueueManager connection
        MQQueueManager queueManager = null;

        try {
            // Connect to the queue manager
            queueManager = new MQQueueManager(queueManagerName);
            System.out.println("Connected to MQ Queue Manager: " + queueManagerName);

            // Define options for the Queue
            int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT;

            // Open the queue
            MQQueue queue = queueManager.accessQueue(queueName, openOptions);
            System.out.println("Opened queue: " + queueName);

            // Send a message to the queue
            MQMessage putMessage = new MQMessage();
            putMessage.writeString("Hello, IBM MQ!");
            MQPutMessageOptions pmo = new MQPutMessageOptions();
            queue.put(putMessage, pmo);
            System.out.println("Message sent to the queue: " + putMessage);

            // Retrieve a message from the queue
            MQMessage getMessage = new MQMessage();
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            queue.get(getMessage, gmo);
            String messageContent = getMessage.readString(getMessage.getMessageLength());
            System.out.println("Message received from the queue: " + messageContent);

            // Close the queue
            queue.close();
            System.out.println("Queue closed");

        } catch (MQException | IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (queueManager != null) {
                    queueManager.disconnect();
                    System.out.println("Disconnected from MQ Queue Manager");
                }
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
    }
}
