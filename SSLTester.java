import java.net.URL;
import java.io.IOException;
import java.security.cert.Certificate;
import javax.net.ssl.HttpsURLConnection;
import java.security.cert.X509Certificate;

public class SSLTester {

    public static void main(String[] args) {

        new SSLTester().run();
    }

    private void printServerCert(Certificate[] certs) {

        Certificate serverCert = certs[0];
        String[] kv;
        String key;
        String value;

        System.out.println("Cert Type: " + serverCert.getType());
        if(serverCert instanceof X509Certificate) {
            for(String kvPair: ((X509Certificate) serverCert).getSubjectX500Principal().getName().split(",")) {
                kv = kvPair.split("=");
                key = kv[0];
                value = kv[1];

                if(key.equals("CN")) {
                    System.out.println("CN: " + value);
                }
            }
        }
    }

    private void run() {

        String https_url = "https://www.google.com/";
        Certificate[] certs;
        URL url;

        try {
            url = new URL(https_url);
            HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
            System.out.println("URL: " + https_url);
            System.out.println("Response Code: " + conn.getResponseCode());
            System.out.println("Cipher Suite: " + conn.getCipherSuite());
            System.out.println();

            certs = conn.getServerCertificates();
            printServerCert(certs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
