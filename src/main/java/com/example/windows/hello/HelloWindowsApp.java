package com.example.windows.hello;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.jeta.forms.components.panel.FormPanel;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.UUID;

@Slf4j
public class HelloWindowsApp {

    public static void main(String[] args) {

        new HelloWindowsApp().run();

    }

    private void run() {

        // simple little Swing app with a dummy UI for example purposes

        SwingUtilities.invokeLater(() -> {

            log.info("the app is starting up");

            JFrame.setDefaultLookAndFeelDecorated(true);

            JFrame mainWindow = new JFrame("Hello Windows!");
            mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
            mainWindow.setResizable(true);

            mainWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                log.warn("app is shutting down");
                }
            });

            JMenuBar menuBar = new JMenuBar();

            JMenuItem quit = new JMenuItem("Quit");

            JMenu fileMenu = new JMenu("File");
            fileMenu.add(quit);

            menuBar.add(fileMenu);

            mainWindow.setJMenuBar(menuBar);

            FormPanel mainPanel = new FormPanel("helloWindowsForm.jfrm");
            JTextField nameTextField = mainPanel.getTextField("nameTextField");
            JButton goButton = (JButton) mainPanel.getButton("goButton");
            goButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    nameTextField.setText(UUID.randomUUID().toString());
                }
            });

            mainWindow.add(mainPanel);

            mainWindow.pack();
            mainWindow.setVisible(true);

            log.info("application has started without issue");

        });

    }

}
