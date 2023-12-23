package org.opennuri.study.architecture.common.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;




/**
 * ReTemplate를 사용할때, 요청산 서버에서 응답을 늦게 주거나 할 경우 서버의 Thred가 늘어나는 문제가 발생할 수 있다.
 * 이를 방지하기 위해 RestTemplate를 사용할때는 Connection Pool을 사용하도록 설정해야 한다.
 * RestTemplate는 기본적으로 SimpleClientHttpRequestFactory를 사용하고 있으며, 이는 Connection Pool을 사용하지 않는다
 * 따라서 RestTemplate를 사용할때는 HttpComponentsClientHttpRequestFactory를 사용하도록 설정해야 한다.
 * HttpComponentsClientHttpRequestFactory는 Connection Pool을 사용하도록 설정되어 있다.
 * apache httpclient5를 사용하도록 설정한다.
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate restTemplate() {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();

        // httpclient5를 사용하여 Connection Pool을 사용하도록 설정한다.
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setMaxConnPerRoute(20) // 기본값 2
                        .setMaxConnTotal(200) // 기본값 20
                        .build());

        HttpClient httpClient = httpClientBuilder.build();


        factory.setHttpClient(httpClient);
        // connectTimeout – the timeout value in milliseconds
        // 클라이언트가 서버에 접속하기 위해 기다리는 최대 시간이다. 이는 네트워크 자체가 느릴 경우 제한할 수 있는 설정이다
        factory.setConnectTimeout(300);
        // connectionRequestTimeout – the timeout value to request a connection in milliseconds
        // 만약 지정한 커넥션 풀이 모두 사용중일 경우에, 다른 가용할 수 있는 쓰레드를 대기 하기 위한 최대 시간이다
        factory.setConnectionRequestTimeout(3000);
        return new RestTemplate(factory);
    }
}
