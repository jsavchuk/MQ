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
