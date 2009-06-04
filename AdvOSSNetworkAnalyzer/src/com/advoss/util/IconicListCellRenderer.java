/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advoss.util;

import com.advoss.network.analyzer.NetworkAnalyzer;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import org.ict.oamp.service.client.ElementManager;

/**
 *
 * @author Alam Sher
 */
public class IconicListCellRenderer extends JLabel implements ListCellRenderer {

    public IconicListCellRenderer() {
        setOpaque(true);
    }

    public Component getListCellRendererComponent(JList list,
            Object value,
            int index,
            boolean iss,
            boolean chf)
    {
        ElementManager element = (ElementManager) value;
        setText(element.getElementIdentifier());
        if (iss) {
            setBorder(BorderFactory.createLineBorder(
                    Color.black, 2));
        } else {
            setBorder(BorderFactory.createLineBorder(
                    list.getBackground(), 2));
        }
        try {
            Image iconImage = Toolkit.getDefaultToolkit().createImage(new URL(NetworkAnalyzer.getInstance().getServerBaseURL() + "/secure/icons/" + element.getCategory().getCategoryId() + ".bmp"));
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(iconImage, 1);
            tracker.waitForAll();
            setIcon(new ImageIcon(iconImage, "Icon"));
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return this;
    }
}
