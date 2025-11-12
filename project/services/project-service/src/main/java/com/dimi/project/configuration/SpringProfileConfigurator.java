package com.dimi.project.configuration;

import com.dimi.core.Domain;
import com.dimi.core.Orion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SpringProfileConfigurator
{
    @Autowired
    public SpringProfileConfigurator(Environment env)
    {
        loadSpringProfile(env.getActiveProfiles());
    }


    private static void loadSpringProfile(String[] activeProfiles)
    {
        if(activeProfiles == null || activeProfiles.length == 0)
        {
            System.setProperty("active.execution.profile", Domain.PRODUCTION);
            Orion.domainName = Domain.PRODUCTION;
        }
        else
        {
            Orion.systemProfileMode = activeProfiles[0];
            if(Orion.isLocalhost())
            {
                Orion.domainName = Domain.LOCALHOST;
            }
            else if(Orion.isTesting())
            {
                Orion.domainName = Domain.TESTING;
            }
            else if(Orion.isDev())
            {
                Orion.domainName = Domain.DEV;
            }
            else if(Orion.isProduction())
            {
                Orion.domainName = Domain.PRODUCTION;
            }
            System.setProperty("active.execution.profile", Orion.domainName);
        }
    }
}