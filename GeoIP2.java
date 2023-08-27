import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Location;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;

public class GeoIPExample {

    public static void main(String[] args) throws IOException, GeoIp2Exception {
        // Đường dẫn tới tệp GeoIP2 City database (đã được tải về)
        File database = new File("path/to/GeoIP2-City.mmdb");

        // Khởi tạo DatabaseReader với tệp database
        DatabaseReader reader = new DatabaseReader.Builder(database).build();

        // Đọc danh sách địa chỉ IP từ tệp
        File ipFile = new File("path/to/ip_addresses.txt");
        Scanner scanner = new Scanner(ipFile);
        while (scanner.hasNextLine()) {
            String ipAddress = scanner.nextLine().trim();
            InetAddress ip = InetAddress.getByName(ipAddress);

            // Truy vấn thông tin địa lý của địa chỉ IP
            CityResponse response = reader.city(ip);
            City city = response.getCity();
            Location location = response.getLocation();

            // In thông tin địa lý
            System.out.println("IP Address: " + ipAddress);
            System.out.println("City Name: " + city.getName());
            System.out.println("Country: " + response.getCountry().getName());
            System.out.println("Latitude: " + location.getLatitude());
            System.out.println("Longitude: " + location.getLongitude());
            System.out.println("------------------------------------");
        }
    }
}
