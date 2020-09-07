# Spring microservice basic
## Developent with springboot application  

+ Customerize configuration  
    - Sử dụng name của file config khi truyền dưới dạng tham số ( java -jar app.jar --spring.config.name="The name of file config")
  
    - Dùng location nơi chưa thư mục của file config (java -jar app.jar --spring.config.location=classpath:/example.properties)

## Advanced configuration settings

Cấu hình của eureka bao gồm 3 phần :

+ Server: Bao gồm tất cả nhưng đặc tính với tiền tố là eureka.server.*. . Nó nằm trong class **EurekaServerConfigBean**

+ Client: Là đặc tính đầu tiên của 2 phần nằm trong Eureka Client. Nó có nhiệm vụ dành cho việc cấu hình để client có thể query registry để định vị các service khác. Những đặc tính này sẽ có tiền tố là eureka.client.*. và nằm trong **EurekaClientConfigBean** class.

+ Instance: nó là các instances của các client trong eureka client như port , name. có tiền tố là eureka.instance.*. Và nằm trong EurekaInstanceConfigBean class.

## Refreshing the registry

Clients sends heartbeats tới server default cứ 30's một lần. Nếu server không nhận được message , sau 90s trước khi nó remove instance khỏi registry và do vậy việc loại bỏ traffic sẽ được gửi tới instance đó. Sử dụng thuộc tính **leaseExpirationDurationInSeconds** = 90  và **leaseRenewalIntervalInSeconds** = 30 properties. Hai tham số này được set dành cho client.  

## Changing the instance identificator

Các instances trong Eureka server được nhóm theo tên, nhưng mỗi chúng phỉa gửi một unique ID để server có thể nhận ra chúng. Có thể bạn lưu ý rằng **instanceId** được hiển thị trên dashboard trong một nhóm các service nằm trong 1 group ở cột Status. Spring cloud Eureka tự động tạo ra như sau :
**${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance}**
Định danh này có thể được thay đổi bằng thuộc tính **eureka.instance.instanceID** .

## Preferring the ip address

 Dành cho mạng sử dụng DNS , và khi đó tất cả các instances hiển thị trên dashboard của Eureka sẽ là **instanceId** khi ta setting thuộc tính **eureka.instance.preferIpAddress** là **true** . Khi chúng ta sử dụng ip là cách để định vị các service trong network chúng ta sẽ có thể gặp một số vấn đề nhưng việc quản lý mạng trọng tổ chức được chia thành nhiều mạng khác nhau, việc giao tiếp giữa các server phải thông qua network interface  để lưạ chọn đúng interface chúng ta có thể định nghĩa danhh sách nhưng parttern bị hạn chế về mặt ip như ví dụ sau: **spring.cloud.inetutils.ignoreInterfaces** = **eth1*** hoặc chúng ta cũng có thể dụng white list network như sau **spring.cloud.inetutisl.preferNetworks** = **192.168.**

## Response Cache

 Eureka Server cache response là default. việc cache này sẽ là không hợp lệ trong vọng 30s. Việc thực hiện kiểm tra bằng cách gọi API của server là **/eureka/apps** , nếu bạn gọi sau khi vừa đăng ký , bạn sẽ thấy rằng nó sẽ không trả về response. nếu thử lại sau 30s bạn sẽ thấy một instaces mới xuất hiện, cache time out này có thể đưọc setting bằng thuộc tính **responseCacheUpdateIntervalMs**. nhưng có một điều khác là thú vị là sẽ không có cache trong khi hiển thị danh sách instaces trên dashboard của Eureka server.
 **eureka.server.responseCacheUpdateIntervalMs=30000** .
 Chúng ta nên biết rằng eureka registry cũng được cached ở client. Do vậy, nếu bạn thay đổi cache time out ở server. nó phải mất một thời gian ngắn cho đến khi nó được refresh bởi client. Registry sẽ tự động refresh theo chu kỳ bất đồng bộ, chạy background khoảng 30s một lần, thông số này cũng có thể setting bằng thuộc tính **registryFetchIntervalSeconds**

## Enabling secure communication between client and server

 - Using spring sercurity và setting tham số user name và password trong config nhưng:
  
```yaml
sercurirt:
    basic:
        enable: true
    user:
        name: admin
        password: admin123

```

và chúng ta sẽ định nghĩa ở phía client để kết nối tới eureka server.

```yaml
eureka:
    client:
        serviceUrl:
            defaultZone: http://admin:admin123@localhost:8761/eureka/
```

Nếu không dùng user name vs password chúng ta có thể dùng **SSL** để authentication giữa client và server. Bằng cách implementation **DiscoveryClientOptionalArgs**.

## Registering a secure service

Bảo mật ở phía server là một điều, còn việc đăng ký một ứng dụng bảo mật là một chuyện khác. Chúng ta có thể làm với nó như sau :

1. Đầu tiên là chúng ta enable **SSL** dành cho Springboot, chúng ta sẽ tạo ra một certificate cho chính bản thân nó bằng cách sử dụng  **keytool** như sau :
   **keytool -genkey -alias client -storetype PKCS12 -keyalg RSA -keysize 2048 -keyst**
