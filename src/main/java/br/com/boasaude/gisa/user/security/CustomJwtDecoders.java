package br.com.boasaude.gisa.user.security;

import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.util.Assert;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;


public class CustomJwtDecoders {
    private CustomJwtDecoders() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends JwtDecoder> T fromOidcIssuerLocation(String oidcIssuerLocation) {
        Assert.hasText(oidcIssuerLocation, "oidcIssuerLocation cannot be empty");
        Map<String, Object> configuration = CustomJwtDecoderProviderConfigurationUtils
                .getConfigurationForOidcIssuerLocation(oidcIssuerLocation);
        return (T) withProviderConfiguration(configuration, oidcIssuerLocation);
    }

    /**
     * Creates a {@link JwtDecoder} using the provided <a href=
     * "https://openid.net/specs/openid-connect-core-1_0.html#IssuerIdentifier">Issuer</a>
     * by querying three different discovery endpoints serially, using the values in the
     * first successful response to initialize. If an endpoint returns anything other than
     * a 200 or a 4xx, the method will exit without attempting subsequent endpoints.
     * <p>
     * The three endpoints are computed as follows, given that the {@code issuer} is
     * composed of a {@code host} and a {@code path}:
     *
     * <ol>
     * <li>{@code host/.well-known/openid-configuration/path}, as defined in
     * <a href="https://tools.ietf.org/html/rfc8414#section-5">RFC 8414's Compatibility
     * Notes</a>.</li>
     * <li>{@code issuer/.well-known/openid-configuration}, as defined in <a href=
     * "https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfigurationRequest">
     * OpenID Provider Configuration</a>.</li>
     * <li>{@code host/.well-known/oauth-authorization-server/path}, as defined in
     * <a href="https://tools.ietf.org/html/rfc8414#section-3.1">Authorization Server
     * Metadata Request</a>.</li>
     * </ol>
     * <p>
     * Note that the second endpoint is the equivalent of calling
     * {@link org.springframework.security.oauth2.jwt.JwtDecoders#fromOidcIssuerLocation(String)}
     *
     * @param issuer the <a href=
     *               "https://openid.net/specs/openid-connect-core-1_0.html#IssuerIdentifier">Issuer</a>
     * @return a {@link JwtDecoder} that was initialized by one of the described endpoints
     */
    @SuppressWarnings("unchecked")
    public static <T extends JwtDecoder> T fromIssuerLocation(String issuer) {
        Assert.hasText(issuer, "issuer cannot be empty");
        Map<String, Object> configuration = CustomJwtDecoderProviderConfigurationUtils
                .getConfigurationForIssuerLocation(issuer);
        return (T) withProviderConfiguration(configuration, issuer);
    }

    /**
     * Validate provided issuer and build {@link JwtDecoder} from <a href=
     * "https://openid.net/specs/openid-connect-discovery-1_0.html#ProviderConfigurationResponse">OpenID
     * Provider Configuration Response</a> and
     * <a href="https://tools.ietf.org/html/rfc8414#section-3.2">Authorization Server
     * Metadata Response</a>.
     *
     * @param configuration the configuration values
     * @param issuer        the <a href=
     *                      "https://openid.net/specs/openid-connect-core-1_0.html#IssuerIdentifier">Issuer</a>
     * @return {@link JwtDecoder}
     */
    private static JwtDecoder withProviderConfiguration(Map<String, Object> configuration, String issuer) {
        CustomJwtDecoderProviderConfigurationUtils.validateIssuer(configuration, issuer);
        OAuth2TokenValidator<Jwt> jwtValidator = JwtValidators.createDefaultWithIssuer(issuer);
        String jwkSetUri = configuration.get("jwks_uri").toString();
        CustomRemoteJWKSet<SecurityContext> jwkSource = new CustomRemoteJWKSet<>(url(jwkSetUri));
        Set<SignatureAlgorithm> signatureAlgorithms = CustomJwtDecoderProviderConfigurationUtils
                .getSignatureAlgorithms(jwkSource);
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(jwkSetUri)
                .jwsAlgorithms((algs) -> algs.addAll(signatureAlgorithms)).build();
        jwtDecoder.setJwtValidator(jwtValidator);
        return jwtDecoder;
    }

    private static URL url(String url) {
        try {
            return new URL(url);
        } catch (IOException ex) {
            throw new UncheckedIOException(ex);
        }
    }

}


