package ru.denis.usdconverter;

import org.json.JSONArray;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.json.JSONObject;
import ru.denis.usdconverter.exceptions.WrongParsingException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ValuteParser {

    public static String getPage(String url) throws IOException {
        URL _url = new URL(url);

        HttpURLConnection connection = (HttpURLConnection)_url.openConnection();
        connection.connect();
        InputStream stream = connection.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
        }
        reader.close();

        return sb.toString();
    }

    public static float getUSD() throws IOException, WrongParsingException {
        String jsonText = getPage("https://www.cbr-xml-daily.ru/daily_json.js");
        float dollarCurency = 0;

        JSONObject object = new JSONObject(jsonText);
        JSONObject valute = object.getJSONObject("Valute");
        Map<String, Object> map = valute.toMap();

        for(Map.Entry<String, Object> entry : map.entrySet()) {
            String key = entry.getKey();

            if (key.equals("USD")){
                HashMap<String, Object> mp = (HashMap<String, Object>) entry.getValue();
                dollarCurency = Float.parseFloat(mp.get("Value").toString());
            }
        }

        if (dollarCurency==0) throw new WrongParsingException("Курс USD не найден");
        return dollarCurency;
    }
}
