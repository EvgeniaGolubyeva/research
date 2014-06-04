package research.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * @author Evgenia
 */
public class HttpUtils {

    public static String sendRequest(URI uri) {

        try {
            HttpURLConnection connection = (HttpURLConnection) uri.toURL().openConnection();
            InputStream responseStream = connection.getInputStream();

            //parse response
            BufferedReader br = new BufferedReader(new InputStreamReader(responseStream));

            StringBuilder res = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                res.append(line);
            }
            return res.toString();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
