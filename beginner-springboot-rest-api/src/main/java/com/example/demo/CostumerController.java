package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = CostumerController.PATH)
public class CostumerController {

    private static final List<Costumer> list = new ArrayList<>();
    public static final String PATH = "/costumer";

    static {
        list.add(new Costumer(1L, "Costumer One"));
        list.add(new Costumer(2L, "Costumer Two"));
    }

    @GetMapping
    public ResponseEntity<List<Costumer>> getAll() {
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<Costumer> create(@RequestBody Costumer costumer) throws URISyntaxException {
        list.add(costumer);
        return ResponseEntity
                .created(new URI(String.format("%s/%d", PATH, costumer.getId())))
                .body(costumer);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Costumer> getById(@PathVariable Long id) {
        if (!hasItem(id)) {
            return ResponseEntity.notFound().build();
        }
        Costumer result = getCostumer(id);
        return ResponseEntity.ok(result);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Costumer> update(@PathVariable Long id, @RequestBody Costumer costumer) {
        if (!hasItem(id)) {
            return ResponseEntity.notFound().build();
        }
        list.remove(getCostumer(id));
        list.add(costumer);
        return ResponseEntity.ok(costumer);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        if (!hasItem(id)) {
            return ResponseEntity.notFound().build();
        }
        list.remove(getCostumer(id));
        return ResponseEntity.noContent().build();
    }

    private Costumer getCostumer(Long id) {
        return list.stream().filter(costumer -> costumer.getId().equals(id)).findFirst().orElse(null);
    }

    private Boolean hasItem(Long id) {
        return list.stream().anyMatch(costumer -> costumer.getId().equals(id));
    }
}
