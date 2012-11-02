package fr.smile.birt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LibraryWithConnectionProfileTest extends BaseReportTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(LibraryWithConnectionProfileTest.class);

	@Test
	public void renderPdf() {
		LOGGER.debug("renderPdf");
		render("library_with_connectionprofile", IRenderer.PDF);
	}
}
