package com.scsb.t.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scsb.t.dto.ProposalMessageDTO;
import com.scsb.t.entity.ProposalDataEntity;
import com.scsb.t.jsonobj.FormInfo;
import com.scsb.t.service.ProposalDataServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.scsb.t.jsonobj.Data;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
public class SendDataController {

    private ProposalDataServiceImpl proposalDataService;

    private final ObjectMapper objectMapper;

    @Autowired
    public SendDataController(ProposalDataServiceImpl proposalDataService, ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        System.out.println("創立建構式 SendDataController");
        this.proposalDataService = proposalDataService;
    }
    //回傳待審表單
    @RequestMapping(value = "/api/user/pending-records", method = RequestMethod.GET, produces = "application/json")
    public String getFormInfo(@RequestParam(value = "empId",required = false) String EmpId) throws JsonProcessingException {
        System.out.println("Enter SendDataController EmpId="+EmpId);
        List<ProposalDataEntity> proposalDataEntities = proposalDataService.findByCurrentApprover(EmpId);


        Data data = new Data(proposalDataEntities);
        String json = objectMapper.writeValueAsString(data);

        return json;
    }
    //回傳個人發案
    @RequestMapping(value = "/api/user/personal-records", method = RequestMethod.GET, produces = "application/json")
    public String getPersonalRecords(@RequestParam(value = "empId",required = false) String EmpId) throws JsonProcessingException {
        System.out.println("Enter SendDataController PersonalRecord="+EmpId);
        List<ProposalDataEntity> proposalDataEntities = proposalDataService.findByEmpId(EmpId);
        for (int i = 0; i < proposalDataEntities.size(); i++) {
            System.out.println(proposalDataEntities.get(i).getEmpId() + proposalDataEntities.get(i).getName());
        }

        Data data = new Data(proposalDataEntities);
        String json = objectMapper.writeValueAsString(data);

        return json;
    }
    //回傳form.io的內容到前端
    @RequestMapping(value = "/api/user/personal-formiojson", method = RequestMethod.GET,produces = "application/json")
    public String getFormIoData(@RequestParam(value = "formCaseId",required = false) String formCaseId) throws JsonProcessingException {
        System.out.println("Enter getFormIoData "+formCaseId);
        String formIoData=proposalDataService.findFormIoData(Integer.parseInt(formCaseId));

        formIoData="{\"data\":"+formIoData+"}";

        return formIoData;
    }
    //把留言傳入對應的表單欄位message
    @RequestMapping(value = "/api/user/postMessage", method = RequestMethod.POST,produces = MediaType.TEXT_PLAIN_VALUE)
    public String postMessageToDataBase(@RequestParam(value = "formCaseId",required = false ) String formCaseId , @RequestBody ProposalMessageDTO proposalMessageDTO)  {

        System.out.println(proposalMessageDTO.toString());
        System.out.println("Enter postMessageToDataBase "+formCaseId);
        boolean result=proposalDataService.putMessageIntoDB(proposalMessageDTO.getContent(),formCaseId);
        if(result){
            return "成功新增留言";
        }
        else{
            return "新增留言失敗";
        }

    }
    //表單分析
    @RequestMapping(value = "/api/user/getList", method = RequestMethod.GET, produces = "application/json")
    public String getTemplateList(@RequestParam(value = "tempId", required = true) int formTemplateId) throws JsonProcessingException {
        System.out.println("Enter getTemplateList form_template_id=" + formTemplateId);

        // Assuming you have a service method to retrieve the data based on formTemplateId
        List<ProposalDataEntity> templateList = proposalDataService.findTemplatesByFormTemplateId(formTemplateId);

        Data data = new Data(templateList);
        String json = objectMapper.writeValueAsString(data);

        return json;
    }

}