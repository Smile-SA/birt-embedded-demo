package fr.smile.birt;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EmitterInfo;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.activity.SemanticException;

public interface IRenderer {
	String XLS = "xls";
	String PDF = "pdf";
	String DOC = "doc";
	String ODP = "odp";
	String HTML = "html";
	String ODS = "ods";
	String PPT = "ppt";
	String ODT = "odt";

	IRunAndRenderTask getRunAndRenderTask(String designName) throws EngineException, DesignFileException, SemanticException, IOException, TechnicalException;

	void render(IRunAndRenderTask task, String type, OutputStream outputStream) throws EngineException, DesignFileException, SemanticException, IOException;

	void render(IRunAndRenderTask task, String type, OutputStream outputStream, Map<String, Object> map) throws EngineException, DesignFileException, SemanticException, IOException;

	EmitterInfo[] getEmitterInfos();

	String getContentType(String type);

	String getExtension(String type);
}
