package seu.edu.bd.healthtracker.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class FoodScannerService {

    public Map<String, String> processFoodImage(MultipartFile file) throws IOException {
        Map<String, String> result = new HashMap<>();

        if (file.isEmpty()) {
            result.put("errorMessage", "Please select an image to upload.");
            return result;
        }

        // Save the uploaded image temporarily
        String uploadDir = System.getProperty("user.dir") + "/uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileExtension = "";
        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex > 0) {
            fileExtension = originalFilename.substring(dotIndex);
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
        Path filePath = uploadPath.resolve(uniqueFileName);

        Files.copy(file.getInputStream(), filePath);

        // Simulate analysis: provide a dummy message
        result.put("analysisResult", "Food analysis functionality is currently not available for image: " + originalFilename + ". Please check back later for updates!");
        result.put("fileName", originalFilename);

        // Clean up the uploaded file after processing (optional, but good practice for temporary files)
        Files.deleteIfExists(filePath);

        return result;
    }
}