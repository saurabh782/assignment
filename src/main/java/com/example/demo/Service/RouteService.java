package com.example.demo.Service;

import com.example.demo.Entry.DeliveryExecutiveEntry;
import com.example.demo.Entry.LocationEntry;
import com.example.demo.Entry.OrderEntry;
import com.example.demo.Entry.RestaurantEntry;
import com.example.demo.Entry.RouteEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliveryExecutiveService deliveryExecutiveService;

    public List<LocationEntry> findBestRoute(Long deliveryExecutiveId) {
        List<OrderEntry> orders = orderService.findOrdersForExecutive(deliveryExecutiveId);

        DeliveryExecutiveEntry deliveryExecutive = deliveryExecutiveService.findExecutive(deliveryExecutiveId);

        return findBestRouteFromAllPossibleRoutes(orders, deliveryExecutive);
    }

    private List<LocationEntry> findBestRouteFromAllPossibleRoutes(List<OrderEntry> orders, DeliveryExecutiveEntry deliveryExecutive) {
        LocationEntry executiveInitialLocation = deliveryExecutive.getLocationEntry();

        OrderEntry firstOrder = orders.get(0);
        OrderEntry secondOrder = orders.get(0);

        RestaurantEntry firstRestaurant = firstOrder.getRestaurantEntry();
        RestaurantEntry secondRestaurant = secondOrder.getRestaurantEntry();

        LocationEntry firstRestaurantLocation = firstOrder.getRestaurantEntry().getLocationEntry();
        LocationEntry secondRestaurantLocation = secondOrder.getRestaurantEntry().getLocationEntry();

        LocationEntry firstCustomerLocation = firstOrder.getConsumerEntry().getLocationEntry();
        LocationEntry secondCustomerLocation = secondOrder.getConsumerEntry().getLocationEntry();

        //Possible routes
        List<LocationEntry> executive_To_R1_To_C1_To_R2_To_C2 = Arrays.asList(executiveInitialLocation, firstRestaurantLocation, firstCustomerLocation, secondRestaurantLocation, secondCustomerLocation);
        List<LocationEntry> executive_To_R2_To_C2_To_R1_To_C1 = Arrays.asList(executiveInitialLocation, secondRestaurantLocation, secondCustomerLocation, firstRestaurantLocation, firstCustomerLocation);
        List<LocationEntry> executive_To_R1_To_R2_To_C1_To_C2 = Arrays.asList(executiveInitialLocation, firstRestaurantLocation, secondRestaurantLocation, firstCustomerLocation, secondCustomerLocation);
        List<LocationEntry> executive_To_R1_To_R2_To_C2_To_C1 = Arrays.asList(executiveInitialLocation, firstRestaurantLocation, secondRestaurantLocation, secondCustomerLocation, firstCustomerLocation);
        List<LocationEntry> executive_To_R2_To_R1_To_C1_To_C2 = Arrays.asList(executiveInitialLocation, secondRestaurantLocation, firstRestaurantLocation, firstCustomerLocation, secondCustomerLocation);
        List<LocationEntry> executive_To_R2_To_R1_To_C2_To_C1 = Arrays.asList(executiveInitialLocation, secondRestaurantLocation, firstRestaurantLocation, firstCustomerLocation, secondCustomerLocation);


        Double timeForExecutive_To_R1_To_C1_To_R2_To_C2 = calculateTimeForExecutive_To_R1_To_C1_To_R2_To_C2(executive_To_R1_To_C1_To_R2_To_C2,
                firstRestaurant.getTimeToPrepareInMinutes(), secondRestaurant.getTimeToPrepareInMinutes());

        Double timeForExecutive_To_R2_To_C2_To_R1_To_C1 = calculateTimeForExecutive_To_R2_To_C2_To_R1_To_C1(executive_To_R2_To_C2_To_R1_To_C1,
                firstRestaurant.getTimeToPrepareInMinutes(), secondRestaurant.getTimeToPrepareInMinutes());

        Double timeForExecutive_To_R1_To_R2_To_C1_To_C2 = calculateTimeForExecutive_To_R1_To_R2_To_C1_To_C2(executive_To_R1_To_R2_To_C1_To_C2,
                firstRestaurant.getTimeToPrepareInMinutes(), secondRestaurant.getTimeToPrepareInMinutes());

        Double timeForExecutive_To_R1_To_R2_To_C2_To_C1 = calculateTimeForExecutive_To_R1_To_R2_To_C2_To_C1(executive_To_R1_To_R2_To_C2_To_C1,
                firstRestaurant.getTimeToPrepareInMinutes(), secondRestaurant.getTimeToPrepareInMinutes());

        Double timeForExecutive_To_R2_To_R1_To_C1_To_C2 = calculateTimeForExecutive_To_R2_To_R1_To_C1_To_C2(executive_To_R2_To_R1_To_C1_To_C2,
                firstRestaurant.getTimeToPrepareInMinutes(), secondRestaurant.getTimeToPrepareInMinutes());

        Double timeForExecutive_To_R2_To_R1_To_C2_To_C1 = calculateTimeForExecutive_To_R2_To_R1_To_C2_To_C1(executive_To_R2_To_R1_To_C2_To_C1,
                firstRestaurant.getTimeToPrepareInMinutes(), secondRestaurant.getTimeToPrepareInMinutes());

        List< RouteEntry> possibleRoutesWithTime = new ArrayList<>(Arrays.asList(
                new RouteEntry(timeForExecutive_To_R1_To_C1_To_R2_To_C2, executive_To_R1_To_C1_To_R2_To_C2),
                new RouteEntry(timeForExecutive_To_R2_To_C2_To_R1_To_C1, executive_To_R2_To_C2_To_R1_To_C1),
                new RouteEntry(timeForExecutive_To_R1_To_R2_To_C1_To_C2, executive_To_R1_To_R2_To_C1_To_C2),
                new RouteEntry(timeForExecutive_To_R1_To_R2_To_C2_To_C1, executive_To_R1_To_R2_To_C2_To_C1),
                new RouteEntry(timeForExecutive_To_R2_To_R1_To_C1_To_C2, executive_To_R2_To_R1_To_C1_To_C2),
                new RouteEntry(timeForExecutive_To_R2_To_R1_To_C2_To_C1, executive_To_R2_To_R1_To_C2_To_C1)));

        // Find the optimal route
        Optional<RouteEntry> minRouteEntry = possibleRoutesWithTime.stream().min(Comparator.comparingDouble(RouteEntry::getTotalTime));
        return minRouteEntry.get().getRoute();
    }

    private double calculateTimeForExecutive_To_R1_To_C1_To_R2_To_C2(List<LocationEntry> route, Double firstRestaurantPrepationTime, Double secondRestaurantPreparationTime) {
        double amanToR1 = haversine(route.get(0).getLatitude(), route.get(0).getLongitude(), route.get(1).getLatitude(), route.get(1).getLongitude());
        double r1ToC1 = haversine(route.get(1).getLatitude(), route.get(1).getLongitude(), route.get(2).getLatitude(), route.get(2).getLongitude());
        double c1ToR2 = haversine(route.get(2).getLatitude(), route.get(2).getLongitude(), route.get(3).getLatitude(), route.get(3).getLongitude());
        double r2ToC2 = haversine(route.get(3).getLatitude(), route.get(3).getLongitude(), route.get(4).getLatitude(), route.get(4).getLongitude());

        return Math.max(Math.max(amanToR1, firstRestaurantPrepationTime) + r1ToC1 + c1ToR2, secondRestaurantPreparationTime) + r2ToC2;
    }

    private double calculateTimeForExecutive_To_R2_To_C2_To_R1_To_C1(List<LocationEntry> route, Double firstRestaurantPrepationTime, Double secondRestaurantPreparationTime) {
        double amanToR2 = haversine(route.get(0).getLatitude(), route.get(0).getLongitude(), route.get(1).getLatitude(), route.get(1).getLongitude());
        double r2ToC2 = haversine(route.get(1).getLatitude(), route.get(1).getLongitude(), route.get(2).getLatitude(), route.get(2).getLongitude());
        double c2ToR1 = haversine(route.get(2).getLatitude(), route.get(2).getLongitude(), route.get(3).getLatitude(), route.get(3).getLongitude());
        double r1ToC1 = haversine(route.get(3).getLatitude(), route.get(3).getLongitude(), route.get(4).getLatitude(), route.get(4).getLongitude());

        return Math.max(Math.max(amanToR2, secondRestaurantPreparationTime) + r2ToC2 + c2ToR1, firstRestaurantPrepationTime) + r1ToC1;
    }

    private double calculateTimeForExecutive_To_R1_To_R2_To_C1_To_C2(List<LocationEntry> route, Double firstRestaurantPrepationTime, Double secondRestaurantPreparationTime) {
        double amanToR1 = haversine(route.get(0).getLatitude(), route.get(0).getLongitude(), route.get(1).getLatitude(), route.get(1).getLongitude());
        double r1ToR2 = haversine(route.get(1).getLatitude(), route.get(1).getLongitude(), route.get(2).getLatitude(), route.get(2).getLongitude());
        double r2ToC1 = haversine(route.get(2).getLatitude(), route.get(2).getLongitude(), route.get(3).getLatitude(), route.get(3).getLongitude());
        double c1ToC2 = haversine(route.get(3).getLatitude(), route.get(3).getLongitude(), route.get(4).getLatitude(), route.get(4).getLongitude());

        return Math.max(Math.max(amanToR1, firstRestaurantPrepationTime) + r1ToR2, secondRestaurantPreparationTime) + r2ToC1 + c1ToC2;
    }


    private Double calculateTimeForExecutive_To_R1_To_R2_To_C2_To_C1(List<LocationEntry> route, Double firstRestaurantPrepationTime, Double secondRestaurantPreparationTime) {
        double amanToR1 = haversine(route.get(0).getLatitude(), route.get(0).getLongitude(), route.get(1).getLatitude(), route.get(1).getLongitude());
        double r1ToR2 = haversine(route.get(1).getLatitude(), route.get(1).getLongitude(), route.get(2).getLatitude(), route.get(2).getLongitude());
        double r2ToC2 = haversine(route.get(2).getLatitude(), route.get(2).getLongitude(), route.get(3).getLatitude(), route.get(3).getLongitude());
        double c2ToC1 = haversine(route.get(3).getLatitude(), route.get(3).getLongitude(), route.get(4).getLatitude(), route.get(4).getLongitude());

        return Math.max(Math.max(amanToR1, firstRestaurantPrepationTime) + r1ToR2, secondRestaurantPreparationTime) + r2ToC2 + c2ToC1;
    }

    private Double calculateTimeForExecutive_To_R2_To_R1_To_C1_To_C2(List<LocationEntry> route, Double firstRestaurantPrepationTime, Double secondRestaurantPreparationTime) {
        double amanToR2 = haversine(route.get(0).getLatitude(), route.get(0).getLongitude(), route.get(1).getLatitude(), route.get(1).getLongitude());
        double r2ToR1 = haversine(route.get(1).getLatitude(), route.get(1).getLongitude(), route.get(2).getLatitude(), route.get(2).getLongitude());
        double r1ToC1 = haversine(route.get(2).getLatitude(), route.get(2).getLongitude(), route.get(3).getLatitude(), route.get(3).getLongitude());
        double c1ToC2 = haversine(route.get(3).getLatitude(), route.get(3).getLongitude(), route.get(4).getLatitude(), route.get(4).getLongitude());

        return Math.max(Math.max(amanToR2, secondRestaurantPreparationTime) + r2ToR1, firstRestaurantPrepationTime) + r1ToC1 + c1ToC2;
    }

    private Double calculateTimeForExecutive_To_R2_To_R1_To_C2_To_C1(List<LocationEntry> route, Double firstRestaurantPrepationTime, Double secondRestaurantPreparationTime) {
        double amanToR2 = haversine(route.get(0).getLatitude(), route.get(0).getLongitude(), route.get(1).getLatitude(), route.get(1).getLongitude());
        double r2ToR1 = haversine(route.get(1).getLatitude(), route.get(1).getLongitude(), route.get(2).getLatitude(), route.get(2).getLongitude());
        double r1ToC2 = haversine(route.get(2).getLatitude(), route.get(2).getLongitude(), route.get(3).getLatitude(), route.get(3).getLongitude());
        double c2ToC1 = haversine(route.get(3).getLatitude(), route.get(3).getLongitude(), route.get(4).getLatitude(), route.get(4).getLongitude());

        return Math.max(Math.max(amanToR2, secondRestaurantPreparationTime) + r2ToR1, firstRestaurantPrepationTime) + r1ToC2 + c2ToC1;
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radius of earth
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}