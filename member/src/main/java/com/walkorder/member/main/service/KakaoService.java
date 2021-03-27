package com.walkorder.member.main.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KakaoService {

    public String getAccessTOken(String code, String RESTAPI_KEY) {

        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept-Charset", "UTF-8");

            // POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=" + RESTAPI_KEY);
            sb.append("&redirect_uri=http://localhost");
            sb.append("&code=" + code);
            bw.write(sb.toString());
            String dd = sb.toString();
            bw.flush();

            // JsonObject jsonObject = new JsonObject();
            // jsonObject.addProperty("grant_type", "authorization_code");
            // jsonObject.addProperty("client_id", RESTAPI_KEY);
            // jsonObject.addProperty("redirect_uri", "http://localhost:8080");
            // jsonObject.addProperty("code", code);

            // Map<String, Object> map = new HashMap();
            // map.put("grant_type", "authorization_code");
            // map.put("client_id", RESTAPI_KEY);
            // map.put("redirect_uri", "http://localhost:8080");
            // map.put("code", code);
            // Gson gson = new Gson();
            // JsonObject json = gson.toJsonTree(map).getAsJsonObject();
            // BufferedWriter bw = new BufferedWriter(new
            // OutputStreamWriter(conn.getOutputStream()));
            // bw.write(json.toString());
            // bw.flush();
            // System.out.println(json.toString());

            // 결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            // 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        log.info("access_Token : " + access_Token);

        return access_Token;
    }
}
