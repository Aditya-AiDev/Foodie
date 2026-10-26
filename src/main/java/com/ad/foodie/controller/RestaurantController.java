package com.ad.foodie.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ad.foodie.dto.RestaurantDto;

import com.ad.foodie.service.RestaurantService;

@RestController
@RequestMapping("/api/v1/restaurants")
public class RestaurantController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Value ("${restaurant.file.path}") 
    private String bannerFolderPath;

    private final RestaurantService restaurantService;

    

    @Autowired
    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    
    

    // add///
    @PostMapping
    public ResponseEntity<RestaurantDto> add(@RequestBody RestaurantDto restaurantDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(restaurantService.add(restaurantDto));
    }

    // update//
    @PutMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDto> update(@RequestBody RestaurantDto restaurantDto,
            @PathVariable String restaurantId) {
        return ResponseEntity.status(HttpStatus.OK).body(restaurantService.update(restaurantDto, restaurantId));
    }

    // findAll///
    @GetMapping
    public ResponseEntity<Page<RestaurantDto>> finAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size,
            @RequestParam(value = "sortBy", required = false, defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir", required = false, defaultValue = "desc") String sortDir

    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        return ResponseEntity.ok(restaurantService.getAll(pageable));

    }

    /// find name///
    ///

    /// delete////
    ///

    ////
    /// Api to handle restaurant banner: uploadFile///
    /// client to server folder to DB ///
    @PostMapping("/upload-banner/{restaurantId}")
    public ResponseEntity<?> uploadFile(@RequestParam("banner") MultipartFile banner,
            @PathVariable String restaurantId) {

        logger.info("Upload banner file:");
        logger.info(banner.getOriginalFilename());
        logger.info(banner.getContentType());
        // call restaurant service
        RestaurantDto restaurantDto=restaurantService.uploadBanner(banner, restaurantId);

        return ResponseEntity.ok(restaurantDto);

    }

    // Api for serve file ///
    //DB to server Folder to client view ///
    @GetMapping ("/{restaurantId}/banner")
    public ResponseEntity<Resource> serveFile(@PathVariable String restaurantId) throws IOException{

        RestaurantDto restaurantDto=restaurantService.get(restaurantId);
        String fullPath= bannerFolderPath + restaurantDto.getBanner();

        //string path to a URI using Paths ////
        Path filePath = Paths.get(fullPath);
        Resource resource = new UrlResource(filePath.toUri());

        if(resource.exists()){
            return ResponseEntity
            .ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(resource);
        }
        else{
            throw new FileNotFoundException("File not found " + fullPath);
        }

        

    }

}
