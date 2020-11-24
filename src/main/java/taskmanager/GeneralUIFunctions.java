package taskmanager;


import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author code copied from https://stackoverflow.com/questions/6495769/how-to-get-all-elements-inside-a-jframe
 * access date: 10/12/2020
 */
public class GeneralUIFunctions {
    
    @NotNull
    public static List<Component> getAllComponents(@NotNull final Container c) {
        Component[] comps = c.getComponents();
        List<Component> compList = new ArrayList<>();
        for (Component comp : comps) {
            compList.add(comp);
            if (comp instanceof Container)
                compList.addAll(getAllComponents((Container) comp));
        }
        return compList;
    }
    
}
