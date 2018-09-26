package dev.sangco.count.web;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/naver")
public class ApiNaverController {

    //  TODO 로거 세팅하기
    private final String clientId; //애플리케이션 클라이언트 아이디값";
    private final String clientSecret; //애플리케이션 클라이언트 시크릿값";

    public ApiNaverController(MessageSourceAccessor msa) {
        clientId = msa.getMessage("naver.clientId");
        clientSecret = msa.getMessage("naver.clientSecret");
    }

    //  TODO 익셉션 처리
    @RequestMapping("/former")
    public ResponseEntity saveFormerData() throws Exception {
        HttpURLConnection con = getHttpURLConnection();
        BufferedReader br = getBufferedReader(con, con.getResponseCode());
        StringBuffer response = setResponse(br);
        br.close();
        return new ResponseEntity(response.toString(), OK);
    }

    private StringBuffer setResponse(BufferedReader br) throws IOException {
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = br.readLine()) != null) {
            response.append(inputLine);
        }
        return response;
    }

    private BufferedReader getBufferedReader(HttpURLConnection con, int responseCode) throws IOException {
        BufferedReader br;
        if (responseCode == 200) { // 정상 호출
            br = new BufferedReader(new InputStreamReader(con.getInputStream()));
        } else {  // 에러 발생
            br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
        }
        return br;
    }

    private HttpURLConnection getHttpURLConnection() throws IOException {
        HttpURLConnection con = (HttpURLConnection) new URL(getApiURL()).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("X-Naver-Client-Id", clientId);
        con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
        return con;
    }

    private String getApiURL() throws UnsupportedEncodingException {
        return "https://openapi.naver.com/v1/search/news?query=" + URLEncoder.encode("사회적기업", "UTF-8");
    }
}
