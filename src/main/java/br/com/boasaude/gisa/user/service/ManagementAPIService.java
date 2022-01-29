package br.com.boasaude.gisa.user.service;

import br.com.boasaude.gisa.user.domain.GisaUser;
import br.com.boasaude.gisa.user.exception.TechnicalException;
import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.ManagementAPI;
import com.auth0.client.mgmt.filter.RolesFilter;
import com.auth0.client.mgmt.filter.UserFilter;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.json.mgmt.Role;
import com.auth0.json.mgmt.RolesPage;
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.net.AuthRequest;
import com.auth0.net.Request;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ManagementAPIService {

    public static final String DOMAIN = "dev-0yczl7li.us.auth0.com";

    public void atualizarUserRole(String role, String email) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();

        RolesFilter rolesFilter = new RolesFilter();
        rolesFilter.withName(role);
        Request<RolesPage> rolesPageRequest = mgmt.roles().list(rolesFilter);
        RolesPage rolesPage = rolesPageRequest.execute();
        List<String> roleIdList = rolesPage.getItems().stream().map(Role::getId).collect(Collectors.toList());

        UsersPage usersPage = getUsersByEmail(email, mgmt);

        usersPage.getItems().stream().findAny().ifPresent(gisaUser -> {
            try {
                mgmt.users().addRoles(gisaUser.getId(), roleIdList).execute();
            } catch (Auth0Exception e) {
                throw new TechnicalException();
            }
        });
    }

    private UsersPage getUsersByEmail(String email, ManagementAPI mgmt) throws Auth0Exception {
        UserFilter userFilter = new UserFilter();
        return mgmt.users().list(userFilter.withQuery("email=" + email)).execute();
    }

    public void atualizarMetadata(GisaUser gisaUser) throws Auth0Exception {
        ManagementAPI mgmt = getManagementAPI();

        UsersPage usersPage = getUsersByEmail(gisaUser.getEmail(), mgmt);

        usersPage.getItems().stream().findAny()
                .ifPresent(userAuth -> {
                    Map<String, Object> userMetadata = userAuth.getUserMetadata();
                    userMetadata.put("cpf", gisaUser.getCpf());
                    userMetadata.put("nome", gisaUser.getNome());
                    userMetadata.put("role", gisaUser.getRole());
                    userMetadata.put("areaAtuacao", gisaUser.getAreaAtuacao());

                    userAuth.setUserMetadata(userMetadata);

                    try {
                        mgmt.users().update(userAuth.getId(), userAuth).execute();
                    } catch (Auth0Exception e) {
                        throw new TechnicalException();
                    }
                });
    }

    private ManagementAPI getManagementAPI() throws Auth0Exception {
        AuthAPI authAPI = new AuthAPI(DOMAIN, "3UL6Igy5CgPdABd4X18M2lcDKkoB7vqR", "6T0gorIHT2_OuHJvcKAgGVFvKlJk5RqL6--C1UVKLBAODQNLcL1GNkdnbT_Hnqrt");
        AuthRequest authRequest = authAPI.requestToken("https://" + DOMAIN + "/api/v2/");
        TokenHolder holder = authRequest.execute();
        return new ManagementAPI(DOMAIN, holder.getAccessToken());
    }


}
