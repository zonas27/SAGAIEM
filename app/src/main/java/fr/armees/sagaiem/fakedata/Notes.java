package fr.armees.sagaiem.fakedata;

import fr.armees.sagaiem.entities.Evaluation;
import fr.armees.sagaiem.entities.Matiere;
import fr.armees.sagaiem.entities.Uv;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Notes {

    public static ArrayList<Uv> getData(){
        ArrayList<Uv> data = new ArrayList<>();

        ArrayList<Evaluation> java = new ArrayList<>();
        java.add(new Evaluation(1, 12F, LocalDateTime.now(),true));
        java.add(new Evaluation(1, 14.5F, LocalDateTime.now(),true));
        java.add(new Evaluation(1, 16F, LocalDateTime.now(),true));
        ArrayList<Evaluation> algo = new ArrayList<>();
        algo.add(new Evaluation(1, 14F, LocalDateTime.now(),true));
        algo.add(new Evaluation(1, 16F, LocalDateTime.now(),true));
        ArrayList<Evaluation> linux = new ArrayList<>();
        linux.add(new Evaluation(1, 11F, LocalDateTime.now(),true));
        ArrayList<Evaluation> reseau = new ArrayList<>();
        reseau.add(new Evaluation(1, 13.5F, LocalDateTime.now(),true));
        ArrayList<Evaluation> archi = new ArrayList<>();
        archi.add(new Evaluation(1, 8F, LocalDateTime.now(),true));

        Matiere mJava =new Matiere("JAVA",java,8);

        Matiere mAlgo = new Matiere("ALGO",algo,6);

        Matiere mLinux = new Matiere("LINUX",linux,1);

        Matiere mReseau = new Matiere("RESEAU",reseau,2);

        Matiere mArchi = new Matiere("ARCHI", archi, 2);

        ArrayList<Matiere> uv1 = new ArrayList<>();
        uv1.add(mJava);
        uv1.add(mAlgo);
        uv1.add(mLinux);
        uv1.add(mReseau);
        ArrayList<Matiere> uv2 = new ArrayList<>();
        uv2.add(mArchi);
        uv2.add(new Matiere("BIG DATA", new ArrayList<>(), 2));
        uv2.add(new Matiere("ANALYSE", new ArrayList<>(), 6));

        data.add(new Uv(1,uv1, LocalDateTime.now()));
        data.add(new Uv(2,uv2, LocalDateTime.now()));

        data.stream().forEach(u -> {
            u.setMoyenneUv();
            u.setCoefMatieresEffectuees();
        });

        return data;
    }

}
