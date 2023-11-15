package com.scsb.t.controller;

import com.scsb.t.entity.TemplatesRegistrationEntity;
import com.scsb.t.dao.TemplatesRegistrationRepository;
import com.scsb.t.service.S3Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;


import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/api/template")
@CrossOrigin(origins = "*")
public class TemplateController {


    private TemplatesRegistrationRepository templatesRegistrationRepository;

    private  S3Loader s3Loader;

    @Autowired
    public TemplateController(S3Loader s3Loader,TemplatesRegistrationRepository templatesRegistrationRepository) {
        this.s3Loader = s3Loader;
        this.templatesRegistrationRepository=templatesRegistrationRepository;
        System.out.println("創立建構式 TemplateController");
    }


    // /api/template/getList
    // 回傳 templates_registration 的表單模板名稱與 id
    @GetMapping("/getList")
    public ResponseEntity<List<Map<String, String>>> getFormData() {
        List<Map<String, String>> dataList = new ArrayList<>();

        List<TemplatesRegistrationEntity> templatesList = templatesRegistrationRepository.findAll();

        for (TemplatesRegistrationEntity template : templatesList) {
            Map<String, String> data = new HashMap<>();
            data.put("label", template.getTemplateName());
            data.put("value",  Long.toString(template.getId()));
            dataList.add(data);
        }

        return ResponseEntity.ok(dataList);
    }


    // 輸入 json 檔案的名稱，回傳其中的內容
//    private String directoryPath = "src/main/resources/templates/forms/formTemplate/";
//    private String loadFormData(String fileName) throws IOException {
//        Path filePath = Paths.get(directoryPath + fileName);
//        InputStream inputStream = Files.newInputStream(filePath);
//
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
//            StringBuilder data = new StringBuilder();
//            String line;
//            while ((line = reader.readLine()) != null) {
//                data.append(line);
//            }
//            System.out.println(data.toString());
//            return data.toString();
//        }
//    }


    @GetMapping(value = "/loadFormData", produces = MediaType.APPLICATION_JSON_VALUE)
    public String loadFormData(@RequestParam String fileName) {
        return s3Loader.loadFormData(fileName);
    }

//    static class S3Loader {
//
//        private static final String BUCKET_NAME = "scsb";
//        private static final String BASE_KEY_PATH = "formTemplate/";
//
//        private static final String ENDPOINT = "https://o0m5.sg.idrivee2-44.com";
//        private static final String ACCESS_KEY = "5sXb1p7xDgohvewN49Gp";
//        private static final String SECRET_KEY = "k3E88YE9msfmKUpLjY2uzFJ1okfzKZbDDScgWvNO";
//        private final S3Client s3;

//        public S3Loader() {
//            s3 = S3Client.builder()
//                    .region(Region.AP_SOUTHEAST_1)
//                    .endpointOverride(URI.create(ENDPOINT))
//                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
//                    .build();
//        }
//
//        public String loadFormData(String fileName) {
//            String fullKey = BASE_KEY_PATH + fileName;
//
//            try (InputStream inputStream = s3.getObject(GetObjectRequest.builder()
//                    .bucket(BUCKET_NAME)
//                    .key(fullKey)
//                    .build())) {
//
//                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
//                    StringBuilder data = new StringBuilder();
//                    String line;
//                    while ((line = reader.readLine()) != null) {
//                        data.append(line);
//                    }
//                    return data.toString();
//                }
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to load data from S3", e);
//            }
//        }
//
//        public void uploadToS3(String fileContent, String s3Key) {
//            s3.putObject(PutObjectRequest.builder()
//                            .bucket(BUCKET_NAME)
//                            .key(s3Key)
//                            .build(),
//                    RequestBody.fromString(fileContent));
//        }
//
//    }

    // /api/template?id=
    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getFormData(@RequestParam String id) {
        try {
            if (!id.endsWith(".json")) {
                id += ".json";
            }
            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(s3Loader.loadFormData(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please Enter Correct Value");
        }
    }



//    // /api/template?id=
//    // 輸入表單 id ，回傳對應的表單 json 內容
//    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<String> getFormData(@RequestParam String id) {
//        try {
//            if (!id.endsWith(".json")) {
//                id += ".json";
//            }
//            return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON).body(loadFormData(id));
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Please Enter Correct Value");
//        }
//    }

    // 文件監控（專案執行時自動啟用）
//    @PostConstruct
//    public void setupFileWatcher() {
//        // 監視的目錄的路徑
//        Path directory = Paths.get(directoryPath);
//
//        try {
//            // 建立WatchService Entity
//            WatchService watchService = FileSystems.getDefault().newWatchService();
//            // 向監視服務註冊路徑，所有文件編輯都會觸發此事件
//            directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
//
//            //
//            Executors.newSingleThreadExecutor().submit(() -> {
//                while (true) {
//                    WatchKey key;
//                    try {
//                        key = watchService.take();
//                    } catch (InterruptedException e) {
//                        return;
//                    }
//
//                    for (WatchEvent<?> event : key.pollEvents()) {
//                        if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
//                            String fileName = event.context().toString();
//                            System.out.println("File modified: " + fileName);
//                            // 在这里重新加载文件
//                        }
//                    }
//                    key.reset();
//                }
//            });
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestHeader("X-Form-Name") String formName,
                                                   @RequestHeader("X-Sign-Type") String signType,
                                                   @org.springframework.web.bind.annotation.RequestBody String fileContent) {
        System.out.println("進入api");
        try {
            String decodedFormName = URLDecoder.decode(formName, "UTF-8");

            TemplatesRegistrationEntity registration = new TemplatesRegistrationEntity();
            registration.setTemplateName(decodedFormName);
            registration.setCreatedBy("E00001");
            registration.setStatus("Enabled");
            registration.setSignType(signType);

            templatesRegistrationRepository.save(registration);

            String s3Key = s3Loader.BASE_KEY_PATH + Long.toString(registration.getId()) + ".json";
            s3Loader.uploadToS3(fileContent, s3Key);

            return ResponseEntity.ok("{\"message\": \"File uploaded successfully 模板已成功上傳！～\"}");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
        }
    }




//    @PostMapping("/upload")
//    public ResponseEntity<String> handleFileUpload(@RequestHeader("X-Form-Name") String formName,
//                                                   @RequestHeader("X-Sign-Type") String signType,
//                                                   @RequestBody String fileContent) {
//        System.out.println("進入api");
//        try {
//            // 将经过 encodeURIComponent 处理的值还原成中文
//            String decodedFormName = URLDecoder.decode(formName, "UTF-8");
//            // 创建 TemplatesRegistrationEntity 对象
//            TemplatesRegistrationEntity registration = new TemplatesRegistrationEntity();
//            registration.setTemplateName(decodedFormName);
//            registration.setCreatedBy("E00001");
//            registration.setStatus("Enabled");
//            registration.setSignType(signType);
//
//            templatesRegistrationRepository.save(registration);
//
//            File formsDir = new File("src/main/resources/templates/forms/formTemplate");
//            if (!formsDir.exists()) {
//                formsDir.mkdirs();
//            }
//
//            File jsonFile = new File(formsDir, Long.toString(registration.getId())+ ".json");
//            System.out.println(jsonFile);
//
//            // Fill in json
//            FileWriter writer = new FileWriter(jsonFile);
//            writer.write(fileContent);
//            writer.close();
//
//            return ResponseEntity.ok("{\"message\": \"File uploaded successfully 模板已成功上傳！～\"}");
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading file");
//        }
//    }


}
