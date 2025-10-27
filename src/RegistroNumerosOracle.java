import java.sql.*;
import java.util.Scanner;

public class RegistroNumerosOracle {

    private static final String URL = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private static final String USER = "system"; // usuario BD
    private static final String PASS = "password"; // contraseña

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== registro de numeros en Oracle 19c ===");
        System.out.println("Ingresa numeros  y separalos por espacios (ej: 5 10 25 100)");
        String input = sc.nextLine();

        String[] numeros = input.split("\\s+");

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {

            String sql = "INSERT INTO NUMEROS_REGISTRADOS (NUMERO) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            for (String n : numeros) {
                try {
                    int numero = Integer.parseInt(n);
                    stmt.setInt(1 , numero);
                    stmt.executeUpdate();
                    System.out.println("✅ Registrado: " + numero);
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Valor no válido: " + n);
                }
            }
            System.out.println("\n📌 Todos los valores válidos fueron guardados en la BD.");
        } catch (SQLException e) {
            System.out.println("❌ Error al conectar o guardar: " + e.getMessage());
        }
        sc.close();
    }
}