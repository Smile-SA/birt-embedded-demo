package fr.smile.birt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleTest extends BaseReportTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleTest.class);

	private void render(String type) {
		render("simple", type);
	}

	@Test
	public void renderHtml() {
		LOGGER.debug("renderHtml");
		render(IRenderer.HTML);
	}

	@Test
	public void renderPdf() {
		LOGGER.debug("renderPdf");
		render(IRenderer.PDF);
	}

	@Test
	public void renderXls() {
		LOGGER.debug("renderXls");
		// FIXME XLS : MS office format doesn't seem to work
		// render(IRenderer.XLS);
	}

	@Test
	public void renderDoc() {
		LOGGER.debug("renderDoc");
		// FIXME DOC :MS office format doesn't seem to work
		// render(IRenderer.DOC);
	}

	@Test
	public void renderOdp() {
		LOGGER.debug("renderOdp");
		// FIXME ODP : java.io.IOException: Stream closed
		// render(IRenderer.ODP);
	}

	@Test
	public void renderOds() {
		LOGGER.debug("renderOds");
		// FIXME ODS : java.io.IOException: Stream closed
		// render(IRenderer.ODS);
	}

	@Test
	public void renderPpt() {
		LOGGER.debug("renderPpt");
		// FIXME PPT : MS office format doesn't seem to work
		// render(IRenderer.PPT);
	}

	@Test
	public void renderOdt() {
		LOGGER.debug("renderOdt");
		// FIXME ODT : java.io.IOException: Stream closed
		// render(IRenderer.ODT);
	}
}
