package com.alpha.Eatclub.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RadioService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // Store Delivery Partner Location
    public String updateDpLoc(Integer partnerId, double lat, double longitude) {

        redisTemplate.opsForGeo().add(
                "deliverypartner:location",
                new Point(longitude, lat),
                partnerId.toString()
        );

        return "LOCATION UPDATED";
    }

    // Find Nearby Delivery Partners
    public List<String> findNearbyPartners(double latitude, double longitude, double radiusKm) {

        Circle searchArea = new Circle(
                new Point(longitude, latitude),
                new Distance(radiusKm, Metrics.KILOMETERS)
        );

        GeoResults<RedisGeoCommands.GeoLocation<String>> results =
                redisTemplate.opsForGeo()
                        .radius("deliverypartner:location", searchArea);

        if (results == null) {
            return List.of();
        }

        return results.getContent()
                .stream()
                .map(result -> result.getContent().getName())
                .collect(Collectors.toList());
    }
}