package ua.training.cruise_company_servlet.web.dto.converter;

import ua.training.cruise_company_servlet.entity.User;
import ua.training.cruise_company_servlet.web.dto.UserDTO;

public class UserDTOConverter {

    public static UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();

        if(user == null){
            return dto;
        }

        dto.setId( user.getId() );
        dto.setEmail( user.getEmail());
        dto.setFirstNameEn( user.getFirstNameEn() );
        dto.setLastNameEn( user.getLastNameEn() );
        dto.setFirstNameNative( user.getFirstNameNative() );
        dto.setLastNameNative( user.getLastNameNative() );
        dto.setRole( user.getRole());
        return dto;
    }
}
