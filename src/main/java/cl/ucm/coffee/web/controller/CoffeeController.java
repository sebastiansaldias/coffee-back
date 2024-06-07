package cl.ucm.coffee.web.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/coffee")
public class CoffeeController {

    @GetMapping("")
    public ResponseEntity<Map<String, String>> coffes(){
        Map map = new HashMap();
        map.put("coffee", "Coffees :Get)");
        return ResponseEntity.ok(map);
    }
    @PostMapping("/save")
    public ResponseEntity<Map<String, String>> coffe(){
        Map map = new HashMap();
        map.put("coffee", "Coffees Post:)");
        return ResponseEntity.ok(map);
    }

}
