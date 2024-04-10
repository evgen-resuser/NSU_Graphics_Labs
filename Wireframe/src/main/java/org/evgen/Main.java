package org.evgen;

import org.evgen.editor.view.EditorMainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

//        try {
//            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//        } catch (Exception e) {
//            return;
//        }

        new EditorMainFrame();
    }
}