package org.wjanaszek.checkstory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wjanaszek.checkstory.exception.NoResourceFoundException;
import org.wjanaszek.checkstory.service.PhotoCompareService;

@RestController
@CrossOrigin
@RequestMapping(value = "api/photos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhotoController {

    @Autowired
    private PhotoCompareService photoCompareService;

    @GetMapping(value = "/compare/{firstId}/with/{secondId}")
    public ResponseEntity<?> comparePhotos(
            @PathVariable Long firstId,
            @PathVariable Long secondId,
            @RequestParam(required = false) Integer sensitivity,
            @RequestParam(required = false) Integer size
    ) throws NoResourceFoundException {
        if (sensitivity == null) {
            sensitivity = 250;
        }
        if (size == null) {
            size = 720;
        }
        return ResponseEntity.ok(photoCompareService.compare(firstId, secondId, sensitivity, size));
    }

}
