package hu.elte.inf.notes.export;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class ImportExportController {

    @Autowired
    private ImportExportService importExportService;

    @GetMapping(value = "/export", produces = "application/zip")
    public void zipFiles(HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Disposition", "attachment; filename=\"export.zip\"");
        response.addHeader("Content-Type", "application/zip");
        try {
            importExportService.export(response.getOutputStream());
        } catch (IOException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/import")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            importExportService.importZip(file.getInputStream());
            return ResponseEntity.ok("File imported successfully.");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Failed to import file.");
        }
    }
}
