package no.priv.bang.modeling.modelstore;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    @SuppressWarnings("rawtypes")
    private ServiceRegistration propertysetManagerServiceRegistration;

    public void start(BundleContext context) throws Exception {
    	propertysetManagerServiceRegistration = context.registerService(PropertysetManager.class.getName(), DefaultPropertysetManager.getInstance(), null);
    }

    public void stop(BundleContext context) throws Exception {
    	propertysetManagerServiceRegistration.unregister();
    }

}
