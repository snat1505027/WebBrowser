package webbrowser;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import static javafx.concurrent.Worker.State.FAILED;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;

public class AccordianTest {

    JPanel getContent() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.add(new AccordianPanel().getPanel(), gbc);
        panel.setBackground(Color.DARK_GRAY);
        panel.setVisible(true);
        return panel;
    }
}

class AccordianPanel extends JPanel {

    boolean movingComponents = false;
    int visibleIndex = 3;
    private JLayeredPane layeredPane;

    public AccordianPanel() {
        layeredPane = new JLayeredPane();
        setLayout(null);
        // Add children and compute prefSize.
        int childCount = 4;
        Dimension d = new Dimension();
        int h = 0;
        Insets insets = getInsets();
        String[] name = {"Navigation", "Settings", "New tab", "More Tools"};
        for (int j = 0; j < childCount; j++) {
            ChildPanel child = new ChildPanel(j + 1, ml, name[j]);
            
            d = child.getPreferredSize();
            //layeredPane.add(child,new Integer(j));
            child.setBounds(0 + insets.left, h + insets.top, d.width, d.height);
            if (j < childCount - 1) {
                h += ControlPanel.HEIGHT;
            }
            add(child);
            setOpaque(true);
        }
        h += d.height;
        setPreferredSize(new Dimension(d.width, h));
        // Set z-order for children.
        setZOrder();
    }
    @Override public boolean isOptimizedDrawingEnabled() {
    return false;
  }

    private void setZOrder() {
        Component[] c = getComponents();
        for (int j = 0; j < c.length - 1; j++) {
            setComponentZOrder(c[j], c.length - 1 - j);
        }
    }

    private void setChildVisible(int indexToOpen) {
        // If visibleIndex < indexToOpen, components at
        // [visibleIndex+1 down to indexToOpen] move up.
        // If visibleIndex > indexToOpen, components at
        // [indexToOpen+1 up to visibleIndex] move down.
        // Collect indices of components that will move
        // and determine the distance/direction to move.
        int[] indices = new int[0];
        int travelLimit = 0;
        if (visibleIndex < indexToOpen) {
            travelLimit = ControlPanel.HEIGHT
                    - getComponent(visibleIndex).getHeight();
            int n = indexToOpen - visibleIndex;
            indices = new int[n];
            for (int j = visibleIndex, k = 0; j < indexToOpen; j++, k++) {
                indices[k] = j + 1;
            }
        } else if (visibleIndex > indexToOpen) {
            travelLimit = getComponent(visibleIndex).getHeight()
                    - ControlPanel.HEIGHT;
            int n = visibleIndex - indexToOpen;
            indices = new int[n];
            for (int j = indexToOpen, k = 0; j < visibleIndex; j++, k++) {
                indices[k] = j + 1;
            }
        }
        movePanels(indices, travelLimit);
        visibleIndex = indexToOpen;
    }
  private void movePanels(final int[] indices, final int travel) {
    movingComponents = true;
    Thread thread = new Thread(new Runnable() {
      public void run() {
        Component[] c = getComponents();
        int limit = travel > 0 ? travel : 0;
        int dy = travel > 0 ? 1 : -1;

        (new SwingWorker<Void, Integer>() {
          @Override public Void doInBackground() {
            int count = travel > 0 ? 0 : travel;
            while (count < limit) {
              try {
                Thread.sleep(5);
              } catch (InterruptedException e) {
                System.out.println("interrupted");
                break;
              }
              for (int j = 0; j < indices.length; j++) {
                int index = c.length - 1 - indices[j];
                Point p = c[index].getLocation();
                p.y += dy;
                c[index].setLocation(p.x, p.y);
              }
              publish(count++);
            }
            return null;
          }
          @Override protected void process(java.util.List<Integer> chunks) {
            repaint();
          }
          @Override public void done() {
            movingComponents = false;
          }
        }).execute();
      }
    });
    thread.setPriority(Thread.NORM_PRIORITY);
    thread.start();
  }

    private MouseListener ml = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            int index = ((ControlPanel) e.getSource()).id - 1;
            if (!movingComponents) {
                setChildVisible(index);
            }
        }
    };

    public JPanel getPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        panel.add(this, gbc);
        return panel;
    }
}

class ChildPanel extends JPanel {
    //static Hello hello;
    static Pooh pooh;
    static BookMarks bookMarks;
    static JButton histbutton = new JButton("History");
    static JButton zbutton = new JButton("Zoom");
    static JSlider slider = new JSlider();
    public ChildPanel(int id, MouseListener ml, String name) {
        setLayout(new BorderLayout());
        add(new ControlPanel(id, ml, name), "First");
        add(getContent(id));
    }

    public JPanel getContent(int id) {
        if (id == 1) {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = gbc.PAGE_START;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JButton hbutton = new JButton("Home");
            hbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_1rsz_house.png")));
            hbutton.setToolTipText("Click to go home");
            hbutton.setPreferredSize(new Dimension(150, 25));
            hbutton.setBackground(Color.decode("#B29A99"));
            hbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if(ChildPanel.pooh.tb.getSelectedIndex()==0){
                        ChildPanel.pooh.actionGo("http://www.google.com");
                    }
                    else{
                   ChildPanel.pooh.listTab.get(ChildPanel.pooh.tb.getSelectedIndex()).actionGo("http://www.google.com");
                    }
                }
            });
            hbutton.setEnabled(true);
            panel.add(hbutton, gbc);
            histbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_1rsz_25011.png")));
            histbutton.setToolTipText("Browse previous pages");
            histbutton.setPreferredSize(new Dimension(150, 25));
            histbutton.setBackground(Color.decode("#B29A99"));
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            //gbc.ipady = 40;      //make this component tall
            gbc.weightx = 0.0;
            gbc.gridheight = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            histbutton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    try {
                        ChildPanel.pooh.histGo();
                    } catch (IOException ex) {
                        Logger.getLogger(ChildPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            histbutton.setEnabled(true);
            panel.add(histbutton, gbc);
            JButton bookbutton = new JButton("Bookmarks");
            bookbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    bookMarks.showFile();
                    
                }
            });
            bookbutton.setEnabled(true);
            bookbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_128px-oxygen480-places-bookmarkssvg.png")));
            bookbutton.setToolTipText("Bookmark this page");
            gbc.insets = new Insets(1, 1, 1, 1);
            bookbutton.setPreferredSize(new Dimension(150, 25));
            bookbutton.setBackground(Color.decode("#B29A99"));
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridheight = 1;
            gbc.weightx = 0.0;
            gbc.gridx = 0;
            gbc.gridy = 2;
            panel.add(bookbutton, gbc);
