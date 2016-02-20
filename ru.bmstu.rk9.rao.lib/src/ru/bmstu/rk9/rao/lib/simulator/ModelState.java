package ru.bmstu.rk9.rao.lib.simulator;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.bmstu.rk9.rao.lib.exception.RaoLibException;
import ru.bmstu.rk9.rao.lib.resource.ComparableResource;
import ru.bmstu.rk9.rao.lib.resource.ResourceManager;

public class ModelState {
	public ModelState(Collection<Class<?>> resourceClasses) {
		for (Class<?> resourceClass : resourceClasses) {
			if (!ComparableResource.class.isAssignableFrom(resourceClass))
				throw new RaoLibException(
						"Attempting to initialize model state with invalid resource type " + resourceClass);

			resourceManagers.put(resourceClass, new ResourceManager<>());
		}
	}

	@SuppressWarnings("unchecked")
	public <T extends ComparableResource<T>> void eraseResource(T resource) {
		ResourceManager<T> resourceManager = (ResourceManager<T>) resourceManagers.get(resource.getClass());
		if (resourceManager == null)
			throw new RaoLibException("Attempting to erase resource of non-existing type " + resource.getClass());

		resourceManager.eraseResource(resource);
	}

	@SuppressWarnings("unchecked")
	public <T extends ComparableResource<T>> void addResource(T resource) {
		ResourceManager<T> resourceManager = (ResourceManager<T>) resourceManagers.get(resource.getClass());
		if (resourceManager == null)
			throw new RaoLibException("Attempting to add resource of non-existing type " + resource.getClass());

		resourceManager.addResource(resource);
	}

	@SuppressWarnings("unchecked")
	public <T extends ComparableResource<T>> List<T> getAll(Class<T> cl) {
		return (List<T>) resourceManagers.get(cl).getAll();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean checkEqual(ModelState other) {
		if (other == null)
			return false;

		if (other == this)
			return true;

		for (Class<?> resourceClass : resourceManagers.keySet()) {
			ResourceManager<?> resourceManager = resourceManagers.get(resourceClass);
			ResourceManager<?> resourceManagerOther = other.resourceManagers.get(resourceClass);

			if (!resourceManager.checkEqual((ResourceManager) resourceManagerOther))
				return false;
		}

		return true;
	}

	// FIXME stub
	public void deploy() {
	}

	// FIXME stub
	public ModelState copy() {
		return null;
	}

	private Map<Class<?>, ResourceManager<?>> resourceManagers = new HashMap<>();
}
