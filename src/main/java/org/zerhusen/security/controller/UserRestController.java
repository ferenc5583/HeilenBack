package org.zerhusen.security.controller;

import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.commons.lang.RandomStringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.zerhusen.heilen.model.Calificacion;
import org.zerhusen.heilen.repository.CalificacionRepository;
import org.zerhusen.heilen.repository.PosicionRepository;
import org.zerhusen.model.security.User;
import org.zerhusen.model.security.UserData;
import org.zerhusen.security.JwtTokenUtil;
import org.zerhusen.security.JwtUser;
import org.zerhusen.security.repository.UserRepository;

@RestController
public class UserRestController {

    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    
    UserData user = new UserData();  

    @Autowired
    @Qualifier("jwtUserDetailsService")
    private UserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PosicionRepository posRepository;
    
    @Autowired
    private CalificacionRepository caliRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

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
    @RequestMapping(value = "/user/nuevoPaciente/", method = POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String newUser(@Valid @RequestBody User user) {
        JsonObject res = new JsonObject();
        
        User userExist = (User) userRepository.UserExist(user.getUsername(), user.getRut());
        if(userExist == null){
            userRepository.save(user);
            userRepository.newAuthority(user.getId(), 1);
            posRepository.NewPositionDefault(user.getId());          
            res.addProperty("exist", false);
            String json_res = res.toString();
            return json_res;
        }else{
            res.addProperty("exist", true);
            String json_res = res.toString();
            return json_res;
        }   
    }
    
    //crea un nuevo usuario profesional
    @CrossOrigin
    @RequestMapping(value = "/user/nuevoProfesional/", method = POST, produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public String newUserProf(@Valid @RequestBody User user) {
        JsonObject res = new JsonObject();
        Calificacion calificacion = new Calificacion(1,5,0,5);
        user.setId_calificacion(calificacion);
        
        User userExist = (User) userRepository.UserExist(user.getUsername(), user.getRut());
        if(userExist == null){
            caliRepository.save(calificacion);
            userRepository.save(user);
            userRepository.newAuthority(user.getId(), 3);
            posRepository.NewPositionDefault(user.getId());
            res.addProperty("exist", false);
            String json_res = res.toString();
            return json_res;
        }else{
            res.addProperty("exist", true);
            String json_res = res.toString();
            return json_res;
        }   
    }
    
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

    //busca a un usuario por id (mala parctica)
    @CrossOrigin
    @RequestMapping(value = "/user/{id}", method = GET)
    public JwtUser getUser(@PathVariable long id) {
        User asd = (User) userRepository.UserxID(id);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(asd.getUsername());
        return user;
    }

    //edita la contraseña de un usuario por el mail cuando olvida la pass
    @CrossOrigin
    @RequestMapping(value = "/user/{mail}", method = RequestMethod.PUT, produces = "application/json")
    public String editUserPass(@PathVariable String mail) {
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
            res.addProperty("message", "Usuario no Encontrado");
            res.addProperty("find", false);
            String json_res = res.toString();
            return json_res;
        }
    }

    //comrobacion de password de un usuario (true/false)
    @CrossOrigin
    @RequestMapping(value = "/user/passFind/{password}", method = GET, produces = "application/json")
    public String authenticatedUserCredentials(@PathVariable String password, HttpServletRequest request) {
        JwtUser username = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request); 
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);
        
        JsonObject res = new JsonObject();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username.getUsername(), password));
            res.addProperty("find", true);
            String json_res = res.toString();
            return json_res;
        } catch (DisabledException e) {
            throw new AuthenticationException("User is disabled!", e);
        } catch (BadCredentialsException e) {
            throw new AuthenticationException("Bad credentials!", e);
        }
    }
    
    //edita la contraseña de un usuario dentro del sistema
    @CrossOrigin
    @RequestMapping(value = "/user/passEdit/{newPass}", method = RequestMethod.PUT, produces = "application/json")
    public String editUserPass(@PathVariable String newPass, HttpServletRequest request) {
        JsonObject res = new JsonObject();

        JwtUser userMail = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request); 
        User userFind = (User) userRepository.UserByUsername(userMail.getUsername());

        if (userFind != null) {
            
            userRepository.UserEdit(BCrypt.hashpw(newPass, BCrypt.gensalt()), userMail.getUsername());
            res.addProperty("message", "Contraseña editada Correctamente");
            res.addProperty("n_pass", newPass);
            res.addProperty("find", true);
            String json_res = res.toString();
            return json_res;
        } else {
            res.addProperty("message", "Usuario no Encontrado");
            res.addProperty("find", false);
            String json_res = res.toString();
            return json_res;
        }
    }
    //busca aun usuario y determina si el rol al k pertenece x el front
    @CrossOrigin
    @RequestMapping(value = "/user/byRole/{username},{role}", method = GET)
    public JwtUser getUserbyRole(@PathVariable String username, @PathVariable int role) {
        User asd = (User) userRepository.FindXUsernameRole(username, role);
        JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(asd.getUsername());
        return user;
    }
    //usuario online/offline
    @CrossOrigin
    @RequestMapping(value = "/user/isOnline/{status}", method = PUT)
    public void getUserOnline(@Valid @PathVariable int status, HttpServletRequest request) {
        JwtUser eluse = user.getAuthenticatedUser(tokenHeader,jwtTokenUtil,userDetailsService,request);
        userRepository.UserIsOnline(status, eluse.getId());
    }
    
    //post construct para agregar lor roles
    @PostConstruct
    public void init() {
        if (userRepository.findAll().isEmpty() == true) {
           Date fecha = new Date(); 
           User user = new User("user@default.tk","$2a$10$XDfGBV91jUQpWbgtmxIK1OlY3vgYo6m6PnQgyqBBht1jrcoSxRf/e","11111111-1","default","user",true,fecha,true);
           userRepository.save(user);
           userRepository.addRoles("ROLE_USER");
           userRepository.addRoles("ROLE_ADMIN");
           userRepository.addRoles("ROLE_PRO");
        }
    }
}

