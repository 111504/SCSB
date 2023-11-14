package com.scsb.t.controller;

import com.scsb.t.entity.EmployeeEntity;
import com.scsb.t.entity.ProposalDataEntity;
import com.scsb.t.service.EmployeeServiceImpl;
import com.scsb.t.service.ProposalDataServiceImpl;
import com.scsb.t.service.S3Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.core.sync.RequestBody;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class SignFormController {
    private EmployeeServiceImpl employeeService;
    private ProposalDataServiceImpl proposalDataService;
    private  S3Loader s3Loader;
    @Autowired
    public SignFormController(EmployeeServiceImpl employeeService, ProposalDataServiceImpl proposalDataService, S3Loader s3Loader) {
        this.employeeService = employeeService;
        this.proposalDataService = proposalDataService;
        this.s3Loader = s3Loader;


        System.out.println("創立建構式 SignFormController");

    }

    // 簽核表單
    @RequestMapping (value = "/api/signForm", method = RequestMethod.GET)
    public void signForm(@RequestParam Integer formCaseId){

        System.out.println("簽核表單id:" + formCaseId);

        // 取得簽核表單的在資料表 proposal_data 中的資訊
        ProposalDataEntity proposalDataEntity = proposalDataService.findByformCaseId(formCaseId);
        if(proposalDataEntity==null){
           System.out.println("錯誤 表單"+formCaseId+"沒有搜尋到結果");
        }
        else {
            // 取得該表單的當前審核人員工編號
            String currentApprover = proposalDataEntity.getCurrentApprover();

            // 搜尋當前審核人在 employee 資料表中的資訊
            EmployeeEntity employeeEntity = employeeService.findByEmpId(currentApprover);

            // 搜尋當前審核人的上級
            String newApprover = employeeEntity.getSuperior();

            // 若當前審核人已無上級
            if (newApprover.equals("-")) {
                // 結案此表單
                proposalDataService.approverUpdate(formCaseId, 2, "-");
                System.out.println("表單簽核完成，此表單已結案");
            }
            // 若當前審核人仍有上級
            else {
                // 繼續送交下一站點
                proposalDataService.approverUpdate(formCaseId, 1, newApprover);
                System.out.println("表單簽核完成，此表單已送交下一站點");
            }
        }
    }

    // 退回表單
    @RequestMapping (value = "/api/rollbackForm", method = RequestMethod.GET)
    public void rollbackForm(@RequestParam Integer formCaseId){

        System.out.println("退回表單id:" + formCaseId);

        // 取得退回表單的在資料表 proposal_data 中的資訊
        ProposalDataEntity proposalDataEntity = proposalDataService.findByformCaseId(formCaseId);

        // 取得表單發起人的員工編號
        String empId = proposalDataEntity.getEmpId();

        // 退回表單
        proposalDataService.approverUpdate(formCaseId, 3, empId);

    }
//    private final S3Loader s3Loader;
//
//    public SignFormController() {
//        this.s3Loader = new S3Loader();
//    }

    @PostMapping("/api/user/signature")
    public ResponseEntity<?> saveSignature(@RequestParam Integer formCaseId, @org.springframework.web.bind.annotation.RequestBody String signature) {
        try {
            String s3Key = s3Loader.BASE_SIGNATURES_PATH + formCaseId + ".txt";
            s3Loader.uploadSignatureToS3(signature, s3Key);
            return ResponseEntity.ok("Signature saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Error saving signature");
        }
    }

    @GetMapping("/api/user/getSignatureFile")
    @ResponseBody
    public String readFile(@RequestParam Integer formCaseId) {
        String s3Key = s3Loader.BASE_SIGNATURES_PATH + formCaseId + ".txt";
        return s3Loader.readSignatureFromS3(s3Key);
    }

//    static class S3Loader {
//
//        private static final String BUCKET_NAME = "scsb"; // TODO: Replace with your bucket name
//        private static final String BASE_SIGNATURES_PATH = "signatures/";
//        private static final String ENDPOINT = "https://o0m5.sg.idrivee2-44.com";
//        private static final String ACCESS_KEY = "5sXb1p7xDgohvewN49Gp";
//        private static final String SECRET_KEY = "k3E88YE9msfmKUpLjY2uzFJ1okfzKZbDDScgWvNO";
//        private final S3Client s3;
//
//        public S3Loader() {
//            s3 = S3Client.builder()
//                    .region(Region.AP_SOUTHEAST_1)  // Adjust this if your region is different
//                    .endpointOverride(URI.create(ENDPOINT))
//                    .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(ACCESS_KEY, SECRET_KEY)))
//                    .build();
//        }
//
//        public void uploadSignatureToS3(String signature, String s3Key) {
//            s3.putObject(PutObjectRequest.builder()
//                            .bucket(BUCKET_NAME)
//                            .key(s3Key)
//                            .build(),
//                    RequestBody.fromString(signature));
//        }
//
//        public String readSignatureFromS3(String s3Key) {
//            try (ResponseInputStream<GetObjectResponse> is = s3.getObject(GetObjectRequest.builder()
//                    .bucket(BUCKET_NAME)
//                    .key(s3Key)
//                    .build())) {
//                return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
//                        .lines()
//                        .collect(Collectors.joining("\n"));
//            } catch (Exception e) {
//                throw new RuntimeException("Failed to load signature from S3", e);
//            }
//        }
//    }

//    @RequestMapping (value = "/api/user/postMessage", method = RequestMethod.POST)
//    public ResponseEntity<?> saveSignature(@RequestParam Integer formCaseId, @RequestBody String signature) {
//        try {
//            File signaturesDir = new File("src/main/resources/templates/forms/signatures");
//            if (!signaturesDir.exists()) {
//                signaturesDir.mkdirs();
//            }
//
//            File txtFile = new File(signaturesDir, formCaseId + ".txt");
//            System.out.println(txtFile);
//
//            // Fill in txt with signature
//            FileWriter writer = new FileWriter(txtFile);
//            writer.write(signature);
//            writer.close();
//
//
//            return ResponseEntity.ok("Signature saved successfully");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.badRequest().body("Error saving signature");
//        }
//    }
//
////    要避免拓墣問題
//    @GetMapping("/api/user/getSignatureFile")
//    @ResponseBody
//    public String readFile(@RequestParam Integer formCaseId) throws IOException {
//        Path path = Paths.get("/Users/chenchin/Downloads/WirelessDeviceForm-main 2/scsb/src/main/resources/", "templates/forms/signatures/" + formCaseId + ".txt");
//        return new String(Files.readAllBytes(path));
//    }




}
