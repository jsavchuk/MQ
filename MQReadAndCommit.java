import com.ibm.mq.*;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQException;

public class MQReadAndCommit {

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

        // Declare the QueueManager and MQQueue variables
        MQQueueManager queueManager = null;
        MQQueue queue = null;

        try {
            // Connect to the MQ QueueManager
            queueManager = new MQQueueManager(queueManagerName);
            System.out.println("Connected to MQ Queue Manager: " + queueManagerName);

            // Define options to open the queue for input
            int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT | MQConstants.MQOO_FAIL_IF_QUIESCING;

            // Open the queue
            queue = queueManager.accessQueue(queueName, openOptions);
            System.out.println("Opened queue: " + queueName);

            // Create a message object to receive the message
            MQMessage getMessage = new MQMessage();

            // Create a GetMessageOptions object (default options)
            MQGetMessageOptions gmo = new MQGetMessageOptions();
            gmo.options = MQConstants.MQGMO_WAIT | MQConstants.MQGMO_CONVERT;
            gmo.waitInterval = 5000; // Wait for 5 seconds for a message

            // Get the message from the queue
            queue.get(getMessage, gmo);
            String messageContent = getMessage.readString(getMessage.getMessageLength());
            System.out.println("Message received: " + messageContent);

            // After successfully processing the message, commit the transaction
            queueManager.commit();
            System.out.println("Transaction committed. Message removed from queue.");

        } catch (MQException | java.io.IOException e) {
            e.printStackTrace();
            try {
                if (queueManager != null) {
                    queueManager.backout();  // Rollback if there's an error
                    System.out.println("Transaction rolled back.");
                }
            } catch (MQException mqEx) {
                mqEx.printStackTrace();
            }
        } finally {
            try {
                if (queue != null) {
                    queue.close(); // Close the queue after processing
                    System.out.println("Queue closed.");
                }
                if (queueManager != null) {
                    queueManager.disconnect(); // Disconnect from the queue manager
                    System.out.println("Disconnected from MQ Queue Manager.");
                }
            } catch (MQException e) {
                e.printStackTrace();
            }
        }
    }
}
