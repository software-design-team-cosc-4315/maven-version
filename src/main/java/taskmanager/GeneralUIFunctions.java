/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taskmanager;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author code copied from https://stackoverflow.com/questions/6495769/how-to-get-all-elements-inside-a-jframe
 * access date: 10/12/2020
 */
public class GeneralUIFunctions {
    
    public static List<Component> getAllComponents(final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<Component>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }
    
}
