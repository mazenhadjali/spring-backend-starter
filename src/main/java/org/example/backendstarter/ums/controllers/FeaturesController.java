package org.example.backendstarter.ums.controllers;

import lombok.RequiredArgsConstructor;
import org.example.backendstarter.common.Feature;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/features")
@RequiredArgsConstructor
public class FeaturesController {


    @GetMapping
    public ResponseEntity<List<Feature>> getAllUsers() {
        return ResponseEntity.ok().body(List.of(Feature.values()));
    }

}
