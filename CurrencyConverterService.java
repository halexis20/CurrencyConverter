import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyConverterService {
    private static final String API_KEY = "249bfd391c02ff41d088bf44";
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/pair/";

    // Método que realiza la llamada a la API y retorna la tasa de cambio
    public double obtenerTasaCambio(Moneda base, Moneda destino) throws Exception {
        String urlStr = API_URL + base + "/" + destino;
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        // Verificar si la conexión fue exitosa
        int status = conn.getResponseCode();
        if (status != 200) {
            throw new Exception("Error al conectar con la API. Código de estado: " + status);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        JSONObject jsonResponse = new JSONObject(content.toString());
        return jsonResponse.getDouble("conversion_rate");
    }
}
