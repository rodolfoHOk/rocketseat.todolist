package br.com.hiokdev.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.hiokdev.todolist.user.model.UserModel;
import br.com.hiokdev.todolist.user.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TaskAuthFilter extends OncePerRequestFilter {

  private final IUserRepository userRepository;

  @Override
  protected void doFilterInternal(
    HttpServletRequest request,
    HttpServletResponse response,
    FilterChain filterChain
  ) throws ServletException, IOException {
    String servletPath = request.getServletPath();
    
    if (servletPath.equals("/tasks")) {
      String authorization = request.getHeader("Authorization");
      String authEncoded = authorization.substring("Basic".length()).trim();
      byte[] authDecoded = Base64.getDecoder().decode(authEncoded);
      String authString = new String(authDecoded);
      String[] credentials = authString.split(":");
      String username = credentials[0];
      String password = credentials[1];

      Optional<UserModel> existUser = userRepository.findByUsername(username);
      if (existUser.isEmpty()) {
        response.sendError(401);
      } else {
        var passwordVerify = BCrypt.verifyer()
          .verify(password.toCharArray(), existUser.get().getPassword());
        if (passwordVerify.verified) {
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
  
}
