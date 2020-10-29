package webbrowser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.embed.swing.JFXPanel;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayer;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Pooh extends JFrame implements MouseMotionListener {

    private JButton backButton = new JButton();
    private JButton newPage;
    private JButton fullScr = new JButton();
    private JButton forwardButton = new JButton();
    public JFXPanel jfxPanel = new JFXPanel();
    ArrayList<JFXPanel> jfxList = new ArrayList<>();
    public JTextField locationTextField = new JTextField(100);
    String tUrl;
    private JPanel fPane = new JPanel(new BorderLayout());
    public final JProgressBar progressBar = new JProgressBar();
    private final JLabel lblStatus = new JLabel();
    String tilteName = null;
    public JPanel statusBar = new JPanel(new BorderLayout(5, 5));
    private JPanel totalPane = new JPanel(new BorderLayout());
    private WebEngine engine;
    private ArrayList<WebView> listView = new ArrayList<>();
    ArrayList<String> timeList = new ArrayList<>();
    JPanel buttonPanel = new JPanel(new BorderLayout());
    public ArrayList<String> pageList = new ArrayList<>();
    public ArrayList<String> historyList = new ArrayList<>();
    static int value = 0;
    static int inI = 0;
    int inIH = 0;
    public ArrayList<String> title = new ArrayList<>();
    public ArrayList<String> titleUrl = new ArrayList<>();
    Hashtable<String, String> table;
    Hashtable<String, String> histTable;
    private JPanel accorPane = new AccordianTest().getContent();
    History hist;
    HashMap<Integer, Hello> listTab = new HashMap<>();
    HashMap<Integer, JPanel> listPanel = new HashMap<>();
    public JTabbedPane tb = new JTabbedPane();
    private GraphicsDevice device;
    private JFrame frame;
    FileWriter fileWriter;
    BufferedWriter out;
    GetName gname;
    Hello hello;
    private JPanel bookMarkPane = new JPanel(new BorderLayout());
    BookMarks bookMarked;
    Calendar cal = Calendar.getInstance();
    FavIcon favIcon;
    static BookMarks bookMarks;
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    BufferedInputStream is = null;
    BufferedOutputStream os = null;

    public Pooh() throws IOException {
//        try {
//            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
//            ex.printStackTrace();
//        }
        title = new ArrayList<String>(new LinkedHashSet<String>(title));
        titleUrl = new ArrayList<String>(new LinkedHashSet<String>(titleUrl));
        bookMarkPane.setBackground(Color.decode("#330300"));
        titleUrl.add("http://www.google.com");
        bookMarked = new BookMarks();
        table = new Hashtable<>();
        setResizable(true);
        setTitle("Pooh");
        ImageIcon img = new ImageIcon(Hello.class.getResource("/Panda-icon.png"));
        setIconImage(img.getImage());
        JPanel buttonPanel1 = new JPanel();
        buttonPanel1.setBackground(Color.decode("#330300"));
        //buttonPanel.setLayout(null);
        backButton.setIcon(new ImageIcon(Hello.class.getResource("/rsz_left_custom.png")));
        backButton.setBackground(Color.pink.darker().darker());
        // backButton.setBackground(Color.decode("#330300"));
        backButton.setToolTipText("Back");
        backButton.setPreferredSize(new Dimension(40, 40));
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                BorderFactory.createRaisedBevelBorder();
                // if ((pageList.size() > 0) && (value > 0)) {
                actionBack();
                //}
                System.out.println(value + " " + pageList.size() + " back");
            }
        });
        backButton.setEnabled(true);
        backButton.setBorderPainted(false);
        buttonPanel1.add(backButton);
        forwardButton.setIcon(new ImageIcon(Hello.class.getResource("/1813406178.png")));
        forwardButton.setBackground(Color.DARK_GRAY);
