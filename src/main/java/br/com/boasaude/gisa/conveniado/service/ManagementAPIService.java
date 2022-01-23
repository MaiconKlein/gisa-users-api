package br.com.boasaude.gisa.conveniado.service;

import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.RolesFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.Role;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.net.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManagementAPIService {

    public static final String DOMAIN = "dev-0yczl7li";

    public void atualizarUserRole(String token, String email) throws Auth0Exception {
//        AuthAPI authAPI = new AuthAPI(DOMAIN, "3UL6Igy5CgPdABd4X18M2lcDKkoB7vqR", "6T0gorIHT2_OuHJvcKAgGVFvKlJk5RqL6--C1UVKLBAODQNLcL1GNkdnbT_Hnqrt");
//        AuthRequest authRequest = authAPI.requestToken("https://"+DOMAIN+"/api/v2/");
//        TokenHolder holder = authRequest.execute();
        ManagementAPI mgmt = new ManagementAPI(DOMAIN, token.replace("Bearer ", ""));

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
