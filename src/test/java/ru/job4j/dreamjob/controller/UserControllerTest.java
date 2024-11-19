package ru.job4j.dreamjob.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;

    private UserController userController;

    private HttpSession session;

    private HttpServletRequest request;

    @BeforeEach
    public void initServices() {
        userService = mock(UserService.class);
        userController = new UserController(userService);
        session = mock(HttpSession.class);
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void whenOpenRegisterPageGetRegisterPage() {
        String viewName = userController.getRegisterPage();
        assertThat(viewName).isEqualTo("users/register");
    }

    @Test
    public void whenUserLogOutThenRedirectToLoginPage() {
        String viewName = userController.logout(session);
        verify(session, times(1)).invalidate();
        assertThat(viewName).isEqualTo("redirect:/users/login");
    }

    @Test
    public void whenRegisterUserGetTheSameUser() {
        var user = new User(1, "test@test", "test", "password");
        var userArgumentCaptor = ArgumentCaptor.forClass(User.class);
        when(userService.save(userArgumentCaptor.capture())).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(model, user);
        var actualUser = userArgumentCaptor.getValue();

        assertThat(view).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenLoginUserWithValidCredentialsThenRedirectToVacancies() {
        var user = new User(1, "test@test", "test", "password");
        var userCaptor = ArgumentCaptor.forClass(User.class);

        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.of(user));
        when(request.getSession()).thenReturn(session);

        var model = new ConcurrentModel();
        String viewName = userController.loginUser(user, model, request);
        verify(session, times(1)).setAttribute(eq("user"), userCaptor.capture());
        var actualUser = userCaptor.getValue();

        assertThat(viewName).isEqualTo("redirect:/vacancies");
        assertThat(actualUser).isEqualTo(user);
    }

    @Test
    public void whenLoginUserWithInvalidCredentialsThenReturnLoginPageWithError() {
        var user = new User(1, "test@test", "test", "password");
        when(userService.findByEmailAndPassword(user.getEmail(), user.getPassword()))
                .thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);
        var actualError = model.getAttribute("error");

        assertThat(view).isEqualTo("users/login");
        assertThat(actualError).isEqualTo("Почта или пароль введены неверно");
    }

}