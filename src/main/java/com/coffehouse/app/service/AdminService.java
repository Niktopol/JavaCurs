package com.coffehouse.app.service;

import com.coffehouse.app.model.User;
import com.coffehouse.app.model.dto.UserDTO;
import com.coffehouse.app.model.dto.UserInfoDTO;
import com.coffehouse.app.model.dto.UserInfoListDTO;
import com.coffehouse.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.Session;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.coffehouse.app.model.User.Role.WORKER;

@AllArgsConstructor
@Service
public class AdminService {
    UserService userService;
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    FindByIndexNameSessionRepository<? extends Session> sessionRepository;

    public String addWorker(UserDTO workerData){
        if (workerData.getName().isEmpty()){
            return "'name' value is required";
        }
        if (workerData.getUsername().isEmpty()){
            return "'username' value is required";
        }
        if (workerData.getPassword().isEmpty()){
            return "'password' value is required";
        }
        User worker = new User(workerData.getName(), workerData.getUsername(),
                passwordEncoder.encode(workerData.getPassword()), WORKER, false);

        return userService.create(worker);
    }

    public List<UserInfoDTO> getWorkers(UserInfoDTO workerData){
        if(workerData == null || workerData.getId() == -1 && workerData.getUsername().isEmpty() && workerData.getName().isEmpty()){
            return userRepository.findAllByRole(WORKER).stream().map(UserInfoDTO::new).toList();
        }else{
            if(workerData.getId() > 0){
                User worker = userRepository.findByRoleAndId(WORKER, workerData.getId()).orElse(null);
                return worker != null ? List.of(new UserInfoDTO(worker)): List.of();
            }else if(!workerData.getUsername().isEmpty()){
                User worker = userRepository.findByRoleAndUsername(WORKER, workerData.getUsername()).orElse(null);
                return worker != null ? List.of(new UserInfoDTO(worker)): List.of();
            }else{
                return userRepository.findAllByRoleAndName(WORKER, workerData.getName()).stream().map(UserInfoDTO::new).toList();
            }
        }
    }

    public String removeWorker(UserInfoDTO workerData){
        if(workerData.getId() == -1 && workerData.getUsername().isEmpty()){
            return "'id' value or 'username' value is required";
        }else{
            if (workerData.getId() != -1){
                User worker = userRepository.findByRoleAndId(WORKER, workerData.getId()).orElse(null);
                if(worker != null){
                    userRepository.delete(worker);
                    return "";
                }else{
                    return "Worker with id " + workerData.getId() + " not found";
                }
            }else{
                User worker = userRepository.findByRoleAndUsername(WORKER, workerData.getUsername()).orElse(null);
                if(worker != null){
                    for (Session i: sessionRepository.findByPrincipalName(worker.getUsername()).values()){
                        sessionRepository.deleteById(i.getId());
                    }
                    userRepository.delete(worker);
                    return "";
                }else{
                    return "Worker with username " + workerData.getUsername() + " not found";
                }
            }
        }
    }

    public String enableOrDisableWorker(UserInfoDTO workerData, boolean enOrDis){
        if(workerData.getId() == -1 && workerData.getUsername().isEmpty()){
            return "'id' value or 'username' value is required";
        }else{
            if (workerData.getId() != -1){
                User worker = userRepository.findByRoleAndId(WORKER, workerData.getId()).orElse(null);
                if(worker != null){
                    worker.setEnabled(enOrDis);
                    userRepository.save(worker);
                    if(!enOrDis){
                        for (Session i: sessionRepository.findByPrincipalName(worker.getUsername()).values()){
                            sessionRepository.deleteById(i.getId());
                        }
                    }
                    return String.format("Worker {id: %d, name: %s, username: %s} %sabled",
                            worker.getId(), worker.getName(), worker.getUsername(), enOrDis? "en": "dis");
                }else{
                    return "Worker with id " + workerData.getId() + " not found";
                }
            }else{
                User worker = userRepository.findByRoleAndUsername(WORKER, workerData.getUsername()).orElse(null);
                if(worker != null){
                    worker.setEnabled(enOrDis);
                    userRepository.save(worker);
                    if(!enOrDis){
                        for (Session i: sessionRepository.findByPrincipalName(worker.getUsername()).values()){
                            sessionRepository.deleteById(i.getId());
                        }
                    }
                    return String.format("Worker {id: %d, name: %s, username: %s} %sabled",
                            worker.getId(), worker.getName(), worker.getUsername(), enOrDis? "en": "dis");
                }else{
                    return "Worker with username " + workerData.getUsername() + " not found";
                }
            }
        }
    }

    public String enableOrDisableWorkers(boolean enOrDis){
        List<User> workers = userRepository.findAllByRole(WORKER);
        for(User worker: workers){
            worker.setEnabled(enOrDis);
            userRepository.save(worker);
            if(!enOrDis){
                for (Session i: sessionRepository.findByPrincipalName(worker.getUsername()).values()){
                    sessionRepository.deleteById(i.getId());
                }
            }
        }
        return String.format("All workers were %sabled", enOrDis? "en": "dis");
    }

    public String enableOrDisableWorkersList(UserInfoListDTO workersData, boolean enOrDis){
        Set<Long> enOrDisAbledIds = new TreeSet<>();
        int wrongCount = 0;
        for(UserInfoDTO workerData: workersData.getInfos()){
            if(workerData.getId() == -1 && workerData.getUsername().isEmpty()){
                wrongCount += 1;
            }else{
                User worker;
                if (workerData.getId() != -1){
                    worker = userRepository.findByRoleAndId(WORKER, workerData.getId()).orElse(null);
                }else{
                    worker = userRepository.findByRoleAndUsername(WORKER, workerData.getUsername()).orElse(null);
                }
                if(worker != null){
                    worker.setEnabled(enOrDis);
                    userRepository.save(worker);
                    if(!enOrDis){
                        for (Session i: sessionRepository.findByPrincipalName(worker.getUsername()).values()){
                            sessionRepository.deleteById(i.getId());
                        }
                    }
                    enOrDisAbledIds.add(worker.getId());
                }else{
                    wrongCount += 1;
                }
            }
        }
        return String.format("%sabled workers with ids: %s; wrong details given: %d", enOrDis? "En": "Dis", Arrays.toString(enOrDisAbledIds.toArray()), wrongCount);
    }
}
