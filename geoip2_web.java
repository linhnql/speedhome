import com.maxmind.geoip2.WebServiceClient;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class GeoIPWebServiceExample {

    public static void main(String[] args) throws IOException, GeoIp2Exception {
        // Thay đổi thành thông tin của tài khoản GeoIP2 của bạn
        String accountId = "YOUR_ACCOUNT_ID";
        String licenseKey = "YOUR_LICENSE_KEY";

        // Khởi tạo WebServiceClient
        WebServiceClient client = new WebServiceClient.Builder(accountId, licenseKey).build();

        // Đọc danh sách địa chỉ IP từ tệp
        File ipFile = new File("path/to/ip_addresses.txt");
        Scanner scanner = new Scanner(ipFile);
        while (scanner.hasNextLine()) {
            String ipAddress = scanner.nextLine().trim();
            InetAddress ip = InetAddress.getByName(ipAddress);

            // Truy vấn thông tin địa lý của địa chỉ IP
            CityResponse response = client.city(ip);

            // In thông tin địa lý
            System.out.println("IP Address: " + ipAddress);
            System.out.println("City Name: " + response.getCity().getName());
            System.out.println("Country: " + response.getCountry().getName());
            System.out.println("Latitude: " + response.getLocation().getLatitude());
            System.out.println("Longitude: " + response.getLocation().getLongitude());
            System.out.println("------------------------------------");
        }
    }
}
