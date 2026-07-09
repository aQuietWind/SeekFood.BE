import com.seek.food.dto.Common.Result;
import com.seek.food.gateway.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(classes = Main.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserServiceTest {

    // WebFlux专属测试客户端，模拟访问网关
    @Autowired
    private WebTestClient webTestClient;
    private static final String User_Start = "/user";
    private WebTestClient.ResponseSpec quickTest(String url){
        return webTestClient.get()
                .uri(User_Start+url)
                .header("X-Forwarded-For","132.123.43.321")
                .exchange();
    }

    @Test
    void testTokenForbidden() {
        Result<String> result=quickTest("/register/opt?phoneNumber=12312312300").expectStatus().isOk().returnResult(Result.class).getResponseBody().blockFirst();
        System.out.println(result);
        quickTest("/register?phoneNumber=12312312300&opt="+result.getData()).expectStatus().isOk().returnResult(Result.class);
    }
    @Test
    void testLoginTokenForbidden() {
        webTestClient.get()
                .uri("/admin/api/hello")
//                .cookie("token", "1-admin-jwt-test") // 携带分割格式的token
                .exchange()
                .expectStatus().isUnauthorized(); // 无token拦截401
    }
    @Test
    void testUrlForbidden1() {
        webTestClient.get()
                .uri("/admin/api/hello")
                .cookie("token", "1-eyJhbGciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().isUnauthorized(); // 无token拦截401
    }
    @Test
    void testUrlForbidden2() {
        webTestClient.get()
                .uri("/user/register")
                .cookie("token", "1-eyJhbGciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().isUnauthorized(); // 无token拦截401
    }
    @Test
    void testUrlForbidden3() {
        webTestClient.get()
                .uri("/user/login")
                .cookie("token", "1-eyJhbG4325346354645676547ciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().is5xxServerError(); // 无token拦截401
    }
    @Test
    void testUser() {
        webTestClient.get()
                .uri("/user/register")
                .cookie("token", "1-eyJhbGciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().isUnauthorized(); // 无token拦截401
    }
    @Test
    void testUrl() {
        webTestClient.get()
                .uri("/user/login")
                .cookie("token", "1-eyJhbG4325346354645676547ciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().is5xxServerError(); // 无token拦截401
    }






}
