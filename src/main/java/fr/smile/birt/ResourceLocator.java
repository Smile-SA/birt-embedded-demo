package fr.smile.birt;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.eclipse.birt.report.model.api.IResourceLocator;
import org.eclipse.birt.report.model.api.ModuleHandle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.io.Resource;

public class ResourceLocator implements IResourceLocator {
	private static final Logger LOGGER = LoggerFactory.getLogger(ResourceLocator.class);
	private Resource defaultReportsDir;
	private Resource defaultLibraryDir;
	private Resource defaultLocaleDir;
	private Resource defaultImagesDir;

	@Required
	public void setDefaultImagesDir(Resource defaultImagesDir) {
		this.defaultImagesDir = defaultImagesDir;
	}

	@Override
	public URL findResource(ModuleHandle moduleHandle, String fileName, int type) {
		Resource defaultRes = defaultReportsDir;
		switch (type) {
		case IResourceLocator.LIBRARY:
			defaultRes = defaultLibraryDir;
			break;
		case IResourceLocator.OTHERS:
			defaultRes = defaultLocaleDir;
			break;
		case IResourceLocator.IMAGE:
			defaultRes = defaultImagesDir;
			break;
		default:
			LOGGER.warn("This type is not managed : {}", type);
			break;
		}
		return findResource(fileName, defaultRes);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public URL findResource(ModuleHandle moduleHandle, String fileName, int type, Map map) {
		return findResource(moduleHandle, fileName, type);
	}

	private URL findResource(final String fileName, final Resource res) {
		try {
			Resource r = res.createRelative(res.getFilename() + "/" + fileName);
			return r.getURL();
		} catch (IOException e) {
			return null;
		}
	}

	/* GETTER AND SETTER */
	@Required
	public void setDefaultReportsDir(Resource defaultReportsDir) {
		this.defaultReportsDir = defaultReportsDir;
	}

	@Required
	public void setDefaultLibraryDir(Resource defaultLibraryDir) {
		this.defaultLibraryDir = defaultLibraryDir;
	}

	@Required
	public void setDefaultLocaleDir(Resource defaultLocaleDir) {
		this.defaultLocaleDir = defaultLocaleDir;
	}
}
