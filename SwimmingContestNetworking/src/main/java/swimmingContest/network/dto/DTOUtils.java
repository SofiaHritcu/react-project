package swimmingContest.network.dto;

import swimmingContest.model.User;

public class DTOUtils {
    public static User getFromDTO(UserDTO usdto){
        Long id=usdto.getId();
        String pass=usdto.getPassword();
        String fstName=usdto.getFirstName();
        String lstName=usdto.getLastName();
        User user=new User(fstName,lstName,pass);
        user.setId(id);
        return user;
    }
    public static UserDTO getDTO(User user){
        Long id=user.getId();
        String pass=user.getPassword();
        String fstName=user.getFirstName();
        String lstName=user.getLastName();
        UserDTO userDTO=new UserDTO(fstName,lstName,pass);
        userDTO.setId(id);
        return userDTO;
    }

    public static UserDTO[] getDTO(User[] users){
        UserDTO[] frDTO=new UserDTO[users.length];
        for(int i=0;i<users.length;i++)
            frDTO[i]=getDTO(users[i]);
        return frDTO;
    }

    public static User[] getFromDTO(UserDTO[] users){
        User[] friends=new User[users.length];
        for(int i=0;i<users.length;i++){
            friends[i]=getFromDTO(users[i]);
        }
        return friends;
    }
}
