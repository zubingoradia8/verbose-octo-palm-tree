import Data.Plot;
import GUI.*;

import javax.swing.*;

public class Main {

    public static void main(String[] args) throws Throwable {
        //new GetDataCSV("MSFT");
        //new Plot("MSFT").plot();
        setUI();
        //new TradingWindow();
        new Login();
        Test.print();
    }

    private static void setUI() throws Exception {
        /*for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                UIManager.setLookAndFeel(info.getClassName());
                break;
            }

        }*/
        //com.jtattoo.plaf.hifi.HiFiLookAndFeel
        UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
    }
}
