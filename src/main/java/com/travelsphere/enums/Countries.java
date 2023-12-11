package com.travelsphere.enums;

import java.util.List;
import java.util.Map;

public class Countries {
    public static List<String> vietnam =
            List.of("Thanh pho Ho Chi Minh", "Ha Noi", "Turan");
    public static List<String> thailand =
            List.of("Bangkok", "Phuket", "Chiang Mai");
    public static List<String> malaysia =
            List.of("Kuala Lumpur", "George Town", "Kota Kinabalu");
    public static Map<String,List<String>> all =
            Map.of("Vietnam", vietnam, "Thailand", thailand, "Malaysia", malaysia);
}
