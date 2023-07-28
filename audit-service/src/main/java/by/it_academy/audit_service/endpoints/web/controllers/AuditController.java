package by.it_academy.audit_service.endpoints.web.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/audit")
@Validated
public class AuditController {

    @GetMapping
    public ResponseEntity<?> get(@RequestParam(defaultValue = "0", required = false) Integer page, @RequestParam(defaultValue = "20", required = false) Integer size) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<?> getByUuid(@PathVariable UUID uuid) {
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
