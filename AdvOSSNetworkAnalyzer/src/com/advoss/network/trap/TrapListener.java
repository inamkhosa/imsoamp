package com.advoss.network.trap;

import com.advoss.network.analyzer.gui.FrmNetworkAnalyzer;
import com.advoss.util.CommonFunctions;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.jms.JMSException;
import org.ict.oamp.service.client.Trap;

public class TrapListener implements MessageListener {

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            TextMessage text = (TextMessage) message;
            try {
                System.out.println("Received: " + text.getText());
                Trap trap = CommonFunctions.generateTrapObject(text.getText());
                if(trap != null) {
                    FrmNetworkAnalyzer.getInstance().addTrap(trap);
                }
            } catch (JMSException exception) {
                System.err.println("Failed to get message text: " + exception);
            }
        }
    }


}
