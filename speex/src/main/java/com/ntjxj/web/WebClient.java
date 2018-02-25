package com.ntjxj.web;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lixiaodong on 2018/2/25.
 */

public class WebClient {

    private static String host = "http://58.221.183.194/video_phone";

    public static String uploadTest(String id, byte[] b) {
        String result = "";
        String url = host + "/services/video/uploadByte?id="
                + id;
        try {
            URL u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("POST");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.setDoOutput(true);
            OutputStream out = conn.getOutputStream();
            out.write(b);
            out.close();
            conn.connect();
            int code = conn.getResponseCode();
            if (code != 200)
                return "" + code;
            InputStream in = conn.getInputStream();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));
            String s = null;
            while ((s = reader.readLine()) != null) {
                result += s;
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public static int testDownload(ByteArrayOutputStream bao, String who, String my) {
        int total = 0;
        String url = host + "/download?id=" + my + "&who=" + who;
        URL u;
        try {
            u = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) u.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(30000);
            conn.setReadTimeout(30000);
            conn.connect();
            int code = conn.getResponseCode();
            if (code != 200)
                return -1;
            InputStream in = conn.getInputStream();
            BufferedInputStream reader = new BufferedInputStream(in);
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = reader.read(b)) > 0) {
                bao.write(b, 0, len);
                total += len;
            }
            in.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return total;
    }
}
