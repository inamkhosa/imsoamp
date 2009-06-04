/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.network.trap;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Properties;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Alam Sher
 */
public class TrapReceiver {
    private static TrapReceiver trapReceiver = null;
    private Context context = null;
    private ConnectionFactory factory = null;
    private Connection connection = null;
    private String factoryName = "ConnectionFactory";
    private String destinationName = "oamp_traps_topic";
    private Destination destination = null;
    private Session session = null;
    private MessageConsumer receiver = null;
    private Exception exception = null;

    private TrapReceiver() {
    }

    public static TrapReceiver getInstance() {
        if(trapReceiver == null) {
            trapReceiver = new TrapReceiver();
        }
        return trapReceiver;
    }
    public boolean initialize() {
        try {
            Properties props = new Properties();
            props.load(getClass().getClassLoader().
                    getResourceAsStream("jndi.properties"));
            context = new InitialContext(props);
            factory = (ConnectionFactory) context.lookup(factoryName);
            destination = (Destination) context.lookup(destinationName);
            connection = factory.createConnection();
            session = connection.createSession(
                    false, Session.AUTO_ACKNOWLEDGE);
            receiver = session.createConsumer(destination);
            receiver.setMessageListener(new TrapListener());
            connection.start();
            System.out.println("Trap dispathcer initialized successfully...");
            exception = null;
            return true;
        } catch (NamingException namingException) {
            namingException.printStackTrace();
            exception = namingException;
        } catch (JMSException jMSException) {
            jMSException.printStackTrace();
            exception = jMSException;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            exception = ioe;
        }
        System.out.println("Trap Dispatcher could not be initialized...");
        return false;
    }

    public String getExceptionMessage() {
        if(exception != null) {
            return exception.getMessage();
        } else {
            return "Exception details not aviable...";
        }
    }

    public void stopReceiver() {
        try {
            session.close();
            connection.close();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
