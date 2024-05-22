import com.alura.screenmatch.excepcion.ErrorConvercionDuracionExcepcion;
import com.alura.screenmatch.modelos.Titulo;
import com.alura.screenmatch.modelos.TituloOmdb;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalBusqueda {
    public static void main(String[] args) throws IOException, InterruptedException {

        List<Titulo> titulos = new ArrayList<>();
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();

        while (true){

            Scanner lectura = new Scanner(System.in);
            System.out.println("Esciba el nombre de una pelicula: ");
            var busqueda = lectura.nextLine();
            String direccion = "http://www.omdbapi.com/?t="+busqueda.replace(" ", "+")+
                    "&apikey=46c89f02";

            if (busqueda.equalsIgnoreCase("salir")){
                break;
            }


            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(direccion))
                        .build();

                HttpResponse<String> response = client
                        .send(request, HttpResponse.BodyHandlers.ofString());

                String json = response.body();
                System.out.println(json);

                TituloOmdb miTituloOmdb = gson.fromJson(json, TituloOmdb.class);
                System.out.println(miTituloOmdb);

                Titulo miTitulo = new Titulo(miTituloOmdb);
                System.out.println("Titulo ya convertido: " + miTitulo);

                titulos.add(miTitulo);

            } catch (NumberFormatException e) {
                System.out.println("Ocurrio un error en el programa ");
                System.out.println(e.getMessage());
            } catch (IllegalArgumentException e){
                System.out.println("Error en la URI, verifique la direcccion URL");
            } catch (ErrorConvercionDuracionExcepcion e){
                System.out.println(e.getMessage());
            }
        }
        System.out.println(titulos);

        FileWriter escritura = new FileWriter("titulos.json");
        escritura.write(gson.toJson(titulos));
        escritura.close();

        System.out.println("Finalizacion del programa!");
    }
}