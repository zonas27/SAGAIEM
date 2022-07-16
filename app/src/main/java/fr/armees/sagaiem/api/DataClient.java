package fr.armees.sagaiem.api;

import android.content.SharedPreferences;
import fr.armees.sagaiem.utils.Constantes;
import fr.armees.sagaiem.utils.DeCryptor;
import fr.armees.sagaiem.utils.EnCryptor;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;

public class DataClient implements Callable<JSONObject> {

    private String url;
    private SharedPreferences sharedPreferences;

    public DataClient(String url, SharedPreferences sharedPreferences) {
        this.url = url;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public JSONObject call() throws Exception {
        DeCryptor deCryptor = new DeCryptor();
        deCryptor.initKeyStore();
        EnCryptor enCryptor = new EnCryptor();
        if (sharedPreferences.contains(Constantes.CONNECTION_TOKEN.name()) && deCryptor.isKeyExiste(Constantes.CONNECTION_TOKEN.name())) {
            String connexionToken = deCryptor.decryptData(Constantes.CONNECTION_TOKEN.name(), enCryptor.getEncryption(), enCryptor.getIv());
            HttpURLConnection connexion = (HttpURLConnection) new URL(url).openConnection();

            connexion.setRequestMethod("GET");
            connexion.setRequestProperty("Content-Type", "application/json");
            connexion.setRequestProperty(Constantes.CONNECTION_TOKEN.name(), connexionToken);
            connexion.setConnectTimeout(5000);

            connexion.getResponseCode();

            FileInputStream fileInputStream = new FileInputStream(url);

            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            JSONObject json = new JSONObject();

            //connexion.disconnect();

            return json;

        } else {
            throw new Exception("Le token ou l'encryption key n'existe pas");
        }




    }
}
