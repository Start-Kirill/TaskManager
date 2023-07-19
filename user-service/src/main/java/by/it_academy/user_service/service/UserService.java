package by.it_academy.user_service.service;

import by.it_academy.user_service.core.dto.UserCreateDto;
import by.it_academy.user_service.dao.api.IUserDao;
import by.it_academy.user_service.dao.entity.User;
import by.it_academy.user_service.service.api.IUserService;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    private IUserDao userDao;

    private ConversionService conversionService;

    public UserService(IUserDao userDao, ConversionService conversionService) {
        this.userDao = userDao;
        this.conversionService = conversionService;
    }

    @Override
    public User save(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public User update(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    public List<User> get() {
        return this.userDao.findAll();
    }

    @Override
    public User get(Long id) {
        return null;
    }
}