2. Bưóc tiếp theo chúng ta copy file đã được tạo ra **keystore.p12** và trong đưòng dẫn resource của spring application **src/main/resource/** sau đó thực hiện cấu hình như sau :

```yaml
server:
    port: ${PORT:8081}
    ssl:
        key-store: classpath:keystore.p12
        key-store-password: 1233456
        keyStoreType: PKCS12
        keyAlias: client
```

3. Bước tiếp theo sau khi running ứng dụng , gọi enpoint **/http://localhost:8761/info/**. Chúng ta cũng cần thực hiện thay đổi cấu hình của eureka client instance như config dưới đây:

```yaml
eureka:
    instace:
        securePortEnable: true
        nonSecurePortEnabled: false
        statusPageUrl: https://${eureka.hostname}:${server.port}/info
        healthCheckUrl: https://${eureka.hostname}:${server.port}/health
        homePageUrl: https://${eureka.hostname}:${server.port}/
```

## Các Eureka API

THam khao Eureka HTTP API. Để sự dụng trong trường hợp các framework khác ngoài spring.

## Replication and High availability

Hệ thống cần tối thiểu 2 eureka server, trong trường hợp 1 server bị fail ( có thể do một vài nguyên nhân liên quan tới mạng).  
Eureka được xây dựng cho độ sẵn sàng và luôn tin cậy với nguyên lý có 2 cây cột (two primary pillars) đối với việc phát triển ở Netflix. 
Nhưng nó không cung cấp kỹ thuật clustering tiêu chuẩn nhưng là có một node là master 1 là slave hay sẽ tự động tham gia cluster đó.
Nó dựa trên mô hình peer-to-peer (ngang hàng) nghĩa là tất cả các dữ liệu của các và heartbead của các server replicate được gửi tới tất cả các node 
và chúng được thiết lập cấu hình giống nhau ở các replication. Như một thuật toán đơn giản và hiệu quả đối với việc chứa data.  
hưng nó cũng có những nhược điểm, như là giới hạn về khả năng scale, bởi vì mọi node phải chịu toàn bộ tải của server.

## Architecture of the sample solution

Kỹ thuật replication là một trong nhưng động lực chính để bắt đầu phiên bản mới của Eureka Server.  
Phiên bản 2.0 vẫn đang được phát triển. Bên cạnh việc tối tưu replication. 
Nó cũng cung cấp những tính năng rất thú vị như là một push model tới server tới clients  khi mà có bất kỳ sự thay đổi nào trong danh sách đăng ký, 
auto scale server, và 1 dashboard hoàn thiện hơn. Nhưng tính năng này sẽ rất là hứa hẹn, 
nhưng Spring cloud Netflix vẫn sử dụng version 1 và thực sự là vẫn chưa có kế hoạch chuyển đổi lên version 2. 
Phiên bản hiện tại của Eureka là **Dalston.SR4** trong release train là 1.6.2. 
Việc cấu hình dành cho kỹ thuật clustering ở phía server hướng tới một điều là thiết lập URL của các discovery server khác sử dụng các thuộc tính **eureka.client.**. 
Các server được lưạ chọn sẽ đăng ký bản thân nó với các server khác và việc lựa chọn này được coi là việc tạo ra cluster, như ví dụ trình bày ở dưới đây:
![sample](./image/example_eureka.png)

## Building the example application

Thêm lần lượt 3 profile cho eureka server nhưng lần lượt là peer1, peer2, peer3. rồi thực hiện khai báo như sau :
Đối với peer1:

```yaml
spring:
  profiles: peer1
eureka:
  instance:
    hostname: peer1
    metadataMap:
      zone: zone1
  client:
    serviceUrl:
      defaultZone: http://localhost:8762/eureka/,http://localhost:8763/eureka/
    registerWithEureka: true
    fetchRegistry: true
server:
  port: ${PORT:8761}

```

Đối với peer2:

```yaml
spring:
  profiles: peer2
eureka:
  instance:
    hostname: peer2
    metadataMap:
      zone: zone2
  client:
    serviceUrl:
      defaultZone: http://localhost:8763/eureka/,http://localhost:8761/eureka/
    registerWithEureka: true
    fetchRegistry: true
server:
  port: ${PORT:8762}

```

Đối với peer3:

```yaml
spring:
  profiles: peer3
eureka:
  instance:
    hostname: peer3
    metadataMap:
      zone: zone3
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/,http://localhost:8762/eureka/
    registerWithEureka: true
    fetchRegistry: true
server:
  port: ${PORT:8763}
```

Thứ tự của thuộc tính **defaultZone** sẽ quyết định connection đến server nào là mặc định, nếu không thể thiết lập kết nối tới server thứ 1 thì sẽ cố gắng kết nối tới server thứ 2. Và lời khuyên là sử dụng tham số **-Xmx192** khi chúng ta test ở locall. Nếu bạn không cung cấp giới hạn cho bộ nhớ spring application nó sẽ mất khoảng 350MB cho heap sau khi start xong và khoảng 600MB cho toàn bộ. Nếu máy bạn khoẻ thì đó không phải là vấn đề khi chạy nhiều instances trên local.