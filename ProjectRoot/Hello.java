package webbrowser;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.View;

public class Hello extends JPanel {

    private JButton backButton = new JButton();
    private JButton newPage;
    private JButton fullScr = new JButton();
    private JButton forwardButton = new JButton();
    private JFXPanel jfxPanel = new JFXPanel();
    ArrayList<JFXPanel> jfxList = new ArrayList<>();
    private JTextField locationTextField = new JTextField(100);
    String tUrl;
    private JPanel fPane = new JPanel(new BorderLayout());
    private final JProgressBar progressBar = new JProgressBar();
    private final JLabel lblStatus = new JLabel();
    String tilteName = null;
    private JPanel statusBar = new JPanel(new BorderLayout(5, 5));
    private JPanel totalPane = new JPanel(new BorderLayout());
    private WebEngine engine;
    private ArrayList<WebView> listView = new ArrayList<>();
    JPanel buttonPanel = new JPanel(new BorderLayout());
    public ArrayList<String> pageList = new ArrayList<>();
    public ArrayList<String> historyList = new ArrayList<>();
    static int value = 0;
    int inI = 0;
    public ArrayList<String> title = new ArrayList<>();
    public ArrayList<String> titleUrl = new ArrayList<>();
    Hashtable<String, String> table;
    //private JPanel accorPane = new AccordianTest().getContent();
    History hist;
    private JTabbedPane tb;
    private GraphicsDevice device;
    private JFrame frame;
    FileWriter fileWriter;
    BufferedWriter out;
    GetName gname;
    static Pooh pooh;
     Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");


    public Hello() throws IOException {

        table = new Hashtable<>();
        fPane.add(jfxPanel, BorderLayout.CENTER);
        fPane.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        setLayout(new BorderLayout());
        add(fPane, BorderLayout.CENTER);
        add(lblStatus, BorderLayout.PAGE_END);
        currentScene();
    }

    public JFXPanel getJfxpanel(){
        return jfxPanel;
    }

    public JPanel getPanel(){
        return this;
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
                listView.add(view);
                engine = view.getEngine();

                engine.createPopupHandlerProperty();

    public void actionGo(String url) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                tUrl = toURL(url);
                engine.load(tUrl);
            }
        });
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
            }else if (str.startsWith("gmail")) {
                str = "https://www.google.com/gmail/about/" + str.substring("gmail".length()).trim().replaceAll(" ", "+");
            } else if (str.contains(" ")) { 
                str = "http://www.google.com/search?q=" + str.trim().replaceAll(" ", "+");
            } else if (!(str.startsWith("http://") || str.startsWith("https://")) && !str.isEmpty()) {
                str = "http://" + str;  
            }

            return new URL(str).toExternalForm();
        } catch (MalformedURLException exception) {
            return null;
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


}
