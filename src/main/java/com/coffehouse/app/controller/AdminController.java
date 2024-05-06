package com.coffehouse.app.controller;

import com.coffehouse.app.model.dto.UserDTO;
import com.coffehouse.app.model.dto.UserInfoDTO;
import com.coffehouse.app.model.dto.UserInfoListDTO;
import com.coffehouse.app.service.AdminService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    AdminService adminService;

    @PostMapping("/worker/add")
    public ResponseEntity<String> addWorker(@RequestBody UserDTO workedData){
        String resp = adminService.addWorker(workedData);
        if(resp.isEmpty()){
            return ResponseEntity.status(HttpServletResponse.SC_CREATED).body("Worker created");
        }else{
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        }
    }

    @GetMapping("/worker/get")
    public ResponseEntity<List<UserInfoDTO>> getWorkers(@RequestBody(required = false) UserInfoDTO workerData){
        return ResponseEntity.ok(adminService.getWorkers(workerData));
    }

    @PostMapping("/worker/remove")
    public ResponseEntity<String> removeWorker(@RequestBody UserInfoDTO workerData){
        String resp = adminService.removeWorker(workerData);
        if(resp.isEmpty()){
            return ResponseEntity.ok("Worker deleted");
        }else{
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body(resp);
        }
    }

    @PostMapping("/worker/enable")
    public ResponseEntity<String> enableWorker(@RequestBody UserInfoDTO workerData){
        return ResponseEntity.ok(adminService.enableOrDisableWorker(workerData, true));
    }

    @PostMapping("/worker/enable/all")
    public ResponseEntity<String> enableWorkers(){
        return ResponseEntity.ok(adminService.enableOrDisableWorkers(true));
    }

    @PostMapping("/worker/enable/list")
    public ResponseEntity<String> enableWorkersList(@RequestBody UserInfoListDTO workersData){
        return ResponseEntity.ok(adminService.enableOrDisableWorkersList(workersData, true));
    }

    @PostMapping("/worker/disable")
    public ResponseEntity<String> disableWorker(@RequestBody UserInfoDTO workerData){
        return ResponseEntity.ok(adminService.enableOrDisableWorker(workerData, false));
    }

    @PostMapping("/worker/disable/all")
    public ResponseEntity<String> disableWorkers(){
        return ResponseEntity.ok(adminService.enableOrDisableWorkers(false));
    }

    @PostMapping("/worker/disable/list")
    public ResponseEntity<String> disableWorkersList(@RequestBody UserInfoListDTO workersData){
        return ResponseEntity.ok(adminService.enableOrDisableWorkersList(workersData, false));
    }
}
