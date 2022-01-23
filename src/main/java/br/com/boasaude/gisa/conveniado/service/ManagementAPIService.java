package br.com.boasaude.gisa.conveniado.service;

import com.auth0.client.HttpOptions;
import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.RolesFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.Role;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.net.AuthRequest;
import com.auth0.net.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagementAPIService {

    public static final String DOMAIN = "dev-0yczl7li.us.auth0.com";

    public void atualizarUserRole(String token, String email) throws Auth0Exception {
        AuthAPI authAPI = new AuthAPI(DOMAIN, "kZLqQQ0tgxilbLMpmLykYwxLR3N90EIh", "mKs-vezECedfb1voawvqfRbZJ0LeBjC847dtZF71w_5o_EHQl4H7LjR4lDh7NHn8");
        AuthRequest authRequest = authAPI.requestToken("https://"+DOMAIN+"/api/v2/");
        authRequest.setScope("update:users");
        TokenHolder holder = authRequest.execute();
        ManagementAPI mgmt = new ManagementAPI(DOMAIN, holder.getAccessToken());

        RolesFilter rolesFilter = new RolesFilter();
        rolesFilter.withName("Conveniado");
        Request<RolesPage> rolesPageRequest = mgmt.roles().list(rolesFilter);
        RolesPage rolesPage = rolesPageRequest.execute();
        List<String> roleIdList = rolesPage.getItems().stream().map(Role::getId).collect(Collectors.toList());


        UserFilter userFilter = new UserFilter();
        mgmt.users().list(userFilter.withQuery("email=" + email));

        mgmt.users().addRoles("1", roleIdList);

    }
}
