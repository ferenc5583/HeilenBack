package org.zerhusen.security.controller;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.model.security.User;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;
import org.zerhusen.security.repository.UserRepository;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    //busca a un usuario x token
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public JwtUser getAuthenticatedUser(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader).substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(username);
        return user;
    }

    //crea un nuevo usuario paciente
    @CrossOrigin
    @RequestMapping(value = "/user/nuevo/", method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public User newUser(@Valid @RequestBody User user) {
        userRepository.save(user);
        userRepository.newAuthority(user.getId(), 1);
        return user;
    }
//      Json para guardar
//{
//    "username": "ferenc@user.com",
//    "password": "123456",
//    "firstname": "Ferenc",
//    "lastname": "Riquelme",
//    "authorities": [ { "id": 1} ],
//    "enabled": true,
//    "lastPasswordResetDate": "2018-09-16"
//}
    //lista todos los usuarios

    @CrossOrigin
    @RequestMapping(value = "/user/todos/", method = GET)
    public Collection<JwtUser> allUsers() {
        Collection<User> asd = userRepository.findAll();
        ArrayList lista = new ArrayList<>();
        for (User c : asd) {
            JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(c.getUsername());
            lista.add(user);
        }
        return lista;
    }

    //busca a un usuario por id
    @CrossOrigin
    @RequestMapping(value = "/user/{id}", method = GET)
    public JwtUser getUser(@PathVariable long id) {
        User asd = (User) userRepository.UserxID(id);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(asd.getUsername());
        return user;
    }
    //edita la contraseña de un usuario por el mail
    @CrossOrigin
    @RequestMapping(value = "/user/{mail}", method = RequestMethod.PUT, produces = "application/json")
    public String editUserTest(@PathVariable String mail) {
        int length = 10;
        boolean useLetters = true;
        boolean useNumbers = false;
        JsonObject res = new JsonObject();
        
        User userFind = (User) userRepository.UserByUsername(mail);
        
        if (userFind != null) {
            String newPass = RandomStringUtils.random(length, useLetters, useNumbers);
            userRepository.UserEdit(BCrypt.hashpw(newPass, BCrypt.gensalt()), mail);
            res.addProperty("message", "Contraseña editada Correctamente");
            res.addProperty("n_pass", newPass);
            res.addProperty("find", true);
            String json_res = res.toString();
            return json_res;
        } else {
            res.addProperty("message", "Usuario no Encontrado123");
            res.addProperty("find", false);
            String json_res = res.toString();
            return json_res;
        }
    }
}