//        forwardButton.setBackground(Color.decode("#330300"));
        forwardButton.setPreferredSize(new Dimension(25, 25));
        forwardButton.setToolTipText("Forward");
        forwardButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (pageList.size() > value) {
                    value++;
                }
                System.out.println(value + "forwad");
                actionForward();
            }
        });
        forwardButton.setEnabled(true);
        forwardButton.setBorderPainted(false);
        buttonPanel1.add(forwardButton);
        JPanel picLoc = new JPanel(new BorderLayout());
        locationTextField.setPreferredSize(new Dimension(100, 25));
        locationTextField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                locationTextField.selectAll();
            }

            @Override
            public void focusLost(FocusEvent fe) {
                locationTextField.select(0, 0);
            }
        });
        locationTextField.setToolTipText("Hi I'm Pooh, where do you want to go today?");
        JButton bookMark = new JButton(new ImageIcon(Hello.class.getResource("/rsz_128px-oxygen480-places-bookmarkssvg.png")));
        bookMark.setOpaque(true);
        bookMark.setBorderPainted(false);
        bookMark.setPreferredSize(new Dimension(22, 22));
        bookMark.setBackground(Color.WHITE);
        bookMark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    bookMarked.addBookMarks(locationTextField.getText(), tb.getTitleAt(tb.getSelectedIndex()));
                    bookMarkPane.add(bookMarked.finalPane, BorderLayout.WEST);
                    bookMarkPane.revalidate();
                    validate();
                    //buttonPanel.add(,BorderLayout.SOUTH);
                } catch (IOException ex) {
                    Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        bookMark.setToolTipText("BookMark this page");
        bookMark.setEnabled(true);
        locationTextField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    value++;
                    String tUrl = toURL(locationTextField.getText());
                    if (tb.getSelectedIndex() == 0) {
                        actionGo(locationTextField.getText());
                    } else {
                        hello = listTab.get(tb.getSelectedIndex());
                        hello.actionGo(locationTextField.getText());
                    }
                    System.out.println(tb.getSelectedIndex());
                }
            }
        });
        locationTextField.setDragEnabled(true);
        picLoc.setBorder(locationTextField.getBorder());
        locationTextField.setBorder(null);
        picLoc.add(locationTextField, BorderLayout.CENTER);
        picLoc.add(bookMark, BorderLayout.EAST);
        buttonPanel1.add(picLoc, BorderLayout.EAST);
        JPanel buttonPanel2 = new JPanel(new BorderLayout(5, 5));
        buttonPanel2.setBackground(Color.decode("#330300"));
        fullScr.setIcon(new ImageIcon(Hello.class.getResource("/rsz_gnome-view-fullscreensvg (1).png")));
        fullScr.setPreferredSize(new Dimension(40, 40));
        fullScr.setBorderPainted(false);
        fullScr.setBackground(Color.decode("#330300"));
        fullScr.setLocation(1050, 25);
        fullScr.setToolTipText("Go Huge");
        fullScr.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                buttonPanel.setVisible(false);
                try {
                    setFullScreen();
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {  // handler
                if (ke.getKeyCode() == KeyEvent.VK_F11) {
                    exitFullScreen();
                }
            }
        });
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                requestFocusInWindow();
                System.out.println("joeee");
            }
        });

        fullScr.setEnabled(true);

        newPage = new JButton();
        newPage.setIcon(new ImageIcon(Hello.class.getResource("/Plus.png")));
        newPage.setPreferredSize(new Dimension(30, 30));
        newPage.setBorderPainted(false);
        newPage.setBackground(Color.decode("#330300"));
        newPage.setToolTipText("New Page");
        newPage.setEnabled(true);

        buttonPanel2.add(fullScr, BorderLayout.LINE_END);
        buttonPanel2.add(newPage, BorderLayout.CENTER);

        buttonPanel.setBackground(Color.decode("#330300"));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        buttonPanel.add(buttonPanel1, BorderLayout.WEST);
        buttonPanel.add(buttonPanel2, BorderLayout.EAST);
        bookMarkPane.add(BookMarks.finalPane, BorderLayout.WEST);
        buttonPanel.add(bookMarkPane, BorderLayout.SOUTH);
        statusBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        statusBar.setBackground(Color.DARK_GRAY);
        progressBar.setPreferredSize(new Dimension(150, 18));
        progressBar.setStringPainted(true);
        progressBar.setForeground(Color.decode("#99817F"));
        statusBar.add(accorPane, BorderLayout.NORTH);
        statusBar.add(progressBar, BorderLayout.SOUTH);

        statusBar.setVisible(false);
        JPanel lblBar = new JPanel(new BorderLayout());
        lblBar.setPreferredSize(new Dimension(50, 10));
        //lblBar.add(lblStatus, BorderLayout.CENTER);
        JPanel jp = new JPanel();
        jp.setLayout(new BorderLayout());
        UIManager.put("TabbedPane.background", Color.decode("#330300"));
        //tb = new JTabbedPane();
        tb.setUI(new CustomTabbedPaneUI());
        tb.setForeground(Color.decode("#330300"));
        tb.addTab("New Tab", fPane);
        //initTabComponent(0);
        jp.add(new JLayer<JTabbedPane>(tb));
        newPage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    hello = new Hello();
                    hello.inI = inIH + 1;
                    listTab.put(inI + 1, hello);
                    JPanel panel = new JPanel(new BorderLayout());
                    panel.add(hello.getPanel(), BorderLayout.CENTER);
                    hello.getJfxpanel().addMouseMotionListener(Pooh.this);
                    panel.add(statusBar, BorderLayout.WEST);
                    listPanel.put(inI + 1, panel);
                    tb.addTab("New Tab", panel);
                    tb.setSelectedIndex(inI + 1);
                    locationTextField.setText("");
                    locationTextField.requestFocusInWindow();
                    initTabComponent(inI + 1);
                    inIH++;
                    inI++;
                } catch (IOException ex) {
                    Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        tb.setOpaque(true);
        fPane.add(jfxPanel, BorderLayout.CENTER);
        jfxPanel.addMouseMotionListener(this);
        fPane.add(statusBar, BorderLayout.WEST);
        fPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        totalPane.add(buttonPanel, BorderLayout.NORTH);
        //totalPane.add(statusBar, BorderLayout.WEST);
        totalPane.add(jp, BorderLayout.CENTER);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        //setUndecorated(true);
        setLayout(new BorderLayout());
        getContentPane().add(totalPane, BorderLayout.CENTER);
        //getContentPane().add(statusBar,BorderLayout.WEST);
        getContentPane().add(lblStatus, BorderLayout.PAGE_END);
        setPreferredSize(new Dimension(1024, 600));
        this.addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                LocalDate localDate = LocalDate.now();
                String date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);
                File file = new File("History.txt");
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException ex) {
                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                try {
                    fileWriter = new FileWriter(file.getAbsoluteFile(), true);
                    out = new BufferedWriter(fileWriter);
                    if (title.size() != 0) {
                        out.write(date);
                        out.newLine();
                    }
                    for (int i = 0; i < title.size(); i++) {
                        String s = timeList.get(i) + " " + titleUrl.get(i) + " " + title.get(i);
                        out.write(s);
                        out.newLine();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (out != null) {
                        try {
                            out.close();
                        } catch (IOException ignore) {
                        }
                    }
                }
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        currentScene();
    }

    public int getIndex() {
        return tb.getSelectedIndex();
    }

    public boolean isActive() {
        return statusBar.isEnabled();
    }

    public JTabbedPane getTab() {
        return tb;
    }

    public void initTabComponent(int i) {
        tb.setTabComponentAt(i,
                new ButtonTabComponent(tb));
    }

    public void setFullScreen() {
        dispose();
        setUndecorated(true);
        setVisible(true);
    }

    public void exitFullScreen() {
        dispose();
        buttonPanel.setVisible(true);
        setUndecorated(false);
        setVisible(true);
    }

    private void actionBack() {
        String url = (String) pageList.get(value);
        try {
            value--;
            actionGo(url);
        } catch (Exception e) {
        }
    }

    private void actionForward() {
        String url = (String) pageList.get(value);
        try {
            actionGo(url);
        } catch (Exception e) {
        }
    }

    private void currentScene() {

        gname = new GetName();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {

                WebView view = new WebView();
                engine = view.getEngine();

                engine.createPopupHandlerProperty();
                engine.titleProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (newValue != null && !"".equals(newValue)) {

                                    Pooh.this.setTitle("Pooh - " + newValue);
                                } else {
                                    Pooh.this.setTitle("Pooh");
                                }

                                if (newValue != null) {
                                    timeList.add(sdf.format(cal.getTime()));
                                    tb.setTitleAt(0, newValue);
                                    tilteName = newValue;
                                    table.put(newValue, tUrl);
                                    title.add(newValue);
                                    System.out.println("title " + newValue);
                                    historyList.add(newValue);
                                    pageList.add(tUrl);
                                }
                            }

                        });
                    }
                });
                engine.locationProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> ov, String oldValue, final String newValue) {
                        SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            public void run() {
                                if (title.size() == titleUrl.size()) {
                                    titleUrl.add(newValue);
                                    System.out.println("titleUrl " + newValue);
                                }
                                if (newValue.endsWith(".pdf")) {
                                    final PDFReader pdfViewer = new PDFReader(true);
                                    try {
                                        pdfViewer.openFile(new URL(newValue));
                                    } catch (IOException ex) {
                                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                locationTextField.setText(newValue);
                                String downloadableFile = null;
                                String[] downloadedFiles = {".doc", ".xls", ".zip", ".tgz", ".jar"};
                                for (String ext : downloadedFiles) {
                                    if (newValue.endsWith(ext)) {
                                        downloadableFile = ext;
                                        break;
                                    }
                                }
                                if (downloadableFile != null) {
                                    try {
                                        saveFileFromWeb(new URL(newValue));
                                    } catch (MalformedURLException ex) {
                                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }

                            }
                        });
                    }
                });


                jfxPanel.setScene(new Scene(view));
                //jfxList.add(jfxPanel);
            }
        });
    }

    public void transfer(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[2048];
        int bytesRead;
        while ((bytesRead = in.read(buffer)) > 0) {
            out.write(buffer, 0, bytesRead);
        }
    }

    public File chooseFile() {
        File result = null;
        JFileChooser fileChooser = new JFileChooser();
        if (fileChooser.showSaveDialog(fileChooser) == JFileChooser.APPROVE_OPTION) {
            result = fileChooser.getSelectedFile();
        }
        return result;
    }

    public void saveFileFromWeb(URL url) {
        File file = chooseFile();   
        if (file != null) {
            File folder = file.getParentFile();
            if (!folder.exists()) {
                folder.mkdirs();
            }

            InputStream in = null;
            OutputStream out = null;
            try {
                // 3. Initialise streams
                in = url.openStream();
                out = new FileOutputStream(file);
                // 4. Transfer data
                transfer(in, out);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 5. close streams
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        /* ignore */ }
                }
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        /* ignore */ }
                }
            }
        }

    }

    public void actionGo(String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tUrl = toURL(url);

                engine.load(tUrl);

            }
        });
    }

    public void histGo() throws IOException {
        LocalDate localDate = LocalDate.now();
        String date = DateTimeFormatter.ofPattern("yyy/MM/dd").format(localDate);

        File file = new File("D:\\WebBrowser\\History.txt");
        if (!file.exists()) {
            file.createNewFile();
        }
        try {
            fileWriter = new FileWriter(file.getAbsoluteFile(), true);
            out = new BufferedWriter(fileWriter);
            if (title.size() > 0) {
                out.write(date);
                out.newLine();
            }
            if (title.size() != titleUrl.size()) {
                System.out.println("hi error");
                timeList.clear();
                title.clear();
                titleUrl.clear();
            } else {
                for (int i = 0; i < title.size(); i++) {
                    String s = timeList.get(i) + " " + titleUrl.get(i) + " " + title.get(i);
                    System.out.println(timeList.get(i) + " " + titleUrl.get(i) + " " + title.get(i));
                    out.write(s);
                    out.newLine();
                }
                timeList.clear();
                title.clear();
                titleUrl.clear();
            }
        } catch (IOException ex) {
            Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignore) {
                }
            }
        }
        hist = new History(file);
        final JPopupMenu men;
        men = hist.create();
        men.show(ChildPanel.histbutton, 0, ChildPanel.histbutton.getHeight());
    }

    private static String toURL(String str) {
        try {
            if (str == null) {
                str = "";
            }
            if (str.startsWith("google")) {
                str = "http://www.google.com/search?q=" + str.substring("google".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("bing")) {
                str = "http://www.bing.com/search?q=" + str.substring("bing".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("yahoo")) {
                str = "http://search.yahoo.com/search?p=" + str.substring("yahoo".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("wiki")) {
                str = "http://en.wikipedia.org/w/index.php?search=" + str.substring("wiki".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("find")) {
                str = "http://www.google.com/search?q=" + str.substring("find".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("search")) {
                str = "http://www.google.com/search?q=" + str.substring("search".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("gmail")) {
                str = "https://www.google.com/gmail/about/" + str.substring("gmail".length()).trim().replaceAll(" ", "+");
            } else if (str.startsWith("oracle")) {
                str = "https://www.oracle.com/java/index.html";
            } else if (str.startsWith("youtube")) {
                str = "https://www.youtube.com/";
            } else if (str.contains(" ")) {
                str = "http://www.google.com/search?q=" + str.trim().replaceAll(" ", "+");
            } else if (!(str.startsWith("http://") || str.startsWith("https://")) && !str.isEmpty()) {
                str = "http://" + str;
            }

            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return "http://www.google.com";
        }
    }

    private void updateButtons() {
        if (pageList.size() < 2) {
            backButton.setEnabled(false);
            forwardButton.setEnabled(false);
        } else {
            backButton.setEnabled(value > 0);
            forwardButton.setEnabled(value < (pageList.size() - 1));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                try {
                    try {
                        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
                        //UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InstantiationException ex) {
                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (UnsupportedLookAndFeelException ex) {
                        Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Pooh browser = new Pooh();
                    ChildPanel.pooh = browser;
                    Hello.pooh = browser;
                    BookMarks.pooh = browser;
                    browser.setVisible(true);
                    browser.actionGo("http://google.com");
                } catch (IOException ex) {
                    Logger.getLogger(Pooh.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        System.out.println("mouse dragged");
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        if (tb.getSelectedIndex() == 0) {
            for (int i = tb.getTabCount() - 1; i > 0; i--) {
                JPanel pane = new JPanel();
                pane = listPanel.get(i);
                pane.remove(statusBar);
            }
            fPane.add(statusBar, BorderLayout.WEST);
            // System.out.println(tb.getSelectedIndex());
            if ((me.getX() < 3) && (me.getY() > 50)) {
                statusBar.setVisible(true);

            } else {
                statusBar.setVisible(false);
            }
        } else {
            for (int i = tb.getTabCount() - 1; i > 0; i--) {
                if (tb.getSelectedIndex() == i) {
                    continue;
                } else {
                    JPanel pane = new JPanel();
                    pane = listPanel.get(i);
                    pane.remove(statusBar);
                }
            }
            JPanel pane = new JPanel();
            pane = listPanel.get(tb.getSelectedIndex());
            pane.add(statusBar, BorderLayout.WEST);
            // System.out.println(tb.getSelectedIndex());
            if ((me.getX() < 3) && (me.getY() > 50)) {
                statusBar.setVisible(true);

            } else {
                statusBar.setVisible(false);
            }

        }
    }

}
