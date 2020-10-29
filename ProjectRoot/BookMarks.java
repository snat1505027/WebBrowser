
package webbrowser;

import static com.alee.laf.filechooser.FileChooserViewType.table;
import static com.alee.managers.style.SupportedComponent.scrollPane;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import static webbrowser.HistList.checkBox;
import static webbrowser.HistList.count;
import static webbrowser.HistList.dateList;
import static webbrowser.HistList.locTable;
import static webbrowser.HistList.namelist;
import static webbrowser.HistList.repeatCount;
import static webbrowser.HistList.timeList;
import static webbrowser.Pooh.inI;

/**
 *
 * @author Nahida
 */
public class BookMarks implements ActionListener {

    static Pooh pooh;
    String title;
    String titleUrl;
    FileWriter fw;
    FileReader fr;
    BufferedReader br;
    BufferedWriter bw;
    Hello hello;
    static int i = 0;
    FavIcon favIcon;
    static final HashMap<String, String> listHist = new HashMap<>();
    static final HashMap<String, JButton> labelList = new HashMap<>();
    static JPanel finalPane = new JPanel(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    FlowLayout experimentLayout = new FlowLayout();
    private JTable table;
    private JScrollPane scrollPane;
    JMenuItem remove = new JMenuItem("Remove BookMark");

    public BookMarks() throws FileNotFoundException, IOException {
        ChildPanel.bookMarks = this;
        finalPane.setBackground(Color.decode("#330300"));
        finalPane.setLayout(experimentLayout);
        File file = new File("BookMark.txt");
        if (!file.exists()) {
            System.out.println("file doesn't exist");
        } else {
            fr = new FileReader(file);
            br = new BufferedReader(fr);
            while (true) {
                String s = br.readLine();
                if (s == null) {
                    break;
                }
                StringTokenizer st = new StringTokenizer(s);
                StringBuilder text = new StringBuilder();
                String url = st.nextToken();
                while (st.hasMoreTokens()) {
                    text.append(st.nextToken() + " ");
                }

                //gbc.fill = GridBagConstraints.HORIZONTAL;
                String textTo = text.toString().trim();
                if (listHist.get(textTo) == null) {
                    listHist.put(textTo, url);
                    JButton label = new JButton(textTo);
                    favIcon = new FavIcon(url);
                    if (favIcon.getIcon() != null) {
                        label.setIcon(favIcon.getIcon());
                    }
                    label.setPreferredSize(new Dimension(100, 20));
                    //label.setOpaque(true);
                    label.setBorderPainted(false);
                    //label.setRolloverEnabled(true);
                    label.setBackground(Color.pink.darker().darker().darker());
                    label.addActionListener(this);
                    labelList.put(textTo, label);
                    finalPane.add(label);
                    finalPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                    i++;
                }
            }
            br.close();
            fr.close();
        }
    }

    public void addBookMarks(String titleUrl, String title) throws IOException {
        finalPane.setBackground(Color.decode("#330300"));
        finalPane.setLayout(experimentLayout);
        File file = new File("BookMark.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        fr = new FileReader(file);
        br = new BufferedReader(fr);
        fw = new FileWriter(file.getAbsoluteFile(), true);
        bw = new BufferedWriter(fw);
        try {
            this.title = title.trim();
            this.titleUrl = titleUrl;
            if (listHist.get(this.title) == null) {
                bw.write(this.titleUrl + " " + this.title);
                bw.newLine();
                listHist.put(this.title, this.titleUrl);
                JButton label = new JButton(this.title);
                favIcon = new FavIcon(titleUrl);
                if (favIcon.getIcon() != null) {
                    label.setIcon(favIcon.getIcon());
                }
                label.setPreferredSize(new Dimension(100, 20));
                //label.setOpaque(false);
                label.setBorderPainted(false);
                //label.setRolloverEnabled(true);
                label.setBackground(Color.pink.darker().darker().darker());
                label.addActionListener(this);
                finalPane.add(label);
                finalPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
                i++;
            }
            for (String key : listHist.keySet()) {
                System.out.println(key);
            }
        } catch (IOException ex) {
            Logger.getLogger(BookMarks.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            try {

                if (bw != null) {
                    bw.close();
                }

                if (fw != null) {
                    fw.close();
                }

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String url = listHist.get(ae.getActionCommand());
        if (BookMarks.pooh.tb.getSelectedIndex() == 0) {
            BookMarks.pooh.actionGo(url);
            System.out.println("go in book");
        } else {
            BookMarks.pooh.listTab.get(BookMarks.pooh.tb.getSelectedIndex()).actionGo(url);
        }
    }

    public void showFile() {
        try {
            //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        MyTableModel model = new MyTableModel();
        String[] title = {" ", "Name", "Location"};
        Object[][] data = new Object[listHist.size()][3];
        int j = 0;
        for (Map.Entry<String, String> entry : listHist.entrySet()) {
            for (int k = 0; k < 4; k++) {
                if (k == 0) {
                    data[j][k] = false;
                } else if (k == 1) {
                    data[j][k] = entry.getKey();
                    System.out.println(entry.getKey());
                } else if (k == 2) {
                    data[j][k] = entry.getValue();
                }
            }
            j++;
        }
        for (int i = 0; i < data.length; i++) {
            model.addRow(data[i]);
            checkBox.add(false);
        }
        this.table = new JTable(model);
        this.table.setShowGrid(false);
        TableColumn column1 = this.table.getColumnModel().getColumn(0);
        column1.setPreferredWidth(10);
        TableColumn column2 = this.table.getColumnModel().getColumn(1);
        column1.setPreferredWidth(20);
        Font f = new Font("Helvetica", Font.PLAIN, 14);
        this.table.getTableHeader().setFont(new Font("Monaco", Font.PLAIN, 15));
        this.table.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        this.scrollPane = new JScrollPane(this.table);
        Dimension size = new Dimension(510, 380);
        this.scrollPane.setPreferredSize(size);
        //   this.scrollPane.setMinimumSize(size);
        this.scrollPane.getViewport().setBackground(Color.WHITE);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(scrollPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
        contentPane.add(Box.createVerticalGlue());
        contentPane.add(Box.createRigidArea(new Dimension(0, 5)));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        JTable target = (JTable) e.getSource();
                        int row = target.getSelectedRow();
                        String url = (String) data[row][2];
                        hello = new Hello();
                        hello.actionGo(url);
                        ChildPanel.pooh.listTab.put(inI + 1, hello);
                        JPanel panel = new JPanel(new BorderLayout());
                        panel.add(hello.getPanel(), BorderLayout.CENTER);
                        hello.getJfxpanel().addMouseMotionListener(ChildPanel.pooh);
                        panel.add(ChildPanel.pooh.statusBar, BorderLayout.WEST);
                        ChildPanel.pooh.listPanel.put(inI + 1, panel);
                        ChildPanel.pooh.tb.addTab("New Tab", panel);
                        ChildPanel.pooh.tb.setSelectedIndex(ChildPanel.pooh.inI + 1);
                        ChildPanel.pooh.locationTextField.setText("");
                        ChildPanel.pooh.locationTextField.requestFocusInWindow();
                        ChildPanel.pooh.initTabComponent(inI + 1);
                        ChildPanel.pooh.inI++;
                    } catch (IOException ex) {
                        Logger.getLogger(HistList.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        //contentPane.add(buttonPane, BorderLayout.PAGE_END);
        JMenuBar organize = new JMenuBar();
        //organize.setOpaque(true);
        //organize.setBackground(getBackground());
        JMenu menu = new JMenu("Organize");
        menu.add(remove);
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int num = 0;
                for (int j = model.getRowCount() - 1; j >= 0; j--) {
                    System.out.println(model.getValueAt(j, 2));
                    System.out.println(model.getRowCount());
                    System.out.println("prev listHist "+listHist.size());
                    if (checkBox.get(j)) {
                        System.out.println("in remove");
                        JButton button = labelList.get(model.getValueAt(j, 1));
                        listHist.remove(model.getValueAt(j, 1), model.getValueAt(j, 2));
                        finalPane.remove(button);
                        finalPane.revalidate();
                        finalPane.validate();
                        System.out.println("listHist "+listHist.size());
                        model.removeRow(j);
                        model.fireTableDataChanged();
                    }
                }
                try {
                    removeFromFile();
                } catch (IOException ex) {
                    Logger.getLogger(BookMarks.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        //menu.setOpaque(true);
        //menu.setBackground(getBackground());
        menu.setIcon(new ImageIcon(Hello.class.getResource("/rsz_todo-512.png")));
        organize.add(menu);

        JPanel finale = new JPanel(new BorderLayout());
        //finale.setOpaque(true);
        finale.add(organize, BorderLayout.NORTH);
        finale.add(contentPane, BorderLayout.CENTER);
        finale.setOpaque(true);
        JFrame frame = new JFrame("BookMark");
        frame.setPreferredSize(new Dimension(350, 250));
        frame.setIconImage(new ImageIcon(Hello.class.getResource("/Panda-icon.png")).getImage());
        frame.setContentPane(finale);
        frame.pack();
        frame.setVisible(true);

    }

    public void removeFromFile() throws IOException {

        File file1 = new File("BookMark.txt");
        file1.delete();
        File file = new File("BookMark.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            for (Map.Entry<String, String> entry : listHist.entrySet()) {
            bw.write(entry.getValue()+" "+entry.getKey());
            bw.newLine();
        }
        }
    catch (IOException ex

    
        ) {
            Logger.getLogger(HistList.class.getName()).log(Level.SEVERE, null, ex);
    }

    
        finally {
            if (bw != null) {
            try {
                bw.close();
            } catch (IOException ignore) {
            }
        }
    }
}

public class MyTableModel extends DefaultTableModel {

    public MyTableModel() {
        super(new String[]{" ", "Name", "Location"}, 0);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
        }
        Class clazz = String.class;
        switch (columnIndex) {
            case 0:
                clazz = Boolean.class;
                break;
        }
        return clazz;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 0;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (aValue instanceof Boolean && column == 0) {

            Vector rowData = (Vector) getDataVector().get(row);
            rowData.set(0, (boolean) aValue);
            if (checkBox.size() > row) {
                checkBox.remove(row);
            }
            checkBox.add(row, (Boolean) aValue);
            System.out.println(row + " " + checkBox.toString());
            if (checkBox.contains(true)) {
                remove.setEnabled(true);
            } else {
                remove.setEnabled(false);
            }
        }
    }

}

}
