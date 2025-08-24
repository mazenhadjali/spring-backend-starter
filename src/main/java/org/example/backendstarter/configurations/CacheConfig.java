package org.example.backendstarter.configurations;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.backendstarter.ums.dto.AUserDto;
import org.example.backendstarter.ums.dto.AUserDtoWithPass;
import org.example.backendstarter.ums.dto.RoleDto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.List;
import java.util.Map;

@Configuration
public class CacheConfig {

    /**
     * Mapper used ONLY for Redis. No default typing => no "@class" pollution.
     * Your MVC ObjectMapper can be separate (or @Primary) if you define one.
     */
    @Bean
    public ObjectMapper redisObjectMapper() {
        return new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    /** Base config builder with a given value serializer and TTL. */
    private RedisCacheConfiguration baseConfig(Jackson2JsonRedisSerializer<?> valueSerializer, Duration ttl) {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                .disableCachingNullValues()
                .entryTtl(ttl);
    }

    /** Typed config for single objects, e.g. AUserDto.class */
    private RedisCacheConfiguration typedConfig(ObjectMapper om, Class<?> valueType, Duration ttl) {
        Jackson2JsonRedisSerializer<?> ser = new Jackson2JsonRedisSerializer<>(om, valueType);
        return baseConfig(ser, ttl);
    }

    /** Typed config for lists, e.g. List<AUserDto> */
    private RedisCacheConfiguration typedListConfig(ObjectMapper om, Class<?> elementType, Duration ttl) {
        JavaType listType = om.getTypeFactory().constructCollectionType(List.class, elementType);
        Jackson2JsonRedisSerializer<?> ser = new Jackson2JsonRedisSerializer<>(om, listType);
        return baseConfig(ser, ttl);
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory, ObjectMapper redisObjectMapper) {

        // Default fallback for caches not explicitly listed
        RedisCacheConfiguration defaultConfig =
                typedConfig(redisObjectMapper, Object.class, Duration.ofMinutes(10));

        // Per-cache strong typing
        Map<String, RedisCacheConfiguration> configs = Map.of(
                "findAUserByUsernameforAuth", typedConfig(redisObjectMapper, AUserDtoWithPass.class, Duration.ofMinutes(20)),
                "AUserById",             typedConfig(redisObjectMapper, AUserDto.class, Duration.ofMinutes(20)),
                "aUserByUsername",       typedConfig(redisObjectMapper, AUserDto.class, Duration.ofMinutes(10)),
                "allaUsers",              typedListConfig(redisObjectMapper, AUserDto.class, Duration.ofMinutes(3)),

                "aUserExistsById",            typedConfig(redisObjectMapper, Boolean.class, Duration.ofMinutes(2)),
                "aUserxistsByUsername",      typedConfig(redisObjectMapper, Boolean.class, Duration.ofMinutes(2)),

                "allRoles",              typedListConfig(redisObjectMapper, RoleDto.class, Duration.ofMinutes(20)),
                "roleById",              typedConfig(redisObjectMapper, RoleDto.class, Duration.ofMinutes(20))
        );

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(defaultConfig)
                .withInitialCacheConfigurations(configs)
                .transactionAware()
                .build();
    }
}
