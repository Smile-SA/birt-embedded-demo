package fr.smile.birt;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.eclipse.birt.report.engine.api.EngineException;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.model.api.DesignFileException;
import org.eclipse.birt.report.model.api.activity.SemanticException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext-test-report.xml" })
public abstract class BaseReportTestCase {
	@Resource(name = "reportRenderer")
	private IRenderer reportRenderer;

	private OutputStream getOutputStream(String reportfileName, String type) {
		String folderPath = "target" + File.separator + "test_report";
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdir();
		}
		OutputStream outputStream = null;
		File file = new File(folderPath + File.separator + reportfileName + "." + type);
		try {
			file.createNewFile();
			outputStream = new FileOutputStream(file);
		} catch (IOException e) {
			fail(e.getMessage());
		}
		return outputStream;
	}

	protected void render(String reportfileName, String type) {
		render(reportfileName, type, new HashMap<String, Object>());
	}

	protected void render(String reportfileName, String type, Map<String, Object> extraParams) {
		OutputStream outputStream = getOutputStream(reportfileName, type);
		try {
			IRunAndRenderTask runAndRenderTask = reportRenderer.getRunAndRenderTask(reportfileName);
			reportRenderer.render(runAndRenderTask, type, outputStream, extraParams);
		} catch (DesignFileException e) {
			Assert.fail(e.getMessage());
		} catch (SemanticException e) {
			Assert.fail(e.getMessage());
		} catch (EngineException e) {
			Assert.fail(e.getMessage());
		} catch (IOException e) {
			Assert.fail(e.getMessage());
		} catch (TechnicalException e) {
			Assert.fail(e.getMessage());
		} finally {
			IOUtils.closeQuietly(outputStream);
		}
	}
}
