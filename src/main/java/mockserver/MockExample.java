package mockserver;


import org.mockserver.client.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.verify.VerificationTimes;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

/**
 * @Author GrayCat.
 * @date 2022/06/08 14:08
 */

public class MockExample {
    public static void main( String[] args){
        System.out.println("mock server running...");
        /**  启动本地 MockServer 实例并向其发出 HTTP 请求 , 启动了mockserver和客户端
         *      启动一个 mock server。
         *      前两个参数是真实服务的 ip 地址和端口号， mock server 一旦发现有请求没有命中事前定义的规则，
         *      那么就会转发给真实的服务处理。 而第三个参数就是 mock server 自己的端口号了。
         * */
        ClientAndServer server = new ClientAndServer("localhost", 8080, 1080);
        server.when(
                request()
                        .withMethod("GET")
                        .withPath("/hello")
        ).respond(
                response()
                        .withBody("mock success.")
        );

        /**
         *  以下是另一种构造规则的方法。向远程 MockServer 实例发出 HTTP 请求
         *  这个方法没有启动mock服务器
         *  第一个参数指定哪里的mock服务器，第二个是端口
         * */
        new MockServerClient("192.168.1.45", 1080).when(
                request()
                        .withMethod("GET")
                        .withPath("/receive")
                ).respond(
                        response().withBody("mockserver client respone.")
        );
    }

     /**
      *     初此之外，mockserver还有很多强大的功能，eg：验证收到多少次重复请求；
      * */
    public static void initMockServer(){
        new MockServerClient("localhost", 1080).verify(
                request().withPath("/receive/amount"), VerificationTimes.atLeast(2));
    }

    /** 还有一种通过命令行运行mock服务的方法，可以在保存在运行期间添加的配置，方法如下：
         * 1. persistExpectations配置属性需要设置为true
         * 2. 用于持久化期望的文件路径可以使用persistedExpectationsPath配置属性进行配置
         * 3. 期望应该使用initialJsonPath配置属性自动初始化
     * */



}
