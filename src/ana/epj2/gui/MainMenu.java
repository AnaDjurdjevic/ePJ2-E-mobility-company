package ana.epj2.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {

        private JFrame thisF;

        private JPanel contentPane;
        private JMenuBar menuBar;
        private JMenu mnApplication;
        private JMenuItem mntmExit;
        private JMenu mnVehicles;
        private JMenuItem mntmCars;
        private JMenuItem mntmScooters;
        private JMenuItem mntmBicycles;
        private JMenu mnMalfunctions;
        private JMenuItem mntmMalfunctions;
        private JMenu mnBusinessResults;
        private JMenuItem mntmBusinessResults;
        private JMenu mnRepairCosts;
        private JMenuItem mntmRepairCosts;

        /**
         * Create the frame.
         */
       public MainMenu() {
            initialize();
        }

        private void initialize() {
                thisF = this;
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent arg0) {
                        String[] options = {"Yes", "No"};
                        int rezultat = JOptionPane.showOptionDialog(thisF,
                            "Are you sure you want to close the application?",
                            "Confirmation of closure", JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE, null,
                                options,
                                options[1]);
                        if (rezultat == JOptionPane.YES_OPTION)
                                System.exit(0);
                }
            });
            setTitle("ePJ2 E-mobility company");
            setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            setBounds(100, 100, 815, 420);
            setLocationRelativeTo(null);
            setExtendedState(Frame.MAXIMIZED_BOTH);
            setJMenuBar(getMenuBar_1());

            this.contentPane = new JPanel();
            this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
            this.contentPane.setLayout(new BorderLayout(0, 0));
            setContentPane(this.contentPane);
        }

        private JMenuBar getMenuBar_1() {
            if (menuBar == null) {
                menuBar = new JMenuBar();
                menuBar.add(getMnApplication());
                menuBar.add(getMnVehicles());
                menuBar.add(getMnMalfunctions());
                menuBar.add(getMnBusinessResults());
                menuBar.add(getmnRepairCosts());
            }
            return menuBar;
        }

        private JMenu getMnApplication() {
            if (mnApplication == null) {
                    mnApplication = new JMenu("Application");
                    mnApplication.setMnemonic('A');
                    mnApplication.add(getMntmExit());
            }
            return mnApplication;
        }

        private JMenuItem getMntmExit() {
            if (mntmExit == null) {
                    mntmExit = new JMenuItem("Exit");
                    mntmExit.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                            thisF.getToolkit()
                                .getSystemEventQueue()
                                .postEvent(
                                        new WindowEvent(thisF,
                                                WindowEvent.WINDOW_CLOSING));
                    }
                });
                    mntmExit.setMnemonic('E');
                    mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q,
                        InputEvent.ALT_MASK));
            }
            return mntmExit;
        }

        private JMenu getMnVehicles() {
            if (mnVehicles == null) {
                    mnVehicles = new JMenu("Vehicles");
                    mnVehicles.setMnemonic('V');
                    mnVehicles.add(getMntmCars());
                    mnVehicles.add(getMntmScooters());
                    mnVehicles.add(getMntmBicycles());
            }
            return mnVehicles;
        }

        private JMenuItem getMntmCars() {
            if (mntmCars == null) {
                mntmCars = new JMenuItem("Cars...");
                mntmCars.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        new CarsFrame().setVisible(true);
                    }
                });
                mntmCars.setMnemonic('C');
            }
            return mntmCars;
        }
        private JMenuItem getMntmScooters() {
                if (mntmScooters == null) {
                    mntmScooters = new JMenuItem("Scooters...");
                    mntmScooters.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            new ScootersFrame().setVisible(true);
                                }
                        });
                    mntmScooters.setMnemonic('S');
                }
                return mntmScooters;
        }
        private JMenuItem getMntmBicycles() {
                if (mntmBicycles == null) {
                    mntmBicycles = new JMenuItem("Bicycles...");
                    mntmBicycles.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        new BicyclesFrame().setVisible(true);
                                }
                        });
                    mntmBicycles.setMnemonic('V');
                }
                return mntmBicycles;
        }


        private JMenu getMnMalfunctions() {
            if (mnMalfunctions == null) {
                    mnMalfunctions = new JMenu("Malfunctions");
                    mnMalfunctions.setMnemonic('M');
                    mnMalfunctions.add(getMntmMalfunctions());
            }
            return mnMalfunctions;
        }

        private JMenuItem getMntmMalfunctions() {
            if (mntmMalfunctions == null) {
                    mntmMalfunctions = new JMenuItem("Malfunctions");
                    mntmMalfunctions.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //new PlanIProgramFrame().setVisible(true);
                    }
                });
                    mntmMalfunctions.setMnemonic('M');
            }
            return mntmMalfunctions;
        }

        private JMenu getMnBusinessResults() {
            if (mnBusinessResults == null) {
                    mnBusinessResults = new JMenu("Business Results");
                    mnBusinessResults.setMnemonic('B');
                    mnBusinessResults.add(getMntmBusinessResults());
            }
            return mnBusinessResults;
        }

        private JMenuItem getMntmBusinessResults() {
            if (mntmBusinessResults == null) {
                    mntmBusinessResults = new JMenuItem(
                        "Business Results...");
                    mntmBusinessResults.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        //new IzvestajiFrame().setVisible(true);
                    }
                });
                    mntmBusinessResults.setMnemonic('B');
            }
            return mntmBusinessResults;
        }

        private JMenu getmnRepairCosts() {
                if (mnRepairCosts == null) {
                        mnRepairCosts = new JMenu("Repair Costs");
                        mnRepairCosts.setMnemonic('R');
                        mnRepairCosts.add(getmntmRepairCosts());
                }
                return mnRepairCosts;
        }

        private JMenuItem getmntmRepairCosts() {
                if (mntmRepairCosts == null) {
                        mntmRepairCosts = new JMenuItem(
                                "Repair Costs...");
                        mntmRepairCosts.addActionListener(new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                        //new IzvestajiFrame().setVisible(true);
                                }
                        });
                        mntmRepairCosts.setMnemonic('R');
                }
                return mntmRepairCosts;
        }

}
