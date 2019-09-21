package GUI;

import Data.GetData;
import Data.Plot;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

public class TradingWindow {

    private JFrame frame;
    private JPanel pane;
    private JTable table;
    private JComboBox menu;
    private JButton add;
    private JLabel dow;

    private List<String> tickers;
    private String[] columns;
    private Object[][] data;
    private String ticker;

    public TradingWindow() {
        frame = new JFrame("Trading Window");
        frame.setSize(new Dimension(800, 800));
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);

        pane = new JPanel(new FlowLayout(FlowLayout.LEFT));
        menu = new JComboBox();
        add = new JButton("Add");
        dow = new JLabel();
        tickers = new LinkedList<>();

        addItems();

        add.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);

                String ticker = (String) menu.getSelectedItem();
                if(!tickers.contains(ticker)) {
                    tickers.add(ticker);
                    updateTable();
                }
            }
        });

        pane.add(menu, FlowLayout.LEFT);
        pane.add(add);
        pane.add(dow);

        columns = new String[] {
                "Ticker", "Open", "High", "Low", "Close", "Volume"
        };

        data = new Object[43][6];
        for(int i = 0; i < data.length; i++)
            for(int j = 0; j < data[i].length; j++)
                data[i][j] = null;

        initializeTable();
        frame.add(pane, BorderLayout.NORTH);
        frame.add(new JScrollPane(table));

        new Thread(() -> {
            while(true) {
                try {
                    refreshTable();

                    dow.setText("Fetching Data for DJIA");
                    dow.setText("DJIA: " + GetData.LoadData("DJI"));
                    Thread.sleep(2000 * 60 );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

        frame.setVisible(true);
    }

    private void initializeTable() {
        table = new JTable(data, columns);
        table.setDefaultEditor(Object.class, null);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //TODO
                super.mouseClicked(e);
                int r = table.rowAtPoint(e.getPoint());

                if (r >= 0 && r < table.getRowCount())
                    table.setRowSelectionInterval(r, r);
                else
                    table.clearSelection();

                int rowIndex = table.getSelectedRow();
                Object[] arr = tickers.toArray();

                try {
                    ticker = (String) arr[rowIndex];
                } catch (ArrayIndexOutOfBoundsException ex) {

                }

                if(ticker == null || r >= tickers.size())
                    return;

                if (ticker.length() > 0) {
                        JFrame f = new JFrame("Select Action");
                        JButton buy = new JButton("Buy");
                        JButton sell = new JButton("Sell");
                        JButton plot = new JButton("Plot");
                        JPanel pane = new JPanel(new FlowLayout());

                        //f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                        f.setSize(new Dimension(200, 100));
                        f.setResizable(false);
                        f.setLocationRelativeTo(null);

                        buy.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                super.mouseReleased(e);
                                //TODO
                                f.dispose();
                                JFrame frame = new JFrame("Buy " + ticker);
                                JLabel qty = new JLabel("Enter Quantity : ");
                                JLabel mkt = new JLabel("Order Type: Market Order");
                                JTextField getQuantity = new JTextField("100");
                                JButton buy = new JButton("Buy");

                                getQuantity.setColumns(5);
                                buy.setSize(25, 25);

                                JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEADING));
                                pane.add(mkt);
                                pane.add(qty);
                                pane.add(getQuantity);
                                pane.add(buy);

                                buy.addMouseListener(new MouseAdapter() {
                                    @Override
                                    public void mouseReleased(MouseEvent e) {
                                        super.mouseReleased(e);
                                        frame.dispose();

                                        //TODO JDBC handling user data

                                        JFrame frame = new JFrame("Status");
                                        JLabel status = new JLabel();
                                        JButton close = new JButton("Close");
                                        JPanel pane = new JPanel(new FlowLayout(FlowLayout.LEADING));

                                        status.setText("Order Placed Successfully.");
                                        frame.setSize(new Dimension(200, 150));
                                        pane.add(status);
                                        pane.add(close);
                                        frame.setLocationRelativeTo(null);
                                        frame.add(pane);
                                        frame.setVisible(true);

                                        close.addMouseListener(new MouseAdapter() {
                                            @Override
                                            public void mouseReleased(MouseEvent e) {
                                                super.mouseReleased(e);
                                                frame.dispose();
                                            }
                                        });
                                    }
                                });

                                frame.add(pane);
                                frame.setSize(new Dimension(200, 150));
                                frame.setLocationRelativeTo(null);
                                frame.setVisible(true);
                            }
                        });

                        sell.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                super.mouseReleased(e);
                                //TODO
                            }
                        });

                        plot.addMouseListener(new MouseAdapter() {
                            @Override
                            public void mouseReleased(MouseEvent e) {
                                super.mouseReleased(e);
                                try {
                                    Plot.plot(ticker);
                                    f.dispose();
                                } catch (Throwable t) {
                                    System.out.println("API Error. (Server Side Error)");
                                }
                            }
                        });

                        pane.add(buy);
                        pane.add(sell);
                        pane.add(plot);
                        f.add(pane);

                        f.setVisible(true);
                }
            }
        });

        ticker = null;
    }

    private void refreshTable() throws Exception {
        for(int i = 0; i < tickers.size(); i++) {
            GetData.LoadData(tickers.get(i), data[i]);
        }
    }

    private void updateTable() {
        try {
            int k = 0;
            Object[] tickers = this.tickers.toArray();
            String ticker;

            for (Object[] i : data) {
                if (k < tickers.length)
                    ticker = (String) tickers[k++];
                else
                    break;

                if (i[0] == null) {
                    i[0] = ticker;
                    GetData.LoadData(ticker, i);
                    initializeTable();
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void addItems() {
        menu.addItem("MSFT");
        menu.addItem("GOOGL");
        menu.addItem("BA");
        menu.addItem("GE");
        menu.addItem("AA");
        menu.addItem("AMZN");
    }

}
