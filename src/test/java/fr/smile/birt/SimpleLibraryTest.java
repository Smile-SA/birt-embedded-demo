package fr.smile.birt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleLibraryTest extends BaseReportTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleLibraryTest.class);

	@Test
	public void renderPdf() {
		LOGGER.debug("renderPdf");
		render("simple_with_library", IRenderer.PDF);
	}
}
