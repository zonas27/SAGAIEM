package fr.armees.sagaiem;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import fr.armees.sagaiem.databinding.ActivityNavigationBinding;
import fr.armees.sagaiem.entities.*;
import fr.armees.sagaiem.fakedata.Notes;
import fr.armees.sagaiem.ui.ViewModelShared;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.UUID;

public class ActivityNavigation extends AppCompatActivity {

    private ActivityNavigationBinding binding;

    private ViewModelShared viewModelShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModelShared = new ViewModelProvider(this).get(ViewModelShared.class);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.itemAccueil_menu, R.id.itemNotes_menu, R.id.itemClassement_menu)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_navigation);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);


        loadStagaire();

    }


    private void loadStagaire(){
        ArrayList<Evaluation> evaluations = new ArrayList<Evaluation>();
        evaluations.add(new Evaluation(2, 20f, LocalDateTime.of(2022,07,01,00,00), true));
        evaluations.add(new Evaluation(2, 12f, LocalDateTime.of(2022,07,02,00,00), true));
        evaluations.add(new Evaluation(2, 15f, LocalDateTime.of(2022,07,03,00,00), true));

        ArrayList<Matiere> matieres = new ArrayList<>();
        Matiere matiere = new Matiere("SQL",evaluations,2);
        matiere.setCoefEvaluationsEffectuees();
        matiere.setMoyenneMatiere();
        matieres.add(matiere);
        matiere = new Matiere("JAVA",evaluations,4);
        matiere.setCoefEvaluationsEffectuees();
        matiere.setMoyenneMatiere();
        matieres.add(matiere);
        matiere = new Matiere("JEE",evaluations,6);
        matiere.setCoefEvaluationsEffectuees();
        matiere.setMoyenneMatiere();
        matieres.add(matiere);


        ArrayList<Uv> uvs = new ArrayList<>();
        uvs.add(new Uv(1,matieres,LocalDateTime.now()));
        uvs.add(new Uv(2,matieres,LocalDateTime.now()));
        uvs.add(new Uv(3,matieres,LocalDateTime.now()));

        //JSONObject data = future.get();
//        Stagiaire stagiaire = new Stagiaire("Toto",randomString(),"1/10", new Stage("J010",uvs));
        Stagiaire stagiaire = new Stagiaire("Durand","Pierre","1/10", new Stage("J010", Notes.getData()));

        viewModelShared.set(stagiaire);
    }

    private String randomString(){
        return UUID.randomUUID().toString();
    }
}