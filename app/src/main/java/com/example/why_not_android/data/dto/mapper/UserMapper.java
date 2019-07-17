package com.example.why_not_android.data.dto.mapper;

import com.example.why_not_android.data.Models.User;
import com.example.why_not_android.data.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static List<User> map(List<UserDTO> eventDTOList) {
        List<User> eventList = new ArrayList<>();
        for (UserDTO userDTO : eventDTOList) {
            eventList.add(map(userDTO));
        }
        return eventList;
    }

    private static User map(UserDTO userDTO) {
        User user = new User();
        user.set_id(userDTO.get_id());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setBirthdate(userDTO.getBirthdate());
        user.setBanned(userDTO.getBanned());
        user.setBio(userDTO.getBio());
        user.setCreatedAt(userDTO.getCreatedAt());
        user.setIsDeleted(userDTO.getIsDeleted());
        user.setPhoto(userDTO.getPhoto());
        return user;
    }
}
