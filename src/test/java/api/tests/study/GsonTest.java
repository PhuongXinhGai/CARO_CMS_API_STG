package api.tests.study;

import com.google.gson.Gson;
import org.testng.annotations.Test;

public class GsonTest {
    @Test
    public void testGsonConvert() {
        String json = "{\"name\":\"Phuong\",\"age\":27}";
        Gson gson = new Gson();
        User user = gson.fromJson(json, User.class);
        System.out.println("Tên: " + user.name + ", Tuổi: " + user.age);
    }

    class User {
        String name;
        int age;
    }
}
