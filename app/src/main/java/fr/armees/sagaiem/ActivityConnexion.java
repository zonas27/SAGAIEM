package fr.armees.sagaiem;


import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import fr.armees.sagaiem.api.ConnexionClient;
import fr.armees.sagaiem.databinding.ActivityConnexionBinding;

import fr.armees.sagaiem.ui.connexion.FragmentConnexion;
import fr.armees.sagaiem.ui.connexion.FragmentLoad;
import fr.armees.sagaiem.ui.connexion.ViewModelConnexion;
import fr.armees.sagaiem.utils.Constantes;
import fr.armees.sagaiem.utils.StagiaireUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class ActivityConnexion extends AppCompatActivity {

    private ActivityConnexionBinding binding;
    private ViewModelConnexion viewModelConnexion;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityConnexionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModelConnexion = new ViewModelProvider(this).get(ViewModelConnexion.class);

        SharedPreferences sharedPreferences = getSharedPreferences(Constantes.SAGAEM_PREFERENCES.name(), MODE_PRIVATE);

        if ((sharedPreferences.contains(Constantes.CONNECTION_TOKEN.name())) || (sharedPreferences.contains(Constantes.USER_LOGIN.name()) && sharedPreferences.contains(Constantes.USER_PWD.name()))) {
            connect();
        }else {
            afficherFragmentConnexion(new Bundle());
        }


        Map map = new HashMap();
        String token = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJiYWYiLCJyb2xlIjoiQkFGIiwidXNlciI6MSwicGx1cyI6MCwiaWF0IjoxNjU3ODY4NjI2LCJleHAiOjE2NTc5NTUwMjZ9.Vq1-rEk5etRsTrQEqryKKSV-ntOFKqy90l3uAz-Q3lX2iPnIuhhCKqR9_8FeSjs4aUgS6C2Sz_cB6xNuG2dKYA";

        map = StagiaireUtils.ParseStagiaireToken(token);


        // This observer Observe the viewModelConnexion.canConnect
        // the boolean value is changed in viewModelConnexion.login()
        // viewModelConnexion.login() is called by fragmentConnexion.OnClickListener or fragmentConnexion.OnActionListener
        // ne marche pas pour une raison inconnue, je suis en train de voir.
        viewModelConnexion.canConnect.observe(this, canConnect -> {
            if(canConnect){
                connect();
            }
        });



    }

    private void connect() {

        try {
            Integer code = 0;
            ExecutorService executor = Executors.newSingleThreadExecutor();
            do {
                Future<Integer> future = executor.submit(new ConnexionClient("", getSharedPreferences(Constantes.SAGAEM_PREFERENCES.name(), MODE_PRIVATE), viewModelConnexion.keepConnection()));
                code = future.get();
            } while (code == 408);

            switch (code) {
                case 200:
                case 201:
                    startActivity(new Intent(this, ActivityNavigation.class));

                case 400:
                case 404:
                    Toast.makeText(this, String.format("Connexion au serveur distant échoué , code de réponse : %d", code), Toast.LENGTH_LONG).show();
                    return;
                case 401:
                    afficherFragmentConnexion(new Bundle());
                    Toast.makeText(this, "Connexion refusée, veuillez vérifer vos identifiants", Toast.LENGTH_LONG).show();
            }

        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private void afficherFragmentLoad(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, FragmentLoad.class, savedInstanceState)
                .commit();

    }


    private void afficherFragmentConnexion(Bundle savedInstanceState) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container_view, FragmentConnexion.class, savedInstanceState)
                .commit();
    }



}