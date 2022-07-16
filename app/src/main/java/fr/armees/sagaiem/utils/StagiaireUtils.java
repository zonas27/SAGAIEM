package fr.armees.sagaiem.utils;


import com.google.gson.Gson;
import fr.armees.sagaiem.entities.Stagiaire;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class StagiaireUtils {

    public static Map<String,String> ParseStagiaireToken(String token) {


        String tkn = token.split(" ")[1];
        tkn = String.format("%s.%s.", tkn.split("\\.")[0], tkn.split("\\.")[1]);
        //String[] logins64 = tkn.split("\\.");

        Jwt<Header, Claims> t = Jwts.parser().parse(tkn);

        Map<String,String> infos = new HashMap<>();

        infos.put(Constantes.NAME_STAGIAIRE.name(),t.getBody().getSubject());
        infos.put(Constantes.ID_STAGIAIRE.name(), String.valueOf(Long.parseLong(t.getBody().get("user").toString().split("\\.")[0])));
        infos.put(Constantes.ID_STAGE.name(),String.valueOf(Long.parseLong(t.getBody().get("plus").toString().split("\\.")[0])));


        return infos;
    }


    public static Stagiaire StagiaireFromJSON(JSONObject jsonObject){
        return null;
    }

}
