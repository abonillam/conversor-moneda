import com.google.gson.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class Main {
    public static Double Conversor(String moneda1, String moneda2, Double monto) throws IOException, InterruptedException {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setPrettyPrinting()
                .create();
        
        //String url_str = "https://v6.exchangerate-api.com/v6/aa99d03380a7b202f5e5fe48/latest/USD";
        //String url_str = "https://v6.exchangerate-api.com/v6/aa99d03380a7b202f5e5fe48/pair/"+moneda1+"/"+moneda2+"/"+monto;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/aa99d03380a7b202f5e5fe48/pair/" + moneda1 + "/" + moneda2 + "/" + monto))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String json = response.body();

        ApiResponse respuesta = gson.fromJson(json, ApiResponse.class);
        //System.out.println("Equivalencia: " + respuesta.conversionResult());
        //System.out.println("Estado del servidor: " + response.statusCode());
        //System.out.println("Respuesta del servidor: " + response.body());

        return respuesta.conversionResult();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        int opcion = 0;
        String tipoMonedaOrigen = "";
        String tipoMonedaDestino = "";
        double montoOrigen = 0.00;
        double montoDestino;

        String menu = """
                ****************************************
                Bienvenido al conversor de monedas:
                ===================================
                * 1- Dolar ==> Peso Argentino
                * 2- Peso Argentino ==> Dolar
                * 3- Dolar ==> Real Brasileño
                * 4- Real Brasileño ==> Dolar
                * 5- Dolar ==> Peso Colombiano
                * 6- Peso Colombiano ==> Dolar
                * 7- Salir
                ****************************************
                Elija una opción válida:
                """;

        Scanner teclado = new Scanner(System.in);
        

        while (opcion != 7){
            System.out.println(menu);
            opcion = teclado.nextInt();

            if ((opcion != 7)) {
                System.out.println("Ingrese el monto a convertir: ");
                montoOrigen = teclado.nextDouble();
            }

            switch (opcion){
                case 1:
                    tipoMonedaOrigen = "USD";
                    tipoMonedaDestino = "ARS";
                    System.out.println("Convirtiendo de Dolar a Peso Argentino: ");
                    break;
                case 2:
                    tipoMonedaOrigen = "ARS";
                    tipoMonedaDestino = "USD";
                    System.out.println("Convirtiendo de Peso Argentino a Dolar: ");
                    break;
                case 3:
                    tipoMonedaOrigen = "USD";
                    tipoMonedaDestino = "BRL";
                    System.out.println("Convirtiendo de Dolar a Real Brasileño: ");
                    break;
                case 4:
                    tipoMonedaOrigen = "BRL";
                    tipoMonedaDestino = "USD";
                    System.out.println("Convirtiendo de Real Brasileño a Dolar: ");
                    break;
                case 5:
                    tipoMonedaOrigen = "USD";
                    tipoMonedaDestino = "COP";
                    System.out.println("Convirtiendo de Dolar a Peso Colombiano: ");
                    break;
                case 6:
                    tipoMonedaOrigen = "COP";
                    tipoMonedaDestino = "USD";
                    System.out.println("Convirtiendo de Peso Colombiano a Dolar: ");
                    break;
                case 7:
                    System.out.println("¡Gracias por usar el conversor de monedas\nVuelva pronto!....");
                    break;
                default:
                    System.out.println("¡Opción inválida!1\n");
                    break;
            }
            if (opcion>=1 && opcion<=6){
                montoDestino = Conversor(tipoMonedaOrigen, tipoMonedaDestino, montoOrigen);
                System.out.println(tipoMonedaOrigen + " " + montoOrigen + " equivalen a " + tipoMonedaDestino + " " + montoDestino + "\n");
            }
        }
    }
}