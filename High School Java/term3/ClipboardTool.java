package term3.term3;
// CAUTION no longer works, was made in 2015/2016, its 2022 at Java 18....
/**
 * @kali purpose is to prevent looking a recently copied link, address or phone number
 * known issue, cant copy and paste, or add notes in the text area because "frame.requestFocusInWindow();"
 * press P or HOME to save text to documents.
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

public class ClipboardTool {

    private static final JFrame frame = new JFrame("ClipboardManager");
    private static final JTextArea Tarea = new JTextArea(); // tarea = test area
    private static final JScrollPane scroll = new JScrollPane(Tarea);
    private static final Clipboard CB = Toolkit.getDefaultToolkit().getSystemClipboard();
    private static Transferable transfer = CB.getContents(frame);

    private static String currentCB = "starting program...";
    private static String lastCB = "";

    public static void main(String[] args) {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        try {
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        final ActionListener print_listener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("print listener triggered*");
                String path = System.getProperty("user.home") + File.separator + "Documents";
                File customDir = new File(path);
                PrintWriter out;
                try {
                    out = new PrintWriter(new BufferedWriter(new FileWriter(customDir + "/ClipboardTool save file.txt")));
                    // out = new PrintWriter(new BufferedWriter(new
                    // FileWriter("/Users/myself/Desktop/area1.txt")));
                    out.write(Tarea.getText());

                    out.close();
                } catch (IOException e1) {
                    // e1.printStackTrace();
                    System.out.println("e1 printed ^^^");
                }

            }
        };
        final JButton print = new JButton();
        print.addActionListener(print_listener);

        Tarea.setBackground(Color.LIGHT_GRAY);
        Tarea.setFont(new Font("Ubuntu", Font.PLAIN, 16));
        Tarea.setLineWrap(false);
        Tarea.setWrapStyleWord(true); // no effect
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_HOME) {
                    print_listener.actionPerformed(new ActionEvent(print, 99990, null));
                    System.out.println("saved*");
                }
            }
        });
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
        frame.setSize(500, 800);
        frame.setLocationRelativeTo(null); // was not working property
        frame.add(scroll);
        // frame.setEnabled(false);
        frame.setVisible(true);

        frame.requestFocusInWindow();

        try {
            // CB = Toolkit.getDefaultToolkit().getSystemClipboard();
            transfer = CB.getContents(frame);
            currentCB = (String) transfer.getTransferData(DataFlavor.stringFlavor);
        } catch (Exception ex) { // if error, sleep for 500Ms
            try {
                Thread.sleep(500);
            } catch (Exception ex2) {
            }
        }
        /**
         * ~10 above lines, i needed help for. & "!currentCB.equals(lastCB)"
         * , because i was doing != and assuming i am correct because no
         * error
         *
         */
        if (!currentCB.equals(lastCB)) // if there is a change in the
        // clipboard
        {
            // paste();

            try {
                Tarea.append(transfer.getTransferData(DataFlavor.stringFlavor) + "\n");
                System.out.println("current clipboard in text area: " + currentCB);
            } catch (Exception err) {
            }

            lastCB = currentCB;
            // Calendar cal = Calendar.getInstance(); no longer needed since
            // i added this to the line below
            Tarea.append("copied on: " + dateFormat.format(Calendar.getInstance().getTime()) + "\n");
        }
        // System.out.println("currentCB: " + currentCB);
        // System.out.println("lastCB: " + lastCB);

        try {
            Thread.sleep(500);
        } catch (Exception ex) {
        }

    }

    public static void paste() { // unused methord, first try making this app

        try {
            Tarea.append(transfer.getTransferData(DataFlavor.stringFlavor) + "\n");
            currentCB = (String) transfer.getTransferData(DataFlavor.stringFlavor);
            System.out.println("current clipboard in the paste metord:" + currentCB);
        } catch (Exception err) {
            err.printStackTrace();
            System.out.println("end of stack trace");
        }
        lastCB = currentCB;

    }

}

/**
 *
 *
 * if (numb == "") { str_copy = Double.toString(x); } else { str_copy = numb; }
 *
 * StringSelection sele = new StringSelection(str_copy);
 *
 * CB.setContents(sele, sele); System.out.println("clipboard is: " + CB);
 *
 *
 *
 *
 *
 *
 *
 */
/**


 */
