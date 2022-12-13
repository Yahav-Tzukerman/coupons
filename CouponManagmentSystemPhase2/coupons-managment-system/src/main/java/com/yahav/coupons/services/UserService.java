package com.yahav.coupons.services;

import com.yahav.coupons.converters.UserEntityToUserResponseModelConverter;
import com.yahav.coupons.converters.UserRequestModelToUserEntityConverter;
import com.yahav.coupons.entities.CompanyEntity;
import com.yahav.coupons.entities.UserEntity;
import com.yahav.coupons.enums.ErrorType;
import com.yahav.coupons.enums.Role;
import com.yahav.coupons.enums.Status;
import com.yahav.coupons.exceptions.ServerException;
import com.yahav.coupons.models.UserLoginData;
import com.yahav.coupons.models.UserRequestModel;
import com.yahav.coupons.models.UserResponseModel;
import com.yahav.coupons.repositories.UserRepository;
import com.yahav.coupons.security.UserPrincipal;
import com.yahav.coupons.security.jwt.JwtProvider;
import com.yahav.coupons.security.jwt.JwtUserValidator;
import com.yahav.coupons.validations.RegexValidations;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final JwtUserValidator jwtUserValidator;
    private final CompanyService companyService;
    private final CustomerService customerService;
    private final UserEntityToUserResponseModelConverter userEntityToUserResponseModelConverter;
    private final UserRequestModelToUserEntityConverter userRequestModelToUserEntityConverter;

    public UserService(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager
            , @Lazy JwtProvider jwtProvider, @Lazy JwtUserValidator jwtUserValidator, @Lazy CompanyService companyService
            , @Lazy CustomerService customerService, @Lazy UserEntityToUserResponseModelConverter userEntityToUserResponseModelConverter
            , @Lazy UserRequestModelToUserEntityConverter userRequestModelToUserEntityConverter) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.jwtUserValidator = jwtUserValidator;
        this.companyService = companyService;
        this.customerService = customerService;
        this.userEntityToUserResponseModelConverter = userEntityToUserResponseModelConverter;
        this.userRequestModelToUserEntityConverter = userRequestModelToUserEntityConverter;
    }

    @PostConstruct
    private void setAdminIfNotExist() throws ServerException {
        if(!userRepository.existsByUsername("admin@admin.com")) {
            UserRequestModel user = new UserRequestModel();
            user.setUsername("admin@admin.com");
            user.setPassword("Admin1234");
            user.setRole(Role.ADMIN);
            addUser(user);
        }
    }

    // Public
    public UserResponseModel login(UserLoginData userLoginData) throws ServerException {
        if(!userRepository.existsByUsername(userLoginData.getUsername())){
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        handleUserSuspensions(userLoginData.getUsername(), userLoginData.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginData.getUsername(), userLoginData.getPassword())
        );
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String jwt = jwtProvider.generateToken(userPrincipal);

        UserEntity userEntity = userPrincipal.getUserEntity();
        userEntity.setToken(jwt);
        return userEntityToUserResponseModelConverter.convert(userEntity);
    }

    private void handleUserSuspensions(String username, String password) throws ServerException {
        UserEntity userEntity = getUserEntityByUsername(username).get(0);
        if (userEntity == null) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        if (userEntity.getStatus() == Status.SUSPENDED) {
            if (userEntity.getSuspensionTimeStamp().isAfter(LocalDateTime.now())) {
                throw new ServerException(ErrorType.USER_IS_SUSPENDED);
            }
            userEntity.setAmountOfFailedLogins(0);
            userEntity.setStatus(Status.ACTIVE);
            userEntity.setSuspensionTimeStamp(null);
            userRepository.save(userEntity);
        }
        if (userEntity.getAmountOfFailedLogins() == 5) {
            userEntity.setStatus(Status.SUSPENDED);
            userEntity.setSuspensionTimeStamp(LocalDateTime.now().plusMinutes(5L));
            userRepository.save(userEntity);
            throw new ServerException(ErrorType.USER_IS_SUSPENDED);
        }
        if (!passwordEncoder.matches(password, userEntity.getPassword())) {
            userEntity.setAmountOfFailedLogins(userEntity.getAmountOfFailedLogins() + 1);
            userRepository.save(userEntity);
            throw new ServerException(ErrorType.LOGIN_FAILURE);
        } else {
            userEntity.setAmountOfFailedLogins(0);
            userEntity.setStatus(Status.ACTIVE);
            userEntity.setSuspensionTimeStamp(null);
            userRepository.save(userEntity);
        }
    }

    // Public
    public UserResponseModel changePassword(String oldPassword, String newPassword, HttpServletRequest request) throws ServerException {
        Authentication authentication = jwtProvider.getAuthentication(request);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        if (!passwordEncoder.matches(oldPassword, userPrincipal.getPassword())) {
            throw new ServerException(ErrorType.INVALID_PASSWORD);
        }
        UserEntity userEntity = userPrincipal.getUserEntity();
        userEntity.setPassword(newPassword);
        userEntity = userRepository.save(userEntity);
        return userEntityToUserResponseModelConverter.convert(userEntity);
    }

    // Only Admin
    public UserResponseModel addUser(UserRequestModel userRequestModel) throws ServerException {
        if (userRepository.existsByUsername(userRequestModel.getUsername())) {
            throw new ServerException(ErrorType.USERNAME_ALREADY_EXIST);
        }
        return saveUser(userRequestModel);
    }

    // Admin or Validated User
    public UserResponseModel updateUser(UserRequestModel userRequestModel, HttpServletRequest request) throws ServerException {
        if (!userRepository.existsById(userRequestModel.getId())) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        jwtUserValidator.validateIdFromJWT(userRequestModel.getId(), request);
        return saveUser(userRequestModel);
    }

    private UserResponseModel saveUser(UserRequestModel userRequestModel) throws ServerException {
        validateUser(userRequestModel);
        UserEntity userEntity = userRequestModelToUserEntityConverter.convert(userRequestModel);
        if (userEntity == null) {
            throw new ServerException(ErrorType.GENERAL_ERROR, userRequestModel.getUsername());
        }
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        if (userEntity.getId() == 0) {
            userEntity.setJoinDate(LocalDate.now());
            userEntity.setAmountOfFailedLogins(0);
            userEntity.setStatus(Status.ACTIVE);
        } else {
            UserEntity userFromDB = getUserEntity(userEntity.getId());
            userEntity.setJoinDate(userFromDB.getJoinDate());
            userEntity.setAmountOfFailedLogins(userFromDB.getAmountOfFailedLogins());
            userEntity.setStatus(userFromDB.getStatus());
        }
        userEntity = userRepository.save(userEntity);
        return userEntityToUserResponseModelConverter.convert(userEntity);
    }

    // Admin or Validated User
    public void deleteUser(long id, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateIdFromJWT(id, request);
        UserResponseModel user = getUser(id);
        if(user.getRole() == Role.CUSTOMER){
            customerService.deleteCustomer(id, request);
        }
        else {
            userRepository.deleteById(id);
        }
    }

    // Internal Server Use
    public UserResponseModel getUser(long id) throws ServerException {
        return userRepository.getUser(id);
    }

    // Admin or Validated User
    public UserResponseModel getUserRequest(long id, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateIdFromJWT(id, request);
        return getUser(id);
    }

    // Only Admin
    public Page<UserResponseModel> getUsers(int pageSize, int pageNumber) {
        Page<UserEntity> userEntityPage = userRepository.findAll(Pageable.ofSize(pageSize).withPage(pageNumber));
        return userEntityPage.map(userEntityToUserResponseModelConverter::convert);
    }

    // Admin or Validated Company User
    public Page<UserResponseModel> getCompanyUsers(long id, int pageSize, int pageNumber, HttpServletRequest request) throws ServerException {
        jwtUserValidator.validateCompanyIdFromJWT(id, request);
        CompanyEntity company = companyService.getCompanyEntity(id);
        Page<UserEntity> userEntityPage = userRepository.findByCompany(company, Pageable.ofSize(pageSize).withPage(pageNumber));
        return userEntityPage.map(userEntityToUserResponseModelConverter::convert);
    }

    // Only Admin
    public Page<UserResponseModel> getUsersByRole(Role role, int pageSize, int pageNumber) {
        Page<UserEntity> userEntityPage = userRepository.findByRole(role, Pageable.ofSize(pageSize).withPage(pageNumber));
        return userEntityPage.map(userEntityToUserResponseModelConverter::convert);
    }

    // Internal Server Use
    public UserEntity getUserEntity(long id) throws ServerException {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        if (userEntityOptional.isEmpty()) {
            throw new ServerException(ErrorType.USER_DOES_NOT_EXIST);
        }
        return userEntityOptional.get();
    }

    // Internal Server Use
    public List<UserEntity> getCompanyUsersEntities(long id) throws ServerException {
        CompanyEntity companyEntity = companyService.getCompanyEntity(id);
        return userRepository.findByCompany(companyEntity);
    }

    // Internal Server Use
    public List<UserEntity> getUserEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public boolean isUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    private void validateUser(UserRequestModel userRequestModel) throws ServerException {
        if (!RegexValidations.emailValidation(userRequestModel.getUsername())) {
            throw new ServerException(ErrorType.INVALID_USERNAME);
        }
        if (!RegexValidations.passwordValidation(userRequestModel.getPassword())) {
            throw new ServerException(ErrorType.INVALID_PASSWORD);
        }
        if (userRequestModel.getFirstName() != null && !userRequestModel.getFirstName().equals("")  && !RegexValidations.nameValidation(userRequestModel.getFirstName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid first name");
        }
        if (userRequestModel.getLastName() != null && !userRequestModel.getLastName().equals("") && !RegexValidations.nameValidation(userRequestModel.getLastName())) {
            throw new ServerException(ErrorType.INVALID_NAME, "Invalid last name");
        }
        if (userRequestModel.getPhone() != null && !userRequestModel.getPhone().equals("") && !RegexValidations.phoneValidation(userRequestModel.getPhone())) {
            throw new ServerException(ErrorType.INVALID_PHONE_NUMBER);
        }
        if (userRequestModel.getRole() == Role.COMPANY && userRequestModel.getCompanyId() == null) {
            throw new ServerException(ErrorType.INVALID_USER, "User of type " + userRequestModel.getRole() + " must posses company id");
        }
        if (userRequestModel.getRole() != Role.COMPANY && userRequestModel.getCompanyId() != null) {
            throw new ServerException(ErrorType.INVALID_USER, "User of type " + userRequestModel.getRole() + " cannot posses company id");
        }
    }
}
