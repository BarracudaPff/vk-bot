import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Map;
import java.util.SortedSet;
import javax.swing.*;
import javax.swing.table.TableColumn;

public class GUI extends JFrame {

    private JButton button = new JButton("Choose file");
    private JLabel label = new JLabel("File: none");
    private File file;

    private JPanel p;

    private JButton buttonAnalysis = new JButton("Analysis");

    private JTextField input = new JTextField("", 5);
    private JCheckBox check = new JCheckBox("Check", false);

    private JTextArea textArea = new JTextArea();
    private JScrollPane scrollPane = new JScrollPane();
    private JTable table;

    public GUI() {
        super("Simple Example");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 1000);
        this.setVisible(true);

        // создаем панель.
        p = new JPanel();
        this.add(p);

        // к панели добавляем менеджер BorderLayout.
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));

        button.setSize(100, 100);
        buttonAnalysis.setSize(100, 100);
        p.add(button);
        p.add(buttonAnalysis);
        p.add(label);
        button.addActionListener(new ButtonEventListener());
        buttonAnalysis.addActionListener(new ButtonEventListenerAnalysis());

        textArea = new JTextArea();
        textArea.setSize(500, 800);
        textArea.setText("Results here");
        JScrollPane taScroll = new JScrollPane(textArea); // Adds the scrolls when there are too much text.

        p.add(taScroll); // You should add the scroll pane now, not the text area.

        table = new JTable();
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileopen = new JFileChooser();
            int ret = fileopen.showDialog(null, "Открыть файл");
            if (ret == JFileChooser.APPROVE_OPTION) {
                file = fileopen.getSelectedFile();
                label.setText("File: " + file.getName() + "");
            }
        }
    }

    class ButtonEventListenerAnalysis implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (file == null) {
                textArea.setText("No file");
                return;
            }
            String[] columnNames = {
                    "Word",
                    "Count",
            };
            textArea.setText(Main.exec(file.getPath()));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI app = new GUI();
            app.setVisible(true);
        });
    }
}