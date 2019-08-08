package no.priv.bang.modeling.modelstore.backend;

import org.osgi.service.component.annotations.Component;

import no.priv.bang.modeling.modelstore.services.Modelstore;

/**
 * An OSGi Declarative Services component providing an implementation of
 * the {@link Modelstore} service.
 *
 */
@Component(service=Modelstore.class, immediate = true)
public class ModelstoreProvider extends ModelstoreBase  {
}
