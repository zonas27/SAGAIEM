package fr.armees.sagaiem.utils;

import fr.armees.sagaiem.entities.Evaluation;
import fr.armees.sagaiem.entities.Matiere;

import java.util.*;

public class MapUtils {


    public static <K, V extends Comparable<? super V>> LinkedHashMap<K, V> sortByValue(Map<K, V> unsortMap) {

        List<Map.Entry<K, V>> list = new LinkedList<>();
        for (Map.Entry<K,V> entry: unsortMap.entrySet()) {
            list.add(entry);
        }

        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        LinkedHashMap<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;

    }

}
