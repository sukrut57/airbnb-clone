//package com.airbnb.clone.userEntity.adapter.out.event;
//
//import com.airbnb.clone.backend.user.adapter.in.event.RedisUserEventSubscriber;
//import com.airbnb.clone.backend.user.domain.event.UserEventType;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.listener.PatternTopic;
//import org.springframework.data.redis.listener.RedisMessageListenerContainer;
//import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//
//@Configuration
//public class RedisEventConfig {
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory("redis", 6379); // use 'redis' host in Docker
//    }
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(factory);
//        template.setKeySerializer(new StringRedisSerializer());
//        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
//        return template;
//    }
//
//    @Bean
//    public RedisMessageListenerContainer redisContainer(
//            RedisConnectionFactory factory,
//            MessageListenerAdapter listenerAdapter) {
//        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
//        container.setConnectionFactory(factory);
//        container.addMessageListener(listenerAdapter, new PatternTopic(UserEventType.USER_CREATED.topic()));
//        return container;
//    }
//
//    @Bean
//    public MessageListenerAdapter listenerAdapter(RedisUserEventSubscriber subscriber) {
//        return new MessageListenerAdapter(subscriber, "onMessage");
//    }
//}
