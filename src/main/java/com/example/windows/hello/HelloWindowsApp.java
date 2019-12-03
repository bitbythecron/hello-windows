package com.example.windows.hello;

import com.jeta.forms.components.panel.FormPanel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileOutputStream;
import java.io.IOException;
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
                    String uuid = UUID.randomUUID().toString();
                    nameTextField.setText(uuid);

                    Workbook workbook = new XSSFWorkbook();
                    Sheet sheet = workbook.createSheet("SOME_SHEET");

                    Font headerFont = workbook.createFont();
                    headerFont.setBold(true);

                    CellStyle cellStyle = workbook.createCellStyle();
                    cellStyle.setFont(headerFont);
                    cellStyle.setFillBackgroundColor(IndexedColors.YELLOW.getIndex());
                    cellStyle.setAlignment(HorizontalAlignment.CENTER);

                    int rowNum = 0;

                    Row headerRow = sheet.createRow(rowNum);
                    headerRow.setRowStyle(cellStyle);

                    Cell partNumberHeaderCell = headerRow.createCell(0);
                    partNumberHeaderCell.setCellValue("Part #");
                    partNumberHeaderCell.setCellStyle(cellStyle);

                    Cell partDescriptionHeaderCell = headerRow.createCell(1);
                    partDescriptionHeaderCell.setCellStyle(cellStyle);
                    partDescriptionHeaderCell.setCellValue("Description");

                    Cell partPriceHeaderCell = headerRow.createCell(2);
                    partPriceHeaderCell.setCellStyle(cellStyle);
                    partPriceHeaderCell.setCellValue("Price");

                    Cell manufacturerHeaderCell = headerRow.createCell(3);
                    manufacturerHeaderCell.setCellStyle(cellStyle);
                    manufacturerHeaderCell.setCellValue("Make");

                    rowNum++;

                    Row nextRow = sheet.createRow(rowNum);

                    nextRow.createCell(0).setCellValue(uuid);
                    nextRow.createCell(1).setCellValue("Some Part");
                    nextRow.createCell(2).setCellValue(2.99);
                    nextRow.createCell(3).setCellValue("ACME");

                    FileOutputStream fos = null;
                    try {
                        fos = new FileOutputStream("acme.xlsx");

                        workbook.write(fos);

                        workbook.close();
                    } catch (IOException ex) {
                        log.error(ExceptionUtils.getStackTrace(ex));
                    }

                }
            });

            mainWindow.add(mainPanel);

            mainWindow.pack();
            mainWindow.setVisible(true);

            log.info("application has started without issue");

        });

    }

}
