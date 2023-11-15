package com.scsb.t.controller;

import com.scsb.t.dao.EmployeeDAO;
import com.scsb.t.dao.TemplatesRegistrationRepository;
import com.scsb.t.dto.ProposalDataDTO;
import com.scsb.t.entity.EmployeeEntity;
import com.scsb.t.entity.ProposalDataEntity;
import com.scsb.t.entity.TemplatesRegistrationEntity;
import com.scsb.t.service.EmployeeService;
import com.scsb.t.service.EmployeeServiceImpl;
import com.scsb.t.service.ProposalDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.nio.file.LinkOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class SaveFormController {

    private EmployeeServiceImpl employeeService;

    private ProposalDataServiceImpl proposalDataService;

    private TemplatesRegistrationRepository templatesRegistrationRepository;


    @Autowired
    public SaveFormController(EmployeeServiceImpl employeeService,
                              ProposalDataServiceImpl proposalDataService,
                              TemplatesRegistrationRepository templatesRegistrationRepository) {
        this.employeeService = employeeService;
        this.proposalDataService = proposalDataService;
        this.templatesRegistrationRepository = templatesRegistrationRepository;

        System.out.println("創立建構式 SaveFormController");

    }

    @PostMapping(value = "/api/saveProposalData", produces = MediaType.TEXT_PLAIN_VALUE)
    public String SaveProposalData(@RequestBody ProposalDataDTO proposalDataDTO) {

        ProposalDataEntity proposalDataEntity = new ProposalDataEntity();
        System.out.println("抓取當前時間---------------------------------------------------------------");
        // 取得當前時間
        LocalDateTime now = LocalDateTime.now();
        // 定義 datetime 格式
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 格式化當前時間
        String formattedNow = now.format(formatter);
        System.out.println("結束---------------------------------------------------------------------");

        // proposalDataDTO這個class接收前端傳過來的json物件
        System.out.println("印出前端傳送過來的json資訊--------------------------------------------------");
        System.out.println(proposalDataDTO.toString());

        // 取得對應的templates_registration 表格中資料
        Integer formTemplateId = Integer.valueOf(proposalDataDTO.getForm_template_id());
        Optional<TemplatesRegistrationEntity> templatesRegistrationEntity = templatesRegistrationRepository.findById(formTemplateId);
        System.out.println("獲取對應的 templates_registation 資料-------------------------------------");
        String templateName = templatesRegistrationEntity.get().getTemplateName();
        String templateSignType = templatesRegistrationEntity.get().getSignType();
        System.out.println("模板名稱：" + templateName + " / 是否送簽: " + templateSignType);

        // 取得前端傳送過來的員工 empid 傳入employee資料表查詢相關結果
        String empid = proposalDataDTO.getEmp_id();
        System.out.println("以員工id=" + empid + "進行查詢------------------------------------------------");
        EmployeeEntity employeeEntity = employeeService.findByEmpId(empid);
        System.out.println("印出員工id=" + empid + "的資訊------------------------------------------------");
        System.out.println("employeeEntity=" + employeeEntity.toString());

        String name = employeeEntity.getName(); // 名稱
        String department = employeeEntity.getDepartment(); // 部門
        String rank = employeeEntity.getRank(); // 職級

        // 抓取 templates_registration 中對應的表單模板名稱作為 project title 和 proposal topic 中存放的值
        String projectTitle = templatesRegistrationEntity.get().getTemplateName();
        String proposalTopic = templatesRegistrationEntity.get().getTemplateName();

        String issueTime = formattedNow;
        String priorityLevel = "中等";
        String securityLevel = "公開";


        int status = 0; // 表單的狀態，1:進行中 / 2:已完成
        String currentApprover = "-"; // 當前使用者的上級
        String arrivalDate = "-"; // 表單抵達時間

        // 根據 templates_registration 中對應的表單模板送簽型態，作為 status 和 currentApprover 中存放的值
        if (templateSignType.equals("Y")) {
            status = 1;
            currentApprover = employeeEntity.getSuperior();
            arrivalDate = formattedNow;
        } else if (templateSignType.equals("N")) {
            status = 2;
            currentApprover = "-";
            arrivalDate = formattedNow;
        }

        String dataFormIo=proposalDataDTO.getData();

        // 創建表單 輸入表單資訊到資料表
        System.out.println("創建表單 輸入表單資訊到 資料表proposal data中-----------------------------------");
        proposalDataEntity.setEmpId(empid);
        proposalDataEntity.setName(name);
        proposalDataEntity.setDepartment(department);
        proposalDataEntity.setRank(rank);
        proposalDataEntity.setProjectTitle(projectTitle);
        proposalDataEntity.setProposalTopic(proposalTopic);
        proposalDataEntity.setIssueDate(issueTime);
        proposalDataEntity.setPriorityLevel(priorityLevel);
        proposalDataEntity.setSecurityLevel(securityLevel);
        proposalDataEntity.setStatus(status);
        proposalDataEntity.setCurrentApprover(currentApprover);
        proposalDataEntity.setArrivalDate(arrivalDate);
        proposalDataEntity.setFormTemplateId(formTemplateId);
        proposalDataEntity.setData(dataFormIo);
        proposalDataEntity.setMessage("");
        proposalDataService.saveFormItem(proposalDataEntity);
        System.out.println("結束-----------------------------------------------------------------------");
        proposalDataEntity = null; // 回收
        return "success";
    }

    @PostMapping(value = "/api/saveFormData")
    public void SaveFormData(@RequestBody String formData) {
        System.out.println("儲存使用者填寫的 form data 資訊-----------------------------------------------");
        System.out.println(formData);
    }
}
