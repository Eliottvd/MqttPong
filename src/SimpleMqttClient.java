import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.paho.client.mqttv3.*;

public class SimpleMqttClient implements MqttCallback {

  MqttClient myClient;
	MqttConnectOptions connOpt;

	static final String BROKER_URL = "tcp://192.168.0.42:1883" ;
	static final String M2MIO_USERNAME = "<m2m.io username>";
	static final String M2MIO_PASSWORD_MD5 = "<m2m.io password (MD5 sum of password)>";

	// the following two flags control whether this example is a publisher, a subscriber or both
	static final Boolean subscriber = true;
	static final Boolean publisher = false;

	/**
	 * 
	 * connectionLost
	 * This callback is invoked upon losing the MQTT connection.
	 * 
	 */
	@Override
	public void connectionLost(Throwable t) {
		System.out.println("Connection lost!");
                t.printStackTrace();
                
	}

	/**
	 * 
	 * deliveryComplete
	 * This callback is invoked when a message published by this client
	 * is successfully received by the broker.
	 * 
	 */
	//@Override
	public void deliveryComplete(MqttDeliveryToken token) {
		//System.out.println("Pub complete" + new String(token.getMessage().getPayload()));
	}

	/**
	 * 
	 * messageArrived
	 * This callback is invoked when a message is received on a subscribed topic.
	 * 
	 */
	//@Override
	public void messageArrived(MqttTopic topic, MqttMessage message) throws Exception {
		System.out.println("-------------------------------------------------");
		System.out.println("| Topic:" + topic.getName());
		System.out.println("| Message: " + new String(message.getPayload()));
		System.out.println("-------------------------------------------------");
	}

	/**
	 * 
	 * MAIN
	 * 
	 */
	public static void main(String[] args) {
		SimpleMqttClient smc = new SimpleMqttClient();
		smc.runClient();
	}
	
	/**
	 * 
	 * runClient
	 * The main functionality of this simple example.
	 * Create a MQTT client, connect to broker, pub/sub, disconnect.
	 * 
	 */
	public void runClient() {
		// setup MQTT Client
                Connexion();

		// setup topic
		String myTopic = "yolo/swag";
		MqttTopic topic = myClient.getTopic(myTopic);

		// subscribe to topic if subscriber
		if (subscriber) {
			try {
				int subQoS = 0;
				myClient.subscribe(myTopic, subQoS);
                                System.out.println("SUBSCRIBED");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// publish messages if publisher
		if (publisher) {
                    for (int i=1; i<=10; i++) {
                        String pubMsg = "b{\"pubmsg\":" + i + "}";
                        System.out.println("Nouveau msg cree : " + pubMsg);
                        int pubQoS = 0;
                        MqttMessage message = new MqttMessage(pubMsg.getBytes());
                        message.setQos(pubQoS);
                        message.setRetained(false);

                        // Publish the message
                        System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
                        MqttDeliveryToken token = null;
                        try {
                        // publish message to broker
                           
                            //token = topic.publish(message);
                            myClient.publish("yolo/swag",message);
                            Thread.sleep(1500);
                        // Wait until the message has been delivered to the broker
                                //token.waitForCompletion();
                        } catch (Exception e) {
                                e.printStackTrace();
                        }
                    }			
		}
                
                while(true);
		
		// disconnect
		/*try {
			// wait to ensure subscribed messages are delivered
			if (subscriber) {
				Thread.sleep(5000);
			}
			myClient.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}

    @Override
    public void messageArrived(String topic, MqttMessage mm) throws Exception {
        System.out.println("messageArrived");
        System.out.println("message : "+mm.toString());
// throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken imdt) {
        System.out.println("DeliveryComplete");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public int Connexion()
    {
        
       String clientID = MqttClient.generateClientId();
        connOpt = new MqttConnectOptions();

        connOpt.setCleanSession(true);
        //connOpt.setKeepAliveInterval(15);
        //connOpt.setConnectionTimeout(30);
        connOpt.setUserName("babybaby");
        connOpt.setPassword("wooooh".toCharArray());
        connOpt.setAutomaticReconnect(true);

        // Connect to Broker
        try {
                myClient = new MqttClient(BROKER_URL, clientID);
                myClient.setCallback(this);
                myClient.connect(connOpt);
        } catch (MqttException e) {
                e.printStackTrace();
                System.exit(-1);
        }

        System.out.println("Connected to " + BROKER_URL);
                
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(SimpleMqttClient.class.getName()).log(Level.SEVERE, null, ex);
        }

          return 1;
    }
}