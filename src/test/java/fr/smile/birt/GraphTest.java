package fr.smile.birt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GraphTest extends BaseReportTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(GraphTest.class);

	@Test
	public void renderPdf() {
		LOGGER.debug("renderPdf");
		// TODO graph report
		render("graph", IRenderer.PDF);
	}
}
