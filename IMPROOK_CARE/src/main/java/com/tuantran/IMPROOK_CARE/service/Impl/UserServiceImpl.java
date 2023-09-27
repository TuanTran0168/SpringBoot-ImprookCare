/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.components.password.PasswordComponent;
import com.tuantran.IMPROOK_CARE.configs.cloudinary.CloudinaryConfig;
import com.tuantran.IMPROOK_CARE.dto.AddUserForAdminDTO;
import com.tuantran.IMPROOK_CARE.dto.RegisterDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForAdminDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForUserDTO;
import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Administrator
 */
//@Service
@Service("userDetailsService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordComponent passworldComponent;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CloudinaryComponent cloudinaryComponent;

    @Autowired
    private DateFormatComponent dateFormatComponent;

    @Override
    public User findUserByUsernameAndActiveTrue(String username) {
        try {
            return this.userRepository.findUserByUsernameAndActiveTrue(username).get();
        } catch (NoSuchElementException ex) {
            return null;
        }
    }

    @Override
    public User addUser(Map<String, String> params, MultipartFile avatar) {
        User user = new User();
        String phonenumber = params.get("phonenumber");
        String username = phonenumber; //username sẽ là số điện thoại lấy qua
        String password = params.get("password");

        String firstname = params.get("firstname");
        String lastname = params.get("lastname");
        String gender = params.get("gender");

        // Đăng ký chỉ dùng 3 cái này thôi
        user.setUsername(username);
        user.setPassword(this.passworldComponent.PasswordEncoder().encode(password));
//        user.setPhonenumber(phonenumber);

        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setGender(Boolean.parseBoolean(gender));

        if (avatar != null) {
            String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
            user.setAvatar(linkCloudinaryAvatar);
        }

        return this.userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByUsernameAndActiveTrue(username);

        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Username không tồn tại!");
        }

        User user = userOptional.get();
        Set<GrantedAuthority> authorities = new HashSet<>();
        Optional<Role> role = this.roleRepository.findById(user.getRoleId().getRoleId());
        String roleName = role.get().getRoleName();
        authorities.add(new SimpleGrantedAuthority(roleName));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(), authorities);
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findUserByActiveTrue();
    }

    @Override
    public User findUserByUsername(String username) {
//        try {
//            
//        }
//        catch(NoSuchElementException ex) {
//            return null;
//        }

        try {
            Optional<User> userOptional = this.userRepository.findUserByUsername(username);
            if (userOptional.isPresent()) {
                return userOptional.get();
            }
        } catch (NoSuchElementException ex) {
            return null;
        }
        return null;
    }

    @Override
    public int updateUser(UpdateUserForUserDTO updateUserForUserDTO, MultipartFile avatar) {
        try {
            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(updateUserForUserDTO.getUserId()));
            if (userOptional.isPresent()) {

                User user = userOptional.get();
                user.setFirstname(updateUserForUserDTO.getFirstname());
                user.setLastname(updateUserForUserDTO.getLastname());
                user.setGender(updateUserForUserDTO.getGender());
                user.setBirthday(this.dateFormatComponent.myDateFormat().parse(updateUserForUserDTO.getBirthday()));
                user.setUpdatedDate(new Date());

                if (avatar != null && !avatar.isEmpty()) {
                    try {
                        String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                        user.setAvatar(linkCloudinaryAvatar);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.userRepository.save(user);
                return 1;
            } else {
                return 2; // Có tìm được ai đâu mà update
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (ParseException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }

    }

    @Override
    public User findUserByUserIdAndActiveTrue(int userId) {
        try {
            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(userId);
            if (userOptional.isPresent()) {
                return userOptional.get();
            }

        } catch (NoSuchElementException ex) {
            return null;
        }
        return null;
    }

    @Override
    public int registerUser(RegisterDTO registerDTO) {
        try {
// Thực ra ở tầng verification đã xác thực phonenumber qua twilio rồi nên kiểm tra thêm cho yên tâm
            Optional<User> userOptional = this.userRepository.findUserByUsername(registerDTO.getUsername());

            if (!userOptional.isPresent()) {
                User userRegister = new User();
                userRegister.setUsername(registerDTO.getUsername()); //Username là phonenumber (Quy ước mới)
                userRegister.setPassword(this.passworldComponent.PasswordEncoder().encode(registerDTO.getPassword()));
                userRegister.setFirstname(registerDTO.getFirstname());
                userRegister.setLastname(registerDTO.getLastname());
                userRegister.setGender(registerDTO.getGender());
                userRegister.setRoleId(this.roleRepository.findRoleByRoleNameAndActiveTrue("USER").get());
                userRegister.setCreatedDate(new Date());
                userRegister.setActive(Boolean.TRUE);
                this.userRepository.save(userRegister);
                return 1;
            } else {
                return 0; // Tồn tại phonenumber
            }

        } catch (NoSuchElementException ex) {
            return 0;
        }

    }

    @Override
    public int addUser(AddUserForAdminDTO addUserForAdminDTO, MultipartFile avatar) {
        try {
            User user = new User();
            Optional<User> userOptional = this.userRepository.findUserByUsernameAndActiveTrue(addUserForAdminDTO.getUsername());

            if (userOptional.isPresent()) {
                return 2; // Tồn tại số điện thoại
            } else {
                user.setUsername(addUserForAdminDTO.getUsername());
            }

//            if (!userOptional.isPresent()) {
//                user.setUsername(addUserForAdminDTO.getUsername());
//            } else {
//                return 2; // Tồn tại số điện thoại
//
//            }

            user.setPassword(this.passworldComponent.PasswordEncoder().encode(addUserForAdminDTO.getPassword()));
            user.setFirstname(addUserForAdminDTO.getFirstname());
            user.setLastname(addUserForAdminDTO.getLastname());
            user.setBirthday(this.dateFormatComponent.myDateFormat().parse(addUserForAdminDTO.getBirthday()));
            user.setGender(addUserForAdminDTO.getGender());
            user.setRoleId(this.roleRepository.findRoleByRoleNameAndActiveTrue("DOCTOR").get());
            user.setCreatedDate(new Date());
            user.setActive(Boolean.TRUE);

            this.userRepository.save(user);
            return 1;
        } catch (ParseException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int updateUser(UpdateUserForAdminDTO updateUserForAdminDTO, MultipartFile avatar) {
        try {
            Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(Integer.parseInt(updateUserForAdminDTO.getUserId()));
            if (userOptional.isPresent()) {

                User user = userOptional.get();
                user.setFirstname(updateUserForAdminDTO.getFirstname());
                user.setLastname(updateUserForAdminDTO.getLastname());
                user.setGender(updateUserForAdminDTO.getGender());
                user.setBirthday(this.dateFormatComponent.myDateFormat().parse(updateUserForAdminDTO.getBirthday()));

                Optional<Role> roleOptional = this.roleRepository.findRoleByRoleIdAndActiveTrue(Integer.parseInt(updateUserForAdminDTO.getRoleId()));
                if (roleOptional.isPresent()) {
                    user.setRoleId(roleOptional.get());
                }
                user.setUpdatedDate(new Date());

                if (avatar != null && !avatar.isEmpty()) {
                    try {
                        String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                        user.setAvatar(linkCloudinaryAvatar);
                    } catch (Exception ex) {
                        Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                this.userRepository.save(user);
                return 1;
            } else {
                return 2; // Có tìm được ai đâu mà update
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (ParseException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
