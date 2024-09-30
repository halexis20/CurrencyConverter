import java.util.Scanner;

public class MonedaConversorApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final CurrencyConverterService converterService = new CurrencyConverterService();

    public static void main(String[] args) {
        iniciarMenu();
    }

    // Método para iniciar el menú
    public static void iniciarMenu() {
        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcionUsuario();

            if (opcion != 7) {
                try {
                    manejarOpcion(opcion);
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } while (opcion != 7);

        System.out.println("Saliendo del programa...");
        scanner.close();
    }

    // Método que muestra las opciones del menú
    private static void mostrarMenu() {
        System.out.println("*************************************");
        System.out.println("1) Dólar -> Peso Argentino");
        System.out.println("2) Peso Argentino -> Dólar");
        System.out.println("3) Dólar -> Real Brasileño");
        System.out.println("4) Real Brasileño -> Dólar");
        System.out.println("5) Dólar -> Peso Colombiano");
        System.out.println("6) Peso Colombiano -> Dólar");
        System.out.println("7) Salir");
        System.out.println("Elija una opción válida:");
        System.out.println("*************************************");
    }

    // Método que lee la opción del usuario con validación
    private static int leerOpcionUsuario() {
        int opcion = 0;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                opcion = Integer.parseInt(scanner.nextLine());
                if (opcion < 1 || opcion > 7) {
                    throw new NumberFormatException();
                }
                inputValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese una opción válida (1-7).");
            }
        }

        return opcion;
    }

    // Método que maneja la opción seleccionada y realiza la conversión
    private static void manejarOpcion(int opcion) throws Exception {
        Moneda monedaBase = null;
        Moneda monedaDestino = null;

        switch (opcion) {
            case 1:
                monedaBase = Moneda.USD;
                monedaDestino = Moneda.ARS;
                break;
            case 2:
                monedaBase = Moneda.ARS;
                monedaDestino = Moneda.USD;
                break;
            case 3:
                monedaBase = Moneda.USD;
                monedaDestino = Moneda.BRL;
                break;
            case 4:
                monedaBase = Moneda.BRL;
                monedaDestino = Moneda.USD;
                break;
            case 5:
                monedaBase = Moneda.USD;
                monedaDestino = Moneda.COP;
                break;
            case 6:
                monedaBase = Moneda.COP;
                monedaDestino = Moneda.USD;
                break;
            default:
                System.out.println("Opción no válida.");
        }

        if (monedaBase != null && monedaDestino != null) {
            realizarConversion(monedaBase, monedaDestino);
        }
    }

    // Método para realizar la conversión
    private static void realizarConversion(Moneda base, Moneda destino) throws Exception {
        System.out.print("Ingrese el valor que desea convertir: ");
        double valor = leerValorUsuario();

        double tasaCambio = converterService.obtenerTasaCambio(base, destino);
        double valorConvertido = valor * tasaCambio;

        System.out.printf("El valor %.2f [%s] corresponde al valor final de =>>> %.2f [%s]\n", valor, base, valorConvertido, destino);
    }

    // Método para leer el valor a convertir con validación
    private static double leerValorUsuario() {
        double valor = 0;
        boolean inputValido = false;

        while (!inputValido) {
            try {
                valor = Double.parseDouble(scanner.nextLine());
                if (valor <= 0) {
                    throw new NumberFormatException();
                }
                inputValido = true;
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un valor numérico válido mayor a 0.");
            }
        }

        return valor;
    }
}
