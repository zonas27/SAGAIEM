package fr.armees.sagaiem.api;

import android.content.SharedPreferences;
import fr.armees.sagaiem.utils.Constantes;
import fr.armees.sagaiem.utils.DeCryptor;
import fr.armees.sagaiem.utils.EnCryptor;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.concurrent.Callable;

public class ConnexionClient implements Callable<Integer> {

    private String url;
    private SharedPreferences sharedPreferences;

    public ConnexionClient(String url, SharedPreferences sharedPreferences,boolean resterConnecte) {
        this.url = url;
        this.sharedPreferences = sharedPreferences;
    }

    @Override
    public Integer call()  {

        Integer responseCode = 0;
        try{
            DeCryptor deCryptor = new DeCryptor();
            deCryptor.initKeyStore();
            EnCryptor enCryptor = new EnCryptor();
/*            HttpURLConnection connexion = (HttpURLConnection) new URL(url).openConnection();
            connexion.setRequestMethod("GET");
            connexion.setConnectTimeout(10000);*/

            if (sharedPreferences.contains(Constantes.CONNECTION_TOKEN.name())) {
                String connexionToken = deCryptor.decryptData(Constantes.CONNECTION_TOKEN.name(),
                        sharedPreferences.getString(Constantes.CONNECTION_TOKEN.name(), "").getBytes(StandardCharsets.UTF_8),
                        enCryptor.getIv());

/*                connexion.setRequestProperty(Constantes.CONNECTION_TOKEN.name(), connexionToken);
                responseCode =  connexion.getResponseCode();*/

                wait(5000);
                responseCode = 200;

            }
            else if (sharedPreferences.contains(Constantes.USER_LOGIN.name()) && sharedPreferences.contains(Constantes.USER_PWD.name())) {

                String login = deCryptor.decryptData(Constantes.USER_LOGIN.name(),
                        sharedPreferences.getString(Constantes.USER_LOGIN.name(), "").getBytes(StandardCharsets.UTF_8),
                        enCryptor.getIv());

                String pwd = deCryptor.decryptData(Constantes.USER_PWD.name(),
                        sharedPreferences.getString(Constantes.USER_PWD.name(), "").getBytes(StandardCharsets.UTF_8),
                        enCryptor.getIv());

                sharedPreferences.edit().putString(Constantes.CONNECTION_TOKEN.name(),login+pwd);



                //responseCode =  connexion.getResponseCode();
                wait(5000);
                responseCode = 201;

            }
            else{
                responseCode = 401;
            }
        }
        catch (ProtocolException e) {throw new RuntimeException(e);}
        catch (MalformedURLException e) {throw new RuntimeException(e);}
        catch (InvalidAlgorithmParameterException e) {throw new RuntimeException(e);}
        catch (UnrecoverableEntryException e) {throw new RuntimeException(e);}
        catch (NoSuchPaddingException e) {throw new RuntimeException(e);}
        catch (IllegalBlockSizeException e) {throw new RuntimeException(e);}
        catch (CertificateException e) {throw new RuntimeException(e);}
        catch (NoSuchAlgorithmException e) {throw new RuntimeException(e);}
        catch (KeyStoreException e) {throw new RuntimeException(e);}
        catch (IOException e) {throw new RuntimeException(e);}
        catch (BadPaddingException e) {throw new RuntimeException(e);}
        catch (InvalidKeyException e) {throw new RuntimeException(e);}

        finally {
            return responseCode;
        }
        }

    }