//            panel.setOpaque(true);
//            panel.setForeground(Color.black);
            panel.setBackground(Color.DARK_GRAY);
            return panel;

        } else if (id == 2) {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = gbc.PAGE_START;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.anchor = gbc.PAGE_START;
            zbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_rsz_2fontsize.png")));
            zbutton.setToolTipText("Click to get better view");
            zbutton.setPreferredSize(new Dimension(150, 15));
            zbutton.setBackground(Color.decode("#B29A99"));
            panel.add(zbutton, gbc);
            slider.setBackground(Color.DARK_GRAY);
            slider.setMinimum(1);
            slider.setValue(1);
//            slider.addChangeListener(new javax.swing.event.ChangeListener() {
//                @Override
//                public void stateChanged(ChangeEvent ce) {
//                   ChildPanel.pooh. 
//                }
//            });
            gbc.insets = new Insets(4, 4, 4, 4);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 0.0;
            gbc.gridheight = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(slider, gbc);
            JButton fsbutton = new JButton("Full Screen");
            fsbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_gnome-view-fullscreensvg.png")));
            fsbutton.setToolTipText("Click to get full screen");
            fsbutton.setPreferredSize(new Dimension(150, 15));
            fsbutton.setBackground(Color.decode("#B29A99"));
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 0.0;
            gbc.gridheight = 1;
            gbc.gridx = 0;
            gbc.gridy = 2;
//            panel.setOpaque(true);
//            panel.setForeground(Color.black);
            panel.add(fsbutton, gbc);
            panel.setBackground(Color.DARK_GRAY);
            return panel;
        } else if (id == 3) {
            JPanel panel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.weightx = 0.5;
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = gbc.PAGE_START;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            JButton hbutton = new JButton("New Page");
            hbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_download_4.jpg")));
            hbutton.setToolTipText("Start with new page");
            hbutton.setPreferredSize(new Dimension(150, 25));
            hbutton.setBackground(Color.decode("#B29A99"));
            panel.add(hbutton, gbc);
            JButton hisbutton = new JButton("New Window");
            hisbutton.setIcon(new ImageIcon(Pooh.class.getResource("/pic4.jpg")));
            hisbutton.setToolTipText("Start in new window");
            hisbutton.setPreferredSize(new Dimension(150, 25));
            hisbutton.setBackground(Color.decode("#B29A99"));
            gbc.insets = new Insets(1, 1, 1, 1);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            //gbc.ipady = 40;      //make this component tall
            gbc.weightx = 0.0;
            gbc.gridheight = 1;
            gbc.gridx = 0;
            gbc.gridy = 1;
            panel.add(hisbutton, gbc);
            panel.setBackground(Color.DARK_GRAY);
            return panel;
        } else {
            JPanel panel4 = new JPanel(new BorderLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.weighty = 0;
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.gridheight = 1;
            gbc.anchor = gbc.PAGE_START;
            gbc.fill = GridBagConstraints.HORIZONTAL;
            JButton cbutton = new JButton("Copy");
            cbutton.setIcon(new ImageIcon(Pooh.class.getResource("/rsz_file_copy.png")));
            cbutton.setToolTipText("Copy");
            cbutton.setPreferredSize(new Dimension(150, 35));
            cbutton.setBackground(Color.decode("#B29A99"));
//            panel4.setOpaque(true);
//            panel4.setForeground(Color.black);
            panel4.setBorder(new EmptyBorder(5, 0, 5, 0));
            panel4.add(cbutton, BorderLayout.NORTH);
            panel4.setBackground(Color.DARK_GRAY);
            return panel4;
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(150, 150);
    }

}

class ControlPanel extends JPanel {

    int id;
    JLabel titleLabel;
    Color c1 = new Color(51, 3, 0);
    Color c2 = new Color(153, 129, 127);
    Color fontFg = Color.BLACK;
    Color rolloverFg = new Color(206, 157, 157);
    public final static int HEIGHT = 40;

    public ControlPanel(int id, MouseListener ml, String name) {
        this.id = id;
        //setLayout(new BorderLayout());
        add(titleLabel = new JLabel(name));
        titleLabel.setForeground(fontFg);
        Dimension d = getPreferredSize();
        d.height = HEIGHT;
        setPreferredSize(d);
        addMouseListener(ml);
        addMouseListener(listener);
    }

    protected void paintComponent(Graphics g) {
        int w = getWidth();
        Graphics2D g2 = (Graphics2D) g;
        //g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
        //        RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setPaint(new GradientPaint(w / 2, 0, c1, w / 2, HEIGHT / 2, c2));
        g2.fillRect(0, 0, w, HEIGHT);
    }

    private MouseListener listener = new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            titleLabel.setForeground(rolloverFg);
        }

        public void mouseExited(MouseEvent e) {
            titleLabel.setForeground(fontFg);
        }
    };
}
