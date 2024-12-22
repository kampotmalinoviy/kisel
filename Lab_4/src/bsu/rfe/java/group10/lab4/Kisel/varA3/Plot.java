package bsu.rfe.java.group10.lab4.Kisel.varA3;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.*;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.util.ArrayList;
import java.util.List;

public class Plot extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    private JFileChooser fileChooser = null;
    private final JCheckBoxMenuItem showAxisMenuItem;
    private final JCheckBoxMenuItem showMarkersMenuItem;
    private final JCheckBoxMenuItem RotateMenuItem;
    private final GraphicsDisplay display = new GraphicsDisplay();
    private boolean fileLoaded = false;

    private final Action RestAction;
    private final Action SaveAction;
    public Plot() {
        super("Build Graphics");
        setSize(WIDTH, HEIGHT);
        Toolkit kit = Toolkit.getDefaultToolkit();
        setLocation((kit.getScreenSize().width - WIDTH)/2,
                (kit.getScreenSize().height - HEIGHT)/2);
        setExtendedState(MAXIMIZED_BOTH);
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        Action openGraphicsAction = new AbstractAction("Open file with graphics data") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser==null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showOpenDialog(Plot.this) ==
                        JFileChooser.APPROVE_OPTION) ;
                openGraphics(fileChooser.getSelectedFile());
            }
        };
        fileMenu.add(openGraphicsAction);

        RestAction = new AbstractAction("Reset") {
            public void actionPerformed(ActionEvent event) {
                display.reset();
            }
        };
        fileMenu.add(RestAction);
        RestAction.setEnabled(false);

        SaveAction = new AbstractAction("Save changed data") {
            public void actionPerformed(ActionEvent event) {
                if (fileChooser == null) {
                    fileChooser = new JFileChooser();
                    fileChooser.setCurrentDirectory(new File("."));
                }
                if (fileChooser.showSaveDialog(Plot.this) == JFileChooser.APPROVE_OPTION) {
                    SaveFile(fileChooser.getSelectedFile());
                }
            }
        };
        fileMenu.add(SaveAction);
        SaveAction.setEnabled(false);


        JMenu graphicsMenu = new JMenu("Graphic");
        menuBar.add(graphicsMenu);
        Action showAxisAction = new AbstractAction("Show axis") {
            public void actionPerformed(ActionEvent event) {
                display.setShowAxis(showAxisMenuItem.isSelected());
            }
        };
        showAxisMenuItem = new JCheckBoxMenuItem(showAxisAction);
        graphicsMenu.add(showAxisMenuItem);
        showAxisMenuItem.setSelected(true);


        Action RotateAction = new AbstractAction("Rotate at left") {
            public void actionPerformed(ActionEvent event) {
                display.setRotate(RotateMenuItem.isSelected());
            }
        };
        RotateMenuItem = new JCheckBoxMenuItem(RotateAction);
        graphicsMenu.add(RotateMenuItem);
        RotateMenuItem.setSelected(false);
        RotateMenuItem.setEnabled(false);

        Action showMarkersAction = new AbstractAction("Show spots markers") {
            public void actionPerformed(ActionEvent event) {
                display.setShowMarkers(showMarkersMenuItem.isSelected());
            }
        };
        showMarkersMenuItem = new JCheckBoxMenuItem(showMarkersAction);
        graphicsMenu.add(showMarkersMenuItem);
        showMarkersMenuItem.setSelected(true);
        graphicsMenu.addMenuListener(new GraphicsMenuListener());
        getContentPane().add(display, BorderLayout.CENTER);
    }



    /* protected void openGraphics(File selectedFile) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(selectedFile));
            ArrayList<Double[]> dataList = new ArrayList<>();
            String line;

            // Пропускаем заголовки и читаем только координаты
            while ((line = in.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length >= 2) {
                    double x = Double.parseDouble(parts[0]);
                    double y = Double.parseDouble(parts[1]);
                    dataList.add(new Double[]{x, y});
                }
            }

            Double[][] graphicsData = dataList.toArray(new Double[0][0]);

            if (graphicsData.length > 0) {
                fileLoaded = true;
                display.showGraphics(graphicsData);
            }
            in.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(Plot.this,
                    "Указанный файл не найден", "Ошибка загрузки данных",
                    JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(Plot.this,
                    "Ошибка чтения координат точек из файла",
                    "Ошибка загрузки данных", JOptionPane.WARNING_MESSAGE);
        }
    } */

    protected void openGraphics(File selectedFile) {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(selectedFile));
            ArrayList<Double[]> graphicsData = new ArrayList(50);

            while(in.available() > 0) {
                Double x = in.readDouble();
                Double y = in.readDouble();
                graphicsData.add(new Double[]{x, y});
            }

            if (graphicsData.size() > 0) {
                this.fileLoaded = true;
                this.resetGraphicsMenuItem.setEnabled(true);
                this.display.displayGraphics(graphicsData);
            }

        } catch (FileNotFoundException var6) {
            JOptionPane.showMessageDialog(this, "Указанный файл не найден", "Ошибка загрузки данных", 2);
        } catch (IOException var7) {
            JOptionPane.showMessageDialog(this, "Ошибка чтения координат точек из файла", "Ошибка загрузки данных", 2);
        }
    }


    private void SaveFile(File selectedFile){
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
            Double[][] data = display.getGraphicsData();
            for (Double[] datum : data) {
                out.writeDouble((Double) datum[0]);
                out.writeDouble((Double) datum[1]);
            }
            out.close();
        } catch (Exception ignored) {
        }
    }

    private class GraphicsMenuListener implements MenuListener {
        public void menuSelected(MenuEvent e) {
            showAxisMenuItem.setEnabled(fileLoaded);
            showMarkersMenuItem.setEnabled(fileLoaded);
        }
        public void menuDeselected(MenuEvent e) {
        }
        public void menuCanceled(MenuEvent e) {
        }
    }
}
