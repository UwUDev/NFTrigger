package me.uwu;

import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class E {
    public static void main(String[] args) {
        for (int i = 1; i <= 100000; i++) {
            int finalI = i;
            if (new File("dump/" + finalI + ".png").exists()) {
                System.out.println("\u001B[33m[~] NFT n°" + finalI + " already downloaded");
                continue;
            }

            try {
                String url = getAssetUrl("0xb47e3cd837ddf8e4c57f05d70ab865de6e193bbb", finalI);
                new Thread(() -> {
                    try {

                        //System.out.println(url);
                        downloadAsset(url, finalI);
                        //System.out.println();
                        System.out.println("\u001B[32m[+] NFT n°" + finalI + " downloaded");
                    } catch (Exception e) {
                        //e.printStackTrace();
                        System.out.println("\u001B[31m[-] NFT n°" + finalI + " failed to download");
                    }
                }).start();
            } catch (Exception e) {
                //e.printStackTrace();
            }
            //new Thread(() -> {

            //}).start();
            //Thread.sleep(50);
        }
    }

    public static void downloadAsset(String url, int id) {
        try(InputStream in = new URL(url).openStream()){
            Files.copy(in, Paths.get("dump/" + id + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getAssetUrl(String applicationID, int assetID) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.opensea.io/api/v1/asset/" + applicationID + "/" + assetID + "/?format=json")
                .get()
                //.addHeader("cookie", "amp_ddd6ec=Wvs155wkK0gegZ8UrG1xT6...1fpv7ec59.1fpv8904i.i.1.j; __cf_bm=wtqVHWWkNSxtwmGXsT.A_JAfgQDb2GysTksqsrsPf6Q-1642800430-0-ATaxlb5VvzStAb/hYSXAR+hBHKcb3U2bm7ApYdpN7HXKNMQOowp/LTmvHoqDbhgq0+o8kCGQWK81akcex/ykyeQ=")
                .addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                //.addHeader("accept-encoding", "e")
                .addHeader("accept-language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7,zh-CN;q=0.6,zh-TW;q=0.5,zh;q=0.4,lt;q=0.3")
                .addHeader("cache-control", "max-age=0")
                .addHeader("if-modified-since", "Fri, 21 Jan 2022 21:31:43 GMT")
                .addHeader("sec-ch-ua", "\"(Not(A:Brand\";v=\"8\", \"Chromium\";v=\"100\", \"Google Chrome\";v=\"100\"")
                .addHeader("sec-ch-ua-mobile", "?0")
                .addHeader("sec-ch-ua-platform", "\"Windows\"")
                .addHeader("sec-fetch-dest", "document")
                .addHeader("sec-fetch-mode", "navigate")
                .addHeader("sec-fetch-site", "none")
                .addHeader("sec-fetch-user", "?1")
                .addHeader("upgrade-insecure-requests", "1")
                .addHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4844.0 Safari/537.36")
                .build();

        Response response = client.newCall(request).execute();
        String body = response.body().string();
        //System.out.println(body);
        return body.split("\"image_url\":\"")[1].split("\"")[0];
    }
}
