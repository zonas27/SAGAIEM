package fr.armees.sagaiem.ui.connexion;

import android.content.SharedPreferences;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Patterns;

import fr.armees.sagaiem.R;
import fr.armees.sagaiem.utils.Constantes;
import fr.armees.sagaiem.utils.DeCryptor;
import fr.armees.sagaiem.utils.EnCryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.*;

public class ViewModelConnexion extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();

    public MutableLiveData<Boolean> canConnect;

    public ViewModelConnexion() {
        canConnect = new MutableLiveData<>(false);
    }

    public LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }



    Boolean login(String username, String password,SharedPreferences sharedPreferences) {
        // can be launched in a separate asynchronous job
        try {

            //  writing users logins in sharedPreference, encrypted whith the android keystore,

            EnCryptor enCryptor = new EnCryptor();
            DeCryptor deCryptor = new DeCryptor();
            deCryptor.initKeyStore();
            byte[] loginChiffre = enCryptor.encryptText(Constantes.USER_LOGIN.name(), MessageDigest.getInstance("SHA-512")
                    .digest(username.getBytes(StandardCharsets.UTF_8)));

            byte[] passwordChiffre = enCryptor.encryptText(Constantes.USER_PWD.name(), MessageDigest.getInstance("SHA-512")
                    .digest(password.getBytes(StandardCharsets.UTF_8)));

            sharedPreferences.edit().putString(Constantes.USER_LOGIN.name(), loginChiffre.toString());
            sharedPreferences.edit().putString(Constantes.USER_PWD.name(), passwordChiffre.toString());


            // setting true to canConnect,
            //  I tested postValue, but it dont seem to work either
            //  I guess it's logic since everything play in the main thread

            canConnect.postValue(true);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    public Boolean keepConnection(){
        return loginFormState.getValue().getStayConnected();
    }

    public LiveData<Boolean> getCanConnect(){
        return canConnect;
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        return true;
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}