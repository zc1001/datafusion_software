package com.control;

import javax.swing.*;
import java.awt.*;

public class Imgs {
    public static ImageIcon getImage(String filename){
        return new ImageIcon(Toolkit.getDefaultToolkit().getImage(Imgs.class.getResource(filename)));
    }
}
