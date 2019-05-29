package no.priv.bang.authservice.web.security.dbrealm;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import no.priv.bang.authservice.definitions.AuthserviceDatabaseService;

@Component( service=Realm.class, immediate=true )
public class AuthserviceDbRealm extends JdbcRealm {

    LogService logservice;
    private AuthserviceDatabaseService database;

    @Reference
    public void setLogservice(LogService logservice) {
        this.logservice = logservice;
    }

    @Reference
    public void setDatabaseService(AuthserviceDatabaseService database) {
        this.database = database;
    }

    @Activate
    public void activate() {
        dataSource = database.getDatasource();
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("SHA-256");
        credentialsMatcher.setStoredCredentialsHexEncoded(false); // base64 encoding, not hex
        credentialsMatcher.setHashIterations(1024);
        setCredentialsMatcher(credentialsMatcher);
    }

}
