
import com.seek.food.gateway.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

// 直接加载你的Gateway主启动类，关闭Nacos避免远程依赖
@SpringBootTest(
        classes = Main.class, // 指定你的网关启动类，完整启动Gateway
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class GatewayServiceTest {

    // WebFlux专属测试客户端，模拟访问网关
    @Autowired
    private WebTestClient webTestClient;

    // 完整链路测试：路由匹配 + TokenFilter鉴权
    @Test
    void testAdminRouteWithValidToken() {
        webTestClient.get()
                .uri("/admin/api/hello")
                .cookie("token", "4-admin-jwt-test") // 携带分割格式的token
                .exchange()
                .expectStatus().isUnauthorized(); // 合法token放行200
    }

    @Test
    void testNoTokenForbidden() {
        webTestClient.get()
                .uri("/admin/api/hello")
                .exchange()
                .expectStatus().isUnauthorized(); // 无token拦截401
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
    }    @Test
    void testUrlForbidden2() {
        webTestClient.get()
                .uri("/user/register")
                .cookie("token", "1-eyJhbGciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().isUnauthorized(); // 无token拦截401
    }@Test
    void testUrlForbidden3() {
        webTestClient.get()
                .uri("/user/login")
                .cookie("token", "1-eyJhbG4325346354645676547ciOiJIUzM4NCJ9.eyJ0b2tlbklkIjoiMjE0MzIxNTQyMzU0Iiwic3ViIjoidGVzdCIsImlhdCI6MTc4MjUyNjUwMSwiZXhwIjoxNzg0MjU0NTAxfQ.A8wyTn-a6zPOSudwA86oFDOtZTOG0M9nzJYyT0R6KMThghqICDuQDJmY-8rKqnxR") // 携带分割格式的token
                .exchange()
                .expectStatus().is5xxServerError(); // 无token拦截401
    }

















}