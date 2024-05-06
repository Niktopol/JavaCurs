package com.coffehouse.app.service;

import com.coffehouse.app.model.MenuPosition;
import com.coffehouse.app.model.Order;
import com.coffehouse.app.model.OrderPosition;
import com.coffehouse.app.model.User;
import com.coffehouse.app.model.dto.MenuPositionDTO;
import com.coffehouse.app.model.dto.MenuPositionListDTO;
import com.coffehouse.app.model.key.PositionCountKey;
import com.coffehouse.app.repository.MenuPositionRepository;
import com.coffehouse.app.repository.OrderPositionRepository;
import com.coffehouse.app.repository.OrderRepository;
import com.coffehouse.app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final MenuPositionRepository menuPositionRepository;
    private final OrderPositionRepository orderPositionRepository;

    public String create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return "User exists";
        }else{
            userRepository.save(user);
            return "";
        }
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    private boolean isMenuPositionsDataCorrect(List<MenuPositionDTO> positions){
        for(int i = 0; i < positions.size() - 1; i++){
            for(int j = i + 1; j < positions.size(); j++){
                if (Objects.equals(positions.get(i).getPositionId(), positions.get(j).getPositionId())){
                    return false;
                }
            }
        }
        return true;
    }

    public String createOrder(MenuPositionListDTO menuPositions){
        List<MenuPosition> menuPositionList = new ArrayList<>();
        if(menuPositions.getMenuPositions().isEmpty()){
            return "Order cannot be empty";
        }
        if(!isMenuPositionsDataCorrect(menuPositions.getMenuPositions())){
            return "Incorrect order format";
        }
        for(MenuPositionDTO pos: menuPositions.getMenuPositions()){
            Optional<MenuPosition> position = menuPositionRepository.findById(pos.getPositionId());
            if(position.isPresent() && pos.getCount() > 0){
                menuPositionList.add(position.get());
            }else{
                return "Incorrect menu positions were found";
            }
        }
        float price = 0;
        Order order = orderRepository.save(new Order(false, 0,
                userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get()));
        for(int i = 0; i < menuPositionList.size(); i++){
            price +=  menuPositionList.get(i).getPrice() * menuPositions.getMenuPositions().get(i).getCount();
            orderPositionRepository.save(new OrderPosition(new PositionCountKey(order.getId(), menuPositionList.get(i).getId()), order, menuPositionList.get(i),
                    menuPositions.getMenuPositions().get(i).getCount()));
        }
        order.setPrice(price);
        return "";
    }

    public List<MenuPosition> getMenu(){
        return menuPositionRepository.findAll();
    }

    public List<Order> getActiveOrders(){
        return orderRepository.findAllByClosedTrueAndCustomer(userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).get());
    }

    public List<Order> getInactiveOrders(){
        return orderRepository.findAllByClosedFalseAndCustomer(userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).get());
    }

    public List<Order> getOrders(){
        return orderRepository.findAllByCustomerOrderByClosed(userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()).get());
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }
}
