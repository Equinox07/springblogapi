package security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import security.services.UserDetailsServiceImpl;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends OncePerRequestFilter {


    //Inject dependencies
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // get JWT token from http request
        String token = getJwtFromToken(request);
        // Validate token
        if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
        // get username from token
            String username = jwtTokenProvider.getUserNameFromJwt(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities());

            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // set spring security
            // load user associated with token
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);


    }


    private String getJwtFromToken(HttpServletRequest request){
        String bearerToken = request.getHeader("Authorization");

       if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
           return bearerToken.substring(7, bearerToken.length());
       }
       return null ;
    }
}
