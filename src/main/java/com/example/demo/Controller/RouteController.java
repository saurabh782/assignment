package com.example.demo.Controller;

import com.example.demo.Entry.LocationEntry;
import com.example.demo.Service.RouteService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Getter
@Setter
@Controller
public class RouteController {

    @Autowired
    private RouteService service;

    public List<LocationEntry> findBestRoute(Long deliveryExecutiveId) {
        return service.findBestRoute(deliveryExecutiveId);
    }
}
