package com.example.Mini1st.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration // config file 인식
@EnableRedisRepositories
public class RedisConfig {

    // Redis 서버의 호스트 주소를 가져오는 변수
    @Value("${spring.redis.host}")
    private String redisHost;

    // Redis 서버의 포트 번호
    @Value("${spring.redis.port}")
    private int redisPort;

//    // Redis 비밀번호
//    @Value("${spring.redis.password}")
//    private String redisPassword;

    // RedisConnectionFactory Bean 생성
    // Redis 서버와의 연결을 설정하고 관리 하는데 사용.
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisHost);
        redisStandaloneConfiguration.setPort(redisPort);
        // redisStandaloneConfiguration.setPassword(redisPassword); // 비밀번호가 설정되지 않았다면 주석 처리
        //return new LettuceConnectionFactory(redisStandaloneConfiguration);
        return new LettuceConnectionFactory("localhost", 6379);
    }
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }

//    // ChannelTopic Bean 생성
//    // Redis의 pub/sub 메시징을 위한 채널 토픽을 설정.
//    @Bean
//    public ChannelTopic channelTopic() {
//        return new ChannelTopic("chatroom");
//    }
//
//    // RedisMessageListenerContainer Bean 생성하는 메서드
//    // Redis 메시지를 수신하고 리스너에 전달하는 컨테이너 설정.
//    @Bean
//    public RedisMessageListenerContainer redisMessageListenerContainer(
//            RedisConnectionFactory connectionFactory,
//            MessageListenerAdapter listenerAdapterChatMessage,
//            ChannelTopic channelTopic) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.addMessageListener(listenerAdapterChatMessage, channelTopic);
//        return container;
//    }
//
//    // 실제 메시지를 처리하는 subscriber 설정 추가
//    // RedisSubscriber 클래스를 사용하여 메시지를 처리하는 어댑터를 설정.
//    @Bean
//    public MessageListenerAdapter listenerAdapterChatMessage(RedisSubscriber subscriber) {
//        return new MessageListenerAdapter(subscriber, "onMessage");
//    }
//
//    // RedisTemplate Bean 생성하는 메서드
//    // Redis 데이터를 직렬화 하고 역 직렬화 하는데 사용한다.
//    @Bean
//    public RedisTemplate<String, Object> chatRoomRedisTemplate(RedisConnectionFactory connectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(connectionFactory);
//
//        // 키를 위한 직렬화 설정
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setHashKeySerializer(new StringRedisSerializer());
//
//        // 값을 위한 직렬화 설정
//        Jackson2JsonRedisSerializer<ChatRoom> serializer = new Jackson2JsonRedisSerializer<>(ChatRoom.class);
//        template.setValueSerializer(serializer);
//        template.setHashValueSerializer(serializer);
//
//        return template;
//    }

}// end class
