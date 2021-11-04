package com.example.springproj2;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class Springproj2ApplicationTests {

    @Test
    @DisplayName("The certificate pinning test")
    void certificateTest() {
        try {
            URL url = new URL("https://60a21d3f745cd70017576092.mockapi.io/api/v1/repos");
            URLConnection urlConnection = url.openConnection();

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlConnection;
            httpsURLConnection.connect();
            Certificate[] certificates = httpsURLConnection.getServerCertificates();
            String installedCertificate = certificates[0].toString();
            System.out.println(String.format("Installed certificate: %s",installedCertificate));

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            FileInputStream fileInputStream = new FileInputStream("lab6.cer");
            X509Certificate certificate = (X509Certificate)certificateFactory.generateCertificate(fileInputStream);
            String localCertificate = certificate.toString();
            System.out.println(String.format("Local certificate %s",localCertificate));

            assertThat(installedCertificate).isEqualTo(localCertificate);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        }
    }





}