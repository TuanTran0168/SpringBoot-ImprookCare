/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tuantran.IMPROOK_CARE.service.Impl;

import com.tuantran.IMPROOK_CARE.Specifications.GenericSpecifications;
import com.tuantran.IMPROOK_CARE.components.cloudinary.CloudinaryComponent;
import com.tuantran.IMPROOK_CARE.components.datetime.DateFormatComponent;
import com.tuantran.IMPROOK_CARE.components.password.PasswordComponent;
import com.tuantran.IMPROOK_CARE.configs.cloudinary.CloudinaryConfig;
import com.tuantran.IMPROOK_CARE.dto.AddUserForAdminDTO;
import com.tuantran.IMPROOK_CARE.dto.ChangePasswordDTO;
import com.tuantran.IMPROOK_CARE.dto.ForgotPasswordDTO;
import com.tuantran.IMPROOK_CARE.dto.RegisterDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForAdminDTO;
import com.tuantran.IMPROOK_CARE.dto.UpdateUserForUserDTO;
import com.tuantran.IMPROOK_CARE.models.ProfileDoctor;
import com.tuantran.IMPROOK_CARE.models.ProfilePatient;
import com.tuantran.IMPROOK_CARE.models.Role;
import com.tuantran.IMPROOK_CARE.models.User;
import com.tuantran.IMPROOK_CARE.repository.ProfileDoctorRepository;
import com.tuantran.IMPROOK_CARE.repository.ProfilePatientRepository;
import com.tuantran.IMPROOK_CARE.repository.RoleRepository;
import com.tuantran.IMPROOK_CARE.repository.UserRepository;
import com.tuantran.IMPROOK_CARE.service.ProfileDoctorService;
import com.tuantran.IMPROOK_CARE.service.ProfilePatientService;
import com.tuantran.IMPROOK_CARE.service.UserService;
import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.util.ArrayList;
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
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Autowired
    private Environment environment;

    @Autowired
    private ProfilePatientRepository profilePatientRepository;

    @Autowired
    private ProfilePatientService profilePatientService;

    @Autowired
    private ProfileDoctorRepository profileDoctorRepository;

    @Autowired
    private ProfileDoctorService profileDoctorService;

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

        if (avatar != null && !avatar.isEmpty()) {
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
                userRegister.setRoleId(this.roleRepository.findRoleByRoleNameAndActiveTrue("ROLE_USER").get());
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
            user.setRoleId(this.roleRepository.findRoleByRoleNameAndActiveTrue("ROLE_DOCTOR").get());
            user.setCreatedDate(new Date());
            user.setActive(Boolean.TRUE);

            if (avatar != null) {
                String linkCloudinaryAvatar = cloudinaryComponent.Cloudinary(avatar).get("secure_url").toString();
                user.setAvatar(linkCloudinaryAvatar);
            }

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

    @Override
    public int changePassword(ChangePasswordDTO changePasswordDTO) {
        try {
            Optional<User> userOptional = this.userRepository.findUserByUsernameAndActiveTrue(changePasswordDTO.getUsername());
            if (userOptional.isPresent()) {

                User user = userOptional.get();

                if (this.passworldComponent.PasswordEncoder().matches(changePasswordDTO.getCurrentPassword(), user.getPassword())) {
                    user.setPassword(this.passworldComponent.PasswordEncoder().encode(changePasswordDTO.getNewPassword()));
                    this.userRepository.save(user);
                    return 1;
                } else {
                    return 3; // mật khẩu cũ không khớp
                }

            } else {
                return 2; // Có tìm được ai đâu mà update
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public int forgotPassword(ForgotPasswordDTO forgotPasswordDTO) {
        try {
            Optional<User> userOptional = this.userRepository.findUserByUsernameAndActiveTrue(forgotPasswordDTO.getUsername());
            if (userOptional.isPresent()) {

                User user = userOptional.get();

                user.setPassword(this.passworldComponent.PasswordEncoder().encode(forgotPasswordDTO.getNewPassword()));
                this.userRepository.save(user);
                return 1;

            } else {
                return 2; // Có tìm được ai đâu mà update
            }

        } catch (DataAccessException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        } catch (NoSuchElementException ex) {
            Logger.getLogger(UserServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }

    @Override
    public Page<User> findAllUserPage(int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));
        return this.userRepository.findUserByActiveTrue(page);
    }

    @Override
    public Page<User> findAllUserPageSpec(Map<String, String> params) {
        String pageNumber = params.get("pageNumber");
        String firstname = params.get("firstname");
        String lastname = params.get("lastname");
        String roleId = params.get("roleId");
        String gender = params.get("gender");

        List<Specification<User>> listSpec = new ArrayList<>();
        int defaultPageNumber = 0;
        Sort mySort = Sort.by("createdDate").descending();
        Pageable page = PageRequest.of(defaultPageNumber, Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
        if (pageNumber != null && !pageNumber.isEmpty()) {
            if (!pageNumber.equals("NaN")) {
                page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")), mySort);
            }
        }

        if (firstname != null && !firstname.isEmpty()) {
            Specification<User> spec = GenericSpecifications.fieldContains("firstname", firstname);
            listSpec.add(spec);
        }

        if (lastname != null && !lastname.isEmpty()) {
            Specification<User> spec = GenericSpecifications.fieldContains("lastname", lastname);
            listSpec.add(spec);
        }

        if (roleId != null && !roleId.isEmpty()) {
            Optional<Role> roleOptional = this.roleRepository.findRoleByRoleIdAndActiveTrue(Integer.parseInt(roleId));
            if (roleOptional.isPresent()) {
                Specification<User> spec = GenericSpecifications.fieldEquals("roleId", roleOptional.get());
                listSpec.add(spec);
            }
        }

        if (gender != null && !gender.isEmpty()) {
            Specification<User> spec = GenericSpecifications.fieldEquals("gender", Boolean.parseBoolean(gender));
            listSpec.add(spec);
        }

        Specification<User> spec = GenericSpecifications.fieldEquals("active", Boolean.TRUE);
        listSpec.add(spec);

        return this.userRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
    }

//    @Override
//    public Page<User> findAllUserPageSpec_test(Map<String, String> params) {
//        String pageNumber = params.get("pageNumber");
//        String firstname = params.get("firstname");
//        String lastname = params.get("lastname");
//        String roleId = params.get("roleId");
//        String gender = params.get("gender");
//
//        List<Specification<User>> listSpec = new ArrayList<>();
//        Pageable page = null; // Cái này null là bay màu luôn
//        if (pageNumber != null && !pageNumber.isEmpty()) {
//            page = PageRequest.of(Integer.parseInt(pageNumber), Integer.parseInt(this.environment.getProperty("spring.data.web.pageable.default-page-size")));
//        }
//
//        if (firstname != null && !firstname.isEmpty()) {
//            Specification<User> spec = GenericSpecifications.fieldContains("firstname", firstname);
//            listSpec.add(spec);
//        }
//
//        if (lastname != null && !lastname.isEmpty()) {
//            Specification<User> spec = GenericSpecifications.fieldContains("lastname", lastname);
//            listSpec.add(spec);
//        }
//
//        if (roleId != null && !roleId.isEmpty()) {
//            Optional<Role> roleOptional = this.roleRepository.findRoleByRoleIdAndActiveTrue(Integer.parseInt(roleId));
//            if (roleOptional.isPresent()) {
//                Specification<User> spec = GenericSpecifications.fieldEquals("roleId", roleOptional.get());
//                listSpec.add(spec);
//            }
//        }
//
//        if (gender != null && !gender.isEmpty()) {
//            Specification<User> spec = GenericSpecifications.fieldEquals("gender", Boolean.parseBoolean(gender));
//            listSpec.add(spec);
//        }
//
//        return this.userRepository.findAll(GenericSpecifications.createSpecification(listSpec), page);
//    }
    @Override
    @Transactional
    public int softDeleteUser(int userId) {
        Optional<User> userOptional = this.userRepository.findUserByUserIdAndActiveTrue(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getActive().equals(Boolean.TRUE)) {
                user.setActive(Boolean.FALSE);

                List<ProfilePatient> listProfilePatient = this.profilePatientRepository.findProfilePatientByUserIdAndActiveTrue(user);

                for (ProfilePatient profilePatient : listProfilePatient) {
                    this.profilePatientService.softDeleteProfilePatient(profilePatient.getProfilePatientId());
                }

                List<ProfileDoctor> listProfileDoctor = this.profileDoctorRepository.findProfileDoctorByUserIdAndActiveTrue(user);

                for (ProfileDoctor profileDoctor : listProfileDoctor) {
                    this.profileDoctorService.softDeleteProfileDoctor(profileDoctor.getProfileDoctorId());
                }

                return 1;
            } else {
                System.out.println("User: " + user.getActive());
                return 2;
            }
        } else {
            return 3; // Không tìm được để xóa
        }
    }
}
