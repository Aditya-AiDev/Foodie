package com.ad.foodie.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Getter 
// @Setter 
// @AllArgsConstructor 
// @NoArgsConstructor 
// @Builder 
// public class FileData {

//     private String fileName;
//     private String filePath;


// }

//// or //////
/// 
public record FileData(String fileName, String filePath) {
}
