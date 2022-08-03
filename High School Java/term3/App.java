package term3.term3;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        JFrame fr = new JFrame("Notepad");
        JMenuBar bar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem saveFile = new JMenuItem("save"); // does actually save the file or do anything else
        JMenuItem close = new JMenuItem("close");

        // SwingUtilities.invokeLater(new Runnable() { public void run() {

        JTextArea text = new JTextArea();
        text.setBackground(Color.WHITE);
        text.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.black));
        text.setFont(new Font("Century Gothic", Font.PLAIN, 14));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);

        file.add(saveFile);
        file.add(close);
        bar.add(file);
        fr.setJMenuBar(bar);
        fr.setSize(500, 400);
        fr.setLocationRelativeTo(null);
        fr.getContentPane().add(scroll); // text -> scroll -> fr
        fr.setVisible(true);

        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
