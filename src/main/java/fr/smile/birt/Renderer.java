package fr.smile.birt;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.eclipse.birt.report.engine.api.EmitterInfo;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.IPDFRenderOption;
import org.eclipse.birt.report.engine.api.IRenderOption;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.birt.report.model.api.DataSourceHandle;
import org.eclipse.birt.report.model.api.DesignElementHandle;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Renderer implements IRenderer {
	private static final Logger LOGGER = LoggerFactory.getLogger(Renderer.class);
	@Resource
	private IReportEngine reportEngine;
	private Map<String, org.springframework.core.io.Resource> designResources = new HashMap<String, org.springframework.core.io.Resource>();
	private Map<String, URL> connectionProfiles = new HashMap<String, URL>();

	private IReportRunnable getReportRunnable(String designName) throws EngineException, IOException {
		org.springframework.core.io.Resource designResource = designResources.get(designName);
		if (designResource == null) {
			throw new TechnicalException("design " + designName + " is not available");
		}
		LOGGER.debug("getDesign designName={}", designResource.getFilename());
		return reportEngine.openReportDesign(designResource.getInputStream());
	}

	private void handleConnectionProfile(IReportRunnable reportRunnable) throws SemanticException {
		DesignElementHandle designElementHandle = reportRunnable.getDesignHandle();
		for (String dataSourceName : connectionProfiles.keySet()) {
			DataSourceHandle dataSource = designElementHandle.getRoot().findDataSource(dataSourceName);
			if (dataSource != null) {
				String path = connectionProfiles.get(dataSourceName).getPath();
				LOGGER.debug("path for design [{}] : [{}]", new Object[] { dataSourceName, path });
				dataSource.setStringProperty("OdaConnProfileStorePath", path);
			}
		}
	}

	@Override
	public IRunAndRenderTask getRunAndRenderTask(String designName) throws EngineException, DesignFileException, SemanticException, IOException, TechnicalException {
		LOGGER.debug("getDesign rptDesign={}", designName);
		IReportRunnable reportRunnable = getReportRunnable(designName);
		handleConnectionProfile(reportRunnable);
		return reportEngine.createRunAndRenderTask(reportRunnable);
	}

	public EmitterInfo[] getEmitterInfos() {
		LOGGER.debug("getEmitterInfos");
		return reportEngine.getEmitterInfo();
	}

	private IRenderOption getRenderOptions(String type) {
		IRenderOption result = new RenderOption();
		if (type.equalsIgnoreCase("html")) {
			HTMLRenderOption htmlOptions = new HTMLRenderOption();
			htmlOptions.setImageDirectory("img");
			htmlOptions.setHtmlPagination(false);
			htmlOptions.setHtmlRtLFlag(false);
			htmlOptions.setEmbeddable(false);
			htmlOptions.setSupportedImageFormats("PNG");
			result = htmlOptions;
		} else if (type.equalsIgnoreCase("pdf")) {
			PDFRenderOption pdfOptions = new PDFRenderOption();
			pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.FIT_TO_PAGE_SIZE);
			pdfOptions.setOption(IPDFRenderOption.PAGE_OVERFLOW, IPDFRenderOption.OUTPUT_TO_MULTIPLE_PAGES);
			result = pdfOptions;
		}
		return result;
	}

	@Override
	public void render(IRunAndRenderTask task, String type, OutputStream outputStream) throws EngineException, DesignFileException, SemanticException, IOException {
		render(task, type, outputStream, new HashMap<String, Object>());
	}

	@Override
	@SuppressWarnings("unchecked")
	public void render(IRunAndRenderTask task, String type, OutputStream outputStream, Map<String, Object> extraParams) throws EngineException, DesignFileException, SemanticException, IOException {
		LOGGER.debug("render type={}", type);
		// set parameter
		for (String extraParamNames : extraParams.keySet()) {
			Object extraParamValue = extraParams.get(extraParamNames);
			task.setParameterValue(extraParamNames, extraParamValue);
		}
		// set parent classloader for reportEngine
		task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, getClass().getClassLoader());
		IRenderOption options = getRenderOptions(type);
		options.setOutputFormat(type);
		options.setOutputStream(outputStream);
		task.setRenderOption(options);
		// run and render report
		task.run();
		task.close();
	}

	@Override
	public String getContentType(String type) {
		// TODO complete this method to match your needs
		String contentType = null;
		if (IRenderer.PDF.equals(type)) {
			contentType = "application/pdf";
		} else if (IRenderer.HTML.equals(type)) {
			contentType = "text/html";
		} else {
			LOGGER.error("contentType not managed for report type {}", type);
		}
		return contentType;
	}

	public String getExtension(String type) {
		// TODO complete this method to match your needs
		String extension = null;
		if (IRenderer.PDF.equals(type)) {
			extension = "pdf";
		} else if (IRenderer.HTML.equals(type)) {
			extension = "html";
		} else {
			LOGGER.error("extension not managed for report type {}", type);
		}
		return extension;
	}

	/* GETTER AND SETTER */
	public Map<String, org.springframework.core.io.Resource> getDesignResources() {
		return designResources;
	}

	public void setDesignResources(Map<String, org.springframework.core.io.Resource> designResources) {
		this.designResources = designResources;
	}

	public Map<String, URL> getConnectionProfiles() {
		return connectionProfiles;
	}

	public void setConnectionProfiles(Map<String, URL> connectionProfiles) {
		this.connectionProfiles = connectionProfiles;
	}
}
