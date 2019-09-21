package Data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetData {

    private String API = "apikey=TD34VBSS8XO5M9W8";
    private String basicURL = "https://www.alphavantage.co/query?function=TIME_SERIES_INTRADAY&";
    private String symbol;
    private String interval;
    private StringBuilder res;


    public GetData(String ticker) throws Exception {
        symbol = "symbol=" + ticker + "&";
        interval = "interval=1min&";

        URL url = new URL(basicURL + symbol + interval + API);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        res = new StringBuilder();

        //TODO
        System.out.println(url.toString());
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        int c;

        while((c = in.read()) != -1)
            res.append((char) c);

    }

    private double[] parseJson(String input) {
        int temp;
        double[] values = null;

        try {
            JsonObject object = new JsonParser().parse(input).getAsJsonObject();
            String data = object.getAsJsonObject("Time Series (1min)").toString();
            String[] vals = data.substring((temp = data.indexOf('{', 1)) + 1, data.indexOf('}', temp)).split(",");
            values = new double[vals.length];
            //TODO
            System.out.println(data);

            for (temp = 0; temp < vals.length; temp++)
                values[temp] = Double.valueOf(vals[temp].substring(vals[temp].indexOf(":") + 1).replaceAll("\"", ""));
        } catch (NullPointerException ex) {
            System.out.println("Disadvantage of a free API");
        }


        return values;
    }

    public static void LoadData(String ticker, Object[] vals) throws Exception {
        GetData data = new GetData(ticker);
        double[] values = data.parseJson(data.res.toString());

        for(int i = 1, j = 0; j < values.length; i++, j++)
            vals[i] = values[j];

        //vals[6] = "Get Chart";
    }

    public static double LoadData(String ticker) throws Exception {
        GetData data = new GetData(ticker);
        if (data != null)
            return data.parseJson(data.res.toString())[0];
        return 0;
    }

}
