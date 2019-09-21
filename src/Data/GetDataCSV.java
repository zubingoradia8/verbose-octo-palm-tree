package Data;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetDataCSV {

    private String API = "apikey=TD34VBSS8XO5M9W8";
    private String basicURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&";
    private static String symbol;
    private String interval;

    public GetDataCSV(String ticker) throws IOException {
        symbol = "symbol=" + ticker + "&";
        interval = "interval=1min&";

        URL url = new URL(basicURL + symbol + interval + API + "&datatype=csv");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        System.out.println(basicURL + symbol + interval + API + "&datatype=csv");

        BufferedInputStream input = new BufferedInputStream(conn.getInputStream());
        FileOutputStream out = new FileOutputStream("Data.csv");
        int c;

        while((c = input.read()) != -1) {
            out.write(c);
        }
    }

    public static String getTicker() {
        return symbol;
    }

}
