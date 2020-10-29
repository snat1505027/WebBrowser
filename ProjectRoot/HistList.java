/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webbrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.table.TableColumn;
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
import static webbrowser.Pooh.inI;

/**
 *
 * @author Nahida
 */
public class HistList extends JPanel {

    HashMap<String, Object[][]> dataList = new HashMap<>();
    JTable table = new JTable();
    Object[][] data;
    public static ArrayList<String> dateList;
    public static ArrayList<String> timeList;
    public static ArrayList<String> namelist;
    public static HashMap<String, Integer> repeatCount;
    public static ArrayList<String> locTable;
    public static ArrayList<Integer> count;
    public ArrayList<Integer> countArr;
    JMenuItem remove = new JMenuItem("Remove");
    JMenuItem selectAll = new JMenuItem("Select All");
    JScrollPane scrollPane = new JScrollPane();
    MyTableModel model = new MyTableModel();
    int i = 0;
    Hello hello;
    public static ArrayList<Boolean> checkBox = new ArrayList<>();

    HistList(ArrayList dateList, ArrayList timeList, ArrayList namelist, ArrayList locTable, HashMap repeatCount) {
        try {
            //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        count = new ArrayList<>();
        this.dateList = dateList;
        this.locTable = locTable;
        this.namelist = namelist;
        this.timeList = timeList;
        this.repeatCount = repeatCount;
        String[] title = {" ", "Time", "Name", "Location"};
        for (int i = 0; i < this.dateList.size(); i++) {
            data = new Object[this.repeatCount.get(this.dateList.get(i))][4];
            int start = (i > 0) ? this.repeatCount.get(this.dateList.get(i - 1)) : 0;
            //int length = (i > 0) ? (this.repeatCount.get(this.dateList.get(i - 1)) + data.length - 1) : data.length;
            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k == 0) {
                        data[j][k] = false;
                    } else if (k == 1) {
                        data[j][k] = this.timeList.get(start);
                    } else if (k == 2) {
                        data[j][k] = this.namelist.get(start);
                    } else if (k == 3) {
                        data[j][k] = this.locTable.get(start);
                    }
                }
                start++;
            }
            dataList.put(this.dateList.get(i), data);
        }
        data = dataList.get(this.dateList.get(0));
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
        TableColumn column = this.table.getColumnModel().getColumn(3);
        column.setPreferredWidth(260);
        Font f = new Font("Helvetica", Font.PLAIN, 14);
        this.table.getTableHeader().setFont(new Font("Monaco", Font.PLAIN, 15));
        this.table.setFont(new Font("Helvetica Neue", Font.PLAIN, 12));
        this.table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        this.scrollPane = new JScrollPane(this.table);
        Dimension size = new Dimension(510, 380);
        this.scrollPane.setPreferredSize(size);
        //   this.scrollPane.setMinimumSize(size);
        this.scrollPane.getViewport().setBackground(Color.WHITE);
        JTextField name = new JTextField(50);
        name.setPreferredSize(new Dimension(scrollPane.getWidth(), 25));
        JTextField locationText = new JTextField(45);
        locationText.setPreferredSize(new Dimension(scrollPane.getWidth(), 25));
        JPanel textPane = new JPanel();
        JLabel nameLabel = new JLabel("Name:");
        JPanel textPane1 = new JPanel();
        textPane1.setLayout(new BoxLayout(textPane1, BoxLayout.X_AXIS));
        textPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        textPane1.add(Box.createHorizontalGlue());
        textPane1.add(nameLabel);
        textPane1.add(Box.createRigidArea(new Dimension(18, 0)));
        textPane1.add(name);
        JLabel locationLabel = new JLabel("Location:");
        JPanel textPane2 = new JPanel();
        textPane2.setLayout(new BoxLayout(textPane2, BoxLayout.X_AXIS));
        textPane2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        textPane2.add(Box.createHorizontalGlue());
        textPane2.add(locationLabel);
        textPane2.add(Box.createRigidArea(new Dimension(5, 0)));
        textPane2.add(locationText);
        textPane.setLayout(new BoxLayout(textPane, BoxLayout.Y_AXIS));
        textPane.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
        textPane.add(Box.createVerticalGlue());
        textPane.add(textPane1);
        textPane.add(Box.createRigidArea(new Dimension(0, 5)));
        textPane.add(textPane2);
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
        contentPane.add(scrollPane);
        contentPane.setBorder(BorderFactory.createEmptyBorder(0, 2, 2, 2));
        contentPane.add(Box.createVerticalGlue());
        contentPane.add(Box.createRigidArea(new Dimension(0, 5)));
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JTable target = (JTable) e.getSource();
                    int row = target.getSelectedRow();
                    int column = target.getSelectedColumn();
                    name.setText((String) data[row][2]);
                    locationText.setText((String) data[row][3]);
                }
                if (e.getClickCount() == 2) {
                    try {
                        JTable target = (JTable) e.getSource();
                        int row = target.getSelectedRow();
                        String url = (String) data[row][3];
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
        contentPane.add(textPane);
        JPanel pane = new JPanel(new BorderLayout());
        pane.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
        pane.setBackground(Color.WHITE);
        String[] dates = new String[this.dateList.size()];
        dates = (String[]) dateList.toArray(dates);
        JList list;
        list = new JList(dates);
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    JList target = (JList) e.getSource();
                    String s = (String) target.getSelectedValue();
                    data = dataList.get(s.toString());
                    checkBox.removeAll(checkBox);
                    remove.setEnabled(false);
                    if (model.getRowCount() > 0) {
                        for (int i = model.getRowCount() - 1; i > -1; i--) {
                            model.removeRow(i);
                        }
                    }
                    for (int i = 0; i < data.length; i++) {
                        model.addRow(data[i]);
                        checkBox.add(false);
                    }
                    model.fireTableDataChanged();

                }
            }
        });
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setCellRenderer(new HistListRenderer());
        ImageIcon imageIcon = new ImageIcon(Hello.class.getResource("/rsz_ic_arrow_drop_down_48px-128.png"));
        JLabel label = new JLabel("Browsing Date", imageIcon, JLabel.LEFT);
        pane.add(label, BorderLayout.NORTH);
        pane.add(list, BorderLayout.WEST);
        pane.setPreferredSize(new Dimension(150, 380));
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pane, contentPane);
        JMenuBar organize = new JMenuBar();
        //organize.setOpaque(true);
        //organize.setBackground(getBackground());
        JMenu menu = new JMenu("Organize");
        menu.add(remove);
        menu.add(selectAll);
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("in select all");
                checkBox.clear();
                for (int i = 0; i < model.getRowCount(); i++) {
                    model.setValueAt(true, i, 0);
                }
                table.revalidate();
                table.validate();
            }
        });
        selectAll.setEnabled(true);
        remove.setEnabled(false);
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int num = 0;
                for (int j = model.getRowCount() - 1; j >= 0; j--) {
                    System.out.println(model.getValueAt(j, 2));
                    System.out.println(model.getRowCount());
                    if (checkBox.get(j)) {
                        count.add(j);
                        System.out.println("in remove");
                        System.out.println("prev "+checkBox.toString());
                        System.out.println(dateList.contains(list.getSelectedValue()));
                        //repeatCount.put(, j);
                        model.removeRow(j);
                        timeList.remove(j);
                        namelist.remove(j);
                        locTable.remove(j);
                        System.out.println("after"+checkBox.toString());
                        try {
                            removeFromFile(j, num);
                            num++;
                        } catch (IOException ex) {
                            Logger.getLogger(HistList.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        model.fireTableDataChanged();
                    }
                    //removeFromFile();
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
        finale.add(splitPane, BorderLayout.CENTER);
        //setOpaque(true);
        add(finale);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("History");
        //frame.setJMenuBar(organize);
        frame.setIconImage(new ImageIcon(Hello.class.getResource("/Panda-icon.png")).getImage());
        //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        HistList newContentPane = new HistList(dateList, timeList, namelist, locTable, repeatCount);
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public class MyTableModel extends DefaultTableModel {

        public MyTableModel() {
            super(new String[]{" ", "Time", "Name", "Location"}, 0);
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            try {
                UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            }
            Class clazz = String.class;
            switch (columnIndex) {
//        case 0:
//          clazz = Integer.class;
//          break;
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

    public void removeFromFile(int pos, int num) throws IOException {

        File file1 = new File("History.txt");
        file1.delete();
        File file = new File("History.txt");
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        try {
            for (int i = 0; i < dateList.size(); i++) {
                bw.write(dateList.get(i));
                bw.newLine();
                int start = (i > 0) ? this.repeatCount.get(this.dateList.get(i - 1)) : 0;
                for (int j = start; j < this.repeatCount.get(this.dateList.get(i)) + start - num; j++) {
                    if (j == pos) {
                        System.out.println("position: " + pos);
                    } else {
                        System.out.println(timeList.get(j) + " " + locTable.get(j) + " " + namelist.get(j));
                        bw.write(timeList.get(j) + " " + locTable.get(j) + " " + namelist.get(j));
                        bw.newLine();
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(HistList.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException ignore) {
                }
            }
        }
    }

    public void refresh() {
        dataList.clear();
        for (int i = 0; i < this.dateList.size(); i++) {
            Object[][] data = new Object[this.repeatCount.get(this.dateList.get(i))][4];
            int start = (i > 0) ? this.repeatCount.get(this.dateList.get(i - 1)) : 0;
            int length = (i > 0) ? (this.repeatCount.get(this.dateList.get(i - 1)) + data.length - 1) : data.length;
            for (int j = 0; j < data.length; j++) {
                for (int k = 0; k < 4; k++) {
                    if (k == 0) {
                        data[j][k] = false;
                    } else if (k == 1) {
                        data[j][k] = this.timeList.get(start);
                    } else if (k == 2) {
                        data[j][k] = this.namelist.get(start);
                    } else if (k == 3) {
                        data[j][k] = this.locTable.get(start);
                    }
                }
                start++;
            }
            dataList.put(this.dateList.get(i), data);
        }
    }

    class HistListRenderer extends DefaultListCellRenderer {

        Font font = new Font("helvitica", Font.ITALIC, 15);

        public Component getListCellRendererComponent(JList list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            label.setIcon(new ImageIcon(Hello.class.getResource("/rsz_icon-arrow-down.png")));
            label.setHorizontalTextPosition(JLabel.RIGHT);
            label.setFont(font);
            return label;
        }
    }
}
