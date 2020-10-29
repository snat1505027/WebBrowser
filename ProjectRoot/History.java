package webbrowser;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import javax.swing.text.StyledEditorKit;
import static webbrowser.Pooh.inI;

public class History implements ActionListener {

    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> namelist;
    Set<String> set = new HashSet<>();
    Set<String> dateSet ;
    Set<String> urlset;
    Hashtable<String, String> table;
    HashMap<String, String> histTable;
    ArrayList<String> locTable;
    HashMap<String, Integer> repeatCount ;
    public JPopupMenu menu ;
    ArrayList<String> dateList;
    ArrayList<String> timeList ;
    JMenuItem item;
    File file;
    FileReader fr;
    int val;
    String date;
    BufferedReader br;
    Hello hello;

    History(File file) throws FileNotFoundException {
        this.file = file;
        val = 0;
        dateSet = new HashSet<>();
        urlset = new HashSet<>();
        table = new Hashtable<>();
        histTable = new HashMap<>();
        locTable = new ArrayList<>();
        repeatCount = new HashMap<>();
        dateList = new ArrayList<>();
        timeList = new ArrayList<>();
        menu = new JPopupMenu();
        fr = new FileReader(file.getAbsoluteFile());
        br = new BufferedReader(fr);
    }

    public JPopupMenu create() throws IOException {
        Font myFont = new Font("Serif", Font.BOLD, 15);
        item = new JMenuItem("History");
        item.setFont(myFont);
        item.addActionListener(this);
        menu.add(item);
        menu.addSeparator();
        while (true) {
            String s = br.readLine();
            if (s == null) {
                break;
            }
            StringTokenizer st = new StringTokenizer(s);
            if (st.countTokens() == 1) {
                date = st.nextToken();
                if ((dateSet.size() > 0) && (!dateSet.contains(date.trim()))) {
                    System.out.println("dateSet : " + dateSet.size() + " " + val);
                    val = 0;
                }
                dateSet.add(date.trim());
            } else {
                String time = st.nextToken();
                //System.out.println(time);
                timeList.add(time);
                String url = st.nextToken();
                locTable.add(url);
                StringBuilder text = new StringBuilder();
                while (st.hasMoreTokens()) {
                    text.append(st.nextToken() + " ");
                }
                list.add(text.toString());
                set.add(text.toString());
                histTable.put(text.toString(), url);
                System.out.println(val + " in history survey " + time + " " + url + " " + text.toString());
                val++;
            }
            repeatCount.put(date, val);
        }
        fr.close();
        br.close();
        dateList = new ArrayList<String>(dateSet);
        namelist = new ArrayList<String>(set);
        for (Map.Entry<String, Integer> entry : repeatCount.entrySet()) {
            System.out.println("key: " + entry.getKey() + " value: "
                    + entry.getValue());
        }
        System.out.println(timeList.size() + " " + list.size() + " " + locTable.size() + " " + dateList.size() + " " + repeatCount.size());
        int size = (namelist.size() > 6) ? 6 : namelist.size();
        for (int i = 0; i < size; i++) {
            item = new JMenuItem(namelist.get(i));
            item.addActionListener(this);
            menu.add(item);
        }
        return menu;
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            JMenuItem source = (JMenuItem) (ae.getSource());
            String st = source.getText();
            if (st.equals("History")) {
                HistList histList = new HistList(dateList, timeList, list, locTable, repeatCount);
                histList.createAndShowGUI();
            } else {
                String URL = histTable.get(st);
                hello = new Hello();
                hello.actionGo(URL);
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
            }
            //ChildPanel.pooh.actionGo(URL);
        } catch (IOException ex) {
            Logger.getLogger(History.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
