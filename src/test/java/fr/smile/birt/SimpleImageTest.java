package fr.smile.birt;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleImageTest extends BaseReportTestCase {
	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleImageTest.class);

	@Test
	public void renderPdfEmbedImage() {
		LOGGER.debug("renderPdfEmbedImage");
		render("simple_with_embed_image", IRenderer.PDF);
	}

	@Test
	public void renderPdfSharedImage() {
		LOGGER.debug("renderPdfSharedImage");
		// FIXME Error.ResourceNotAccessible
		render("simple_with_shared_image", IRenderer.PDF);
	}
}
