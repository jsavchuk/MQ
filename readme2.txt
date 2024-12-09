To read from an IBM MQ queue and commit the message (i.e., remove the message from the queue once it has been successfully processed), you need to use the IBM MQ Java API. In this context, committing means acknowledging that you have successfully processed the message and that the message should be removed from the queue.

Here’s an example that demonstrates how to read (get) a message from an IBM MQ queue and commit the transaction using the MQ API:

Prerequisites:
IBM MQ Java Client Libraries: Ensure that the necessary IBM MQ client libraries (e.g., com.ibm.mq.allclient.jar) are included in your project.
MQ Environment Setup: Make sure your MQ server is running, and the necessary queues and queue manager are set up.

MQReadAndCommit.java

Explanation:
MQ Environment Configuration:

The MQEnvironment class is configured with the MQ server's hostname, port, and channel. These values must match the MQ server setup.
Open the Queue:

The queue is opened with MQOO_INPUT_AS_Q_DEF, which allows reading from the queue. The MQOO_OUTPUT flag is also used for the possibility of writing to the queue in the future.
Retrieve the Message:

A MQMessage object is created, and the message is retrieved from the queue using queue.get().
The MQGetMessageOptions is configured with a wait time (5000 milliseconds, or 5 seconds) and options like MQGMO_WAIT to wait for a message to arrive.
Commit the Transaction:

After successfully retrieving and processing the message, queueManager.commit() is called to commit the transaction. This will remove the message from the queue permanently.
Rollback on Error:

If an exception occurs during message retrieval or processing, the transaction is rolled back using queueManager.backout(). This will prevent any incomplete message processing from being committed.
Cleanup:

The queue is closed using queue.close(), and the queueManager.disconnect() method is called to disconnect from the MQ queue manager.
Key Points:
Commit: In MQ, commit means that the transaction is finalized, and the message is removed from the queue.
Rollback: If there’s an error, backout() ensures the message is not removed from the queue.
Message Options: The MQGetMessageOptions (gmo) specifies how the message should be retrieved (e.g., wait for a message, convert message encoding).
Prerequisites to Make This Work:
Ensure that your MQ queue manager and queue are properly configured.
Ensure that the QM1 queue manager and MYQUEUE queue exist (replace with your own names if needed).
Make sure the appropriate permissions are set for the user or application making the connection to IBM MQ.
This example assumes that the MQ client is installed and set up correctly, and your MQ server is accessible from your Java application.
