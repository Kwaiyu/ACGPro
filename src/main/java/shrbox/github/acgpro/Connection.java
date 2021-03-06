package shrbox.github.acgpro;

import net.mamoe.mirai.console.plugins.Config;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

public class Connection {
    public static String getURL(String keyword, Boolean isr18) {
        Config config = Main.config;
        try {
            URL url;
            String address = "https://api.lolicon.app/setu/?num=10&apikey=";
            String apikey = config.getString("apikey");
            if (keyword.equals("")) {
                if (isr18) {
                    url = new URL(address + apikey + "&r18=2");
                } else {
                    url = new URL(address + apikey);
                }
            } else {
                if (isr18) {
                    url = new URL(address + apikey + "&keyword=" + keyword + "&r18=2");
                } else {
                    url = new URL(address + apikey + "&keyword=" + keyword);
                }
            }
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlConnection;
            if (httpURLConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "";
            }
            InputStream inputStream = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String reading, content = "";
            while((reading = bufferedReader.readLine()) != null) {
                content = content.concat(reading);
            }
            content = content.replaceFirst("\n|\r", "");
            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
            return content;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
