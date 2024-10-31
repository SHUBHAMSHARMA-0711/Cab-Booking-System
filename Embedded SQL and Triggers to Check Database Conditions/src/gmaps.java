import java.net.URI;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class gmaps {
    public String apikey="";
    public float[][] distance;
    public float[][] time;

    public void getData(String source, String destination) throws Exception{
        var url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins="+source
                +"&destinations="+destination+"&key="+apikey;
        var request  = HttpRequest.newBuilder().GET().uri(URI.create(url)).build();
        var client = HttpClient.newBuilder().build();
        var response = client.send(request, HttpResponse.BodyHandlers.ofString()).body();

        System.out.println(response);
    }

    public static void main(String[] args) throws Exception {
        gmaps g1 = new gmaps();
        g1.getData("Delhi","Jaipur");
    }
}

