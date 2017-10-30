package info.androidhive.firebase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Admin on 08/03/2017.
 */

public class APICatcher {
    private String json = null;
    private String link = null;

    public String getJSONFile(int movieId)
    {
        try {
            //MainActivity idObject = new MainActivity();
            //int movieId = idObject.getMovieId();

            String link = "https://api.themoviedb.org/3/movie/" + String.valueOf(movieId) + "?language=en-US&api_key=32f5f1d0ff7289475163c1cfb3ec9061";
            URL url = new URL(link);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            bufferedReader.close();
            json = new String(stringBuilder.toString());

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}